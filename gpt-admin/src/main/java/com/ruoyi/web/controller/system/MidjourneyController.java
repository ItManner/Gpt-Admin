package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.web.domain.Midjourney;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * @Author: Lan HuaZeng
 * @Date: 2023/5/29 16:49
 */
@RestController
@RequestMapping("/system/images")
public class MidjourneyController {

    @Value("${discord.bot.apiUrl}")
    private String apiUrl;

    /**
     * 创建绘画任务
     */
    @PreAuthorize("@ss.hasPermi('system:midjourney:create')")
    @PostMapping("/createImage")
    public AjaxResult createImage(@RequestBody Midjourney midjourney) {
        if (ObjectUtils.isEmpty(midjourney)) {
            throw new GlobalException("参数异常，请检查");
        }
        String fetchId = "";
        AtomicReference<String> msg = new AtomicReference<>("操作成功");
        OkHttpClient client = new OkHttpClient();
        try {
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("prompt", midjourney.getPrompt());
            requestMap.put("action", midjourney.getAction());
            if ("UPSCALE".equals(midjourney.getAction()) || "VARIATION".equals(midjourney.getAction())) {
                requestMap.put("taskId", midjourney.getTaskId());
                requestMap.put("index", midjourney.getIndex());
            }
            // 根据不同的任务类型添加不同的请求参数
            okhttp3.RequestBody requestBody = new FormBody.Builder()
                    .add("prompt", midjourney.getPrompt())
                    .add("action", midjourney.getAction())
                    .build();
            if ("UPSCALE".equals(midjourney.getAction()) || "VARIATION".equals(midjourney.getAction())) {
                requestMap.put("taskId", midjourney.getTaskId());
                requestMap.put("index", midjourney.getIndex());
                requestBody = new FormBody.Builder()
                        .add("action", midjourney.getAction())
                        .add("prompt", midjourney.getPrompt())
                        .add("taskId", midjourney.getTaskId())
                        .add("index", midjourney.getIndex())
                        .build();
            }
            final okhttp3.RequestBody finalRequestBody = requestBody;
            // 发送请求并解析响应
            fetchId = Stream.of(
                            new Request.Builder()
                                    .url(apiUrl + "/api/v1/trigger/submit")
                                    .post(finalRequestBody)
                                    .build())
                    .map(request -> {
                        try {
                            Response response = client.newCall(request).execute();
                            int responseCode = response.code();
                            if (responseCode != 200) {
                                msg.set("操作失败");
                                return "error " + responseCode;
                            } else {
                                JSONObject result = JSONObject.parseObject(response.body().string());
                                return result.getString("result");
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .findFirst()
                    .orElse(fetchId);
            return AjaxResult.success(msg.get(), fetchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PreAuthorize("@ss.hasPermi('system:midjourney:getImage')")
    @GetMapping("/getImage")
    public AjaxResult getImage(@RequestParam String fetchId) {
        if (ObjectUtils.isEmpty(fetchId)) {
            throw new GlobalException("参数异常，请检查");
        }
        String msg = "操作成功";
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiUrl + "/api/v1/task/fetch?_id=" + fetchId)
                    .build();
            Response response = client.newCall(request).execute();
            // 解析响应
            int responseCode = response.code();
            if (responseCode != 200) {
                msg = "查询失败";
                return AjaxResult.error(msg, fetchId);
            }
            JSONObject responseBody = JSONObject.parseObject(response.body().string());
            if (!ObjectUtils.isEmpty(responseBody.getString("code"))) {
                if ("0".equals(responseBody.getString("code"))) {
                    msg = "查询失败";
                }
                return AjaxResult.error(msg, fetchId);
            }
            if (!ObjectUtils.isEmpty(responseBody) && "IN_PROGRESS".equals(responseBody.getString("status"))) {
                msg = "任务进行中...";
                return AjaxResult.success(msg, fetchId);
            }
            return AjaxResult.success(msg, responseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
