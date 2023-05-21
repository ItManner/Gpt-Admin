package com.ruoyi.web.controller.system;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.BannedPromptHelper;
import com.ruoyi.common.Message;
import com.ruoyi.common.entity.Task;
import com.ruoyi.common.entity.dto.GptImageDto;
import com.ruoyi.common.enums.Action;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.ConvertUtils;
import com.ruoyi.web.service.DiscordService;
import com.ruoyi.web.service.TranslateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/gpt")
public class GptCreateImage {

    private static final Random RANDOM = new Random();
    private static final BannedPromptHelper bannedPromptHelper = new BannedPromptHelper();

    @Autowired
    private TranslateService translateService;
    @Autowired
    private DiscordService discordService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String KEY_PREFIX = "mj-task::";
    /**
     * 提交Imagine或UV任务
     * @param submitDTO 包含提交的信息及动作的GptImageDto对象
     * @return 消息对象，表示任务提交的结果
     */
    @ApiOperation(value = "提交Imagine或UV任务")
    @PostMapping("/submit")
    public Message<String> submit(@RequestBody GptImageDto submitDTO) {
        // 验证提交的信息是否完整，如果不完整则返回验证错误的消息
        if (submitDTO.getAction() == null) {
            return Message.validationError();
        }
        if ((submitDTO.getAction() == Action.UPSCALE || submitDTO.getAction() == Action.VARIATION)
                && (submitDTO.getIndex() < 1 || submitDTO.getIndex() > 4)) {
            return Message.validationError();
        }

        // 创建一个任务对象，设置任务状态、动作、提交时间等信息
        Task task = new Task();
        task.setId(randomIntString(16));
        task.setSubmitTime(System.currentTimeMillis());
        task.setState(submitDTO.getState());
        task.setAction(submitDTO.getAction());

        Message<Void> result;

        // 如果动作是IMAGINE（生成图像），则需要提供生成图像所需的输入数据（prompt）
        if (Action.IMAGINE.equals(submitDTO.getAction())) {
            String prompt = submitDTO.getPrompt();
            if (CharSequenceUtil.isBlank(prompt)) {
                return Message.validationError();
            }

            // 为任务设置相关信息
            task.setKey(task.getId());
            task.setPrompt(prompt);
            String promptEn;
            int paramStart = prompt.indexOf(" --");
            if (paramStart > 0) {
                promptEn = translateService.translateToEnglish(prompt.substring(0, paramStart)).trim() + prompt.substring(paramStart);
            } else {
                promptEn = translateService.translateToEnglish(prompt).trim();
            }

            // 利用翻译服务将输入的prompt翻译成英文，以方便后续处理
            if (bannedPromptHelper.isBanned(promptEn)) {
                return Message.of(Message.VALIDATION_ERROR_CODE, "可能包含敏感词");
            }
            task.setPromptEn(promptEn);
            task.setFinalPrompt("[" + task.getId() + "] " + promptEn);
            task.setDescription("/imagine " + submitDTO.getPrompt());
            redisTemplate.opsForValue().set(getRedisKey(task.getId()), JSONObject.toJSONString(task), 60 * 60);
            // 向Discord服务提交生成图像的请求，并获得提交结果
            result = discordService.imagine(task.getFinalPrompt(),submitDTO.getServerId());
        } else {
            // 如果动作是UPSCALE（缩放图像）或者VARIATION（变更图像），则需要提交相关的任务信息
            if (CharSequenceUtil.isBlank(submitDTO.getTaskId())) {
                return Message.validationError();
            }

            // 根据提交的任务ID查找已有的任务信息
            Task targetTask =JSONObject.parseObject(redisTemplate.opsForValue().get(getRedisKey(submitDTO.getTaskId())),Task.class);
            if (targetTask == null) {
                return Message.of(Message.VALIDATION_ERROR_CODE, "任务不存在或已失效");
            }
            if (!TaskStatus.SUCCESS.equals(targetTask.getStatus())) {
                return Message.of(Message.VALIDATION_ERROR_CODE, "关联任务状态错误");
            }

            // 设置任务相关的信息，包括待处理的prompt、promptEn等
            task.setPrompt(targetTask.getPrompt());
            task.setPromptEn(targetTask.getPromptEn());
            task.setFinalPrompt(targetTask.getFinalPrompt());
            task.setRelatedTaskId(ConvertUtils.findTaskIdByFinalPrompt(targetTask.getFinalPrompt()));
            String key = targetTask.getMessageId() + "-" + submitDTO.getAction();
            task.setKey(key);

            // 根据提交的动作选择不同的处理方式
            if (Action.UPSCALE.equals(submitDTO.getAction())) {
                task.setDescription("/up " + submitDTO.getTaskId() + " U" + submitDTO.getIndex());

                // 将新任务存入任务列表
                redisTemplate.opsForValue().set(key, JSONObject.toJSONString(task), 60*60);

                // 向Discord服务提交缩放图像的请求，并获得提交结果
                result = discordService.upscale(targetTask.getMessageId(), submitDTO.getIndex(), targetTask.getMessageHash(),submitDTO.getServerId());
            } else if (Action.VARIATION.equals(submitDTO.getAction())) {
                task.setDescription("/up " + submitDTO.getTaskId() + " V" + submitDTO.getIndex());

                // 将新任务存入任务列表
                redisTemplate.opsForValue().set(key, JSONObject.toJSONString(task), 60*60);

                // 向Discord服务提交变更图像的请求，并获得提交结果
                result = discordService.variation(targetTask.getMessageId(), submitDTO.getIndex(), targetTask.getMessageHash(),submitDTO.getServerId());
            } else {
                // 动作不是IMAGINE、UPSCALE或者VARIATION，则返回动作不支持的消息
                return Message.of(Message.VALIDATION_ERROR_CODE, "不支持的操作");
            }
        }

        // 根据提交结果判断任务是否提交成功
        if (result.getCode() != Message.SUCCESS_CODE) {
            redisTemplate.delete(getRedisKey(task.getId()));
            return Message.of(result.getCode(), result.getDescription());
        }

        // 返回任务ID，表示任务已提交成功
        return Message.success(task.getId());
    }

    @ApiOperation(value = "列出指定id任务信息")
    @GetMapping("/{id}/fetch")
    public Task getTask(@ApiParam(value = "任务id") @PathVariable String id) {
        return JSONObject.parseObject( redisTemplate.opsForValue().get(getRedisKey(id)), Task.class);
    }

    /**
     * 生成指定长度的随机数字字符串
     *
     * @param length 字符串长度
     * @return 随机数字字符串
     */
    public static String randomIntString(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }

        return sb.toString();
    }

    private String getRedisKey(String id) {
        return KEY_PREFIX + id;
    }
}
