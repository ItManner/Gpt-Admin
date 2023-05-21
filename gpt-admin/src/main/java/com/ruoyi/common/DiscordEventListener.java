package com.ruoyi.common;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.entity.Task;
import com.ruoyi.common.entity.TaskCondition;
import com.ruoyi.common.enums.Action;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.ConvertUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DiscordEventListener extends ListenerAdapter  {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String KEY_PREFIX = "mj-task::";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        if (MessageType.SLASH_COMMAND.equals(message.getType()) || MessageType.DEFAULT.equals(message.getType())) {
            MessageData messageData = ConvertUtils.matchImagineContent(message.getContentRaw());
            if (messageData == null) {
                return;
            }
            String taskId = ConvertUtils.findTaskIdByFinalPrompt(messageData.getPrompt());

            Task task = JSONObject.parseObject(redisTemplate.opsForValue().get(taskId),Task.class);
            if (task == null) {
                return;
            }
            task.setMessageId(message.getId());
            if ("Waiting to start".equals(messageData.getStatus())) {
                task.setStatus(TaskStatus.IN_PROGRESS);
            } else {
                finishTask(task, message);
            }
            redisTemplate.opsForValue().set(taskId,JSONObject.toJSONString(task), 60*60);
        } else if (MessageType.INLINE_REPLY.equals(message.getType()) && message.getReferencedMessage() != null) {
            String content = message.getContentRaw();
            MessageData data = ConvertUtils.matchImagineContent(content);
            if (data == null) {
                data = ConvertUtils.matchUVContent(content);
            }
            if (data == null) {
                return;
            }
            String relatedTaskId = ConvertUtils.findTaskIdByFinalPrompt(data.getPrompt());
            if (CharSequenceUtil.isBlank(relatedTaskId)) {
                return;
            }
            TaskCondition condition = new TaskCondition();
            condition.setActionSet(Stream.of(Action.UPSCALE, Action.VARIATION).collect(Collectors.toSet()));
            condition.setRelatedTaskId(relatedTaskId);
            condition.setStatusSet(Stream.of(TaskStatus.NOT_START).collect(Collectors.toSet()));
            Task task = listTask().stream()
                    .filter(condition)
                    .max(Comparator.comparing(Task::getSubmitTime))
                    .orElse(null);
            if (task == null) {
                return;
            }
            task.setStatus(TaskStatus.IN_PROGRESS);
            redisTemplate.opsForValue().set(task.getKey(),JSONObject.toJSONString(task),60*60);
        }
        System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                event.getGuildChannel().getName(),
                event.getAuthor().getName(),
                event.getMessage().getContentDisplay());
    }

    public List<Task> listTask() {
        Set<String> keys = redisTemplate.keys(getRedisKey("*"));
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyList();
        }
        ValueOperations<String, String> operations = this.redisTemplate.opsForValue();
        List<String> objStrings = keys.stream().map(operations::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return objStrings.stream()
                .map(json -> JSON.parseObject(json, Task.class))
                .collect(Collectors.toList());
    }


    private String getRedisKey(String id) {
        return KEY_PREFIX + id;
    }


    void finishTask(Task task, Message message) {
        task.setFinishTime(System.currentTimeMillis());
        if (!message.getAttachments().isEmpty()) {
            task.setStatus(TaskStatus.SUCCESS);
            String imageUrl = message.getAttachments().get(0).getUrl();
            task.setImageUrl(imageUrl);
            int hashStartIndex = imageUrl.lastIndexOf("_");
            task.setMessageHash(CharSequenceUtil.subBefore(imageUrl.substring(hashStartIndex + 1), ".", true));
        } else {
            task.setStatus(TaskStatus.FAILURE);
        }
    }
}
