package com.ruoyi.web.service.impl;


import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.Message;
import com.ruoyi.system.domain.GptDiscordChannel;
import com.ruoyi.system.mapper.GptDiscordChannelMapper;
import com.ruoyi.web.service.DiscordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordServiceImpl implements DiscordService {

    @Autowired
    private GptDiscordChannelMapper gptDiscordChannelMapper;
    private static final String DISCORD_API_URL = "https://discord.com/api/v9/interactions";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private String imagineParamsJson;
    private String upscaleParamsJson;
    private String variationParamsJson;
    private static final String DICORD_ID = "discord-";


    @PostConstruct
    public void loadDiscord() {
        imagineParamsJson = ResourceUtil.readUtf8Str("api-params/imagine.json");
        upscaleParamsJson = ResourceUtil.readUtf8Str("api-params/upscale.json");
        variationParamsJson = ResourceUtil.readUtf8Str("api-params/variation.json");
    }

    // 根据用户ID获取用户信息
    public GptDiscordChannel getDiscordConfig(String id) {
        try {
            String objString = redisTemplate.opsForValue().get(DICORD_ID + id);
            if (!ObjectUtils.isEmpty(objString)) {
                return JSONObject.parseObject(objString,GptDiscordChannel.class);
            }
            if (ObjectUtils.isEmpty(gptDiscordChannelMapper.selectGptDiscordChannelById(Long.valueOf(id)))) {
                throw new Exception("未配置Mj服务器信息，无法创建任务");
            }
            GptDiscordChannel gptDiscordChannel = gptDiscordChannelMapper.selectGptDiscordChannelById(Long.valueOf(id));
            redisTemplate.opsForValue().set(DICORD_ID + gptDiscordChannel.getId(), JSONObject.toJSONString(gptDiscordChannel), 60 * 60);
            return gptDiscordChannel;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Message<Void> imagine(String prompt, String serverId) {
        GptDiscordChannel discordConfig = getDiscordConfig(serverId);
        String paramsStr = this.imagineParamsJson.replace("$guild_id", discordConfig.getDiscordServerId())
                .replace("$channel_id", discordConfig.getDiscordChannelId());
        JSONObject params = JSON.parseObject(paramsStr);
        params.getJSONObject("data").getJSONArray("options").getJSONObject(0)
                .put("value", prompt);
        return postJsonAndCheckStatus(params.toString(), serverId);
    }

    @Override
    public Message<Void> upscale(String messageId, int index, String messageHash, String serverId) {
        GptDiscordChannel discordConfig = getDiscordConfig(serverId);
        String paramsStr = upscaleParamsJson.replace("$guild_id", discordConfig.getDiscordServerId())
                .replace("$channel_id", discordConfig.getDiscordChannelId())
                .replace("$message_id", messageId)
                .replace("$index", String.valueOf(index))
                .replace("$message_hash", messageHash);
        return postJsonAndCheckStatus(paramsStr, serverId);
    }

    @Override
    public Message<Void> variation(String messageId, int index, String messageHash, String serverId) {
        GptDiscordChannel discordConfig = getDiscordConfig(serverId);
        String paramsStr = this.variationParamsJson.replace("$guild_id", discordConfig.getDiscordServerId())
                .replace("$channel_id", discordConfig.getDiscordChannelId())
                .replace("$message_id", messageId)
                .replace("$index", String.valueOf(index))
                .replace("$message_hash", messageHash);
        return postJsonAndCheckStatus(paramsStr, serverId);
    }

    private ResponseEntity<String> postJson(String paramsStr, String serverId) {
        return postJson(DISCORD_API_URL, paramsStr, serverId);
    }

    private ResponseEntity<String> postJson(String url, String paramsStr, String serverId) {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";
        GptDiscordChannel discordConfig = getDiscordConfig(serverId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", discordConfig.getDiscordToken());
        headers.add("User-Agent", userAgent);
        HttpEntity<String> httpEntity = new HttpEntity<>(paramsStr, headers);
        return new RestTemplate().postForEntity(url, httpEntity, String.class);
    }

    private Message<Void> postJsonAndCheckStatus(String paramsStr, String serverId) {
        try {
            ResponseEntity<String> responseEntity = postJson(paramsStr, serverId);
            if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
                return Message.success();
            }
            return Message.of(responseEntity.getStatusCodeValue(), CharSequenceUtil.sub(responseEntity.getBody(), 0, 100));
        } catch (HttpClientErrorException e) {
            try {
                JSONObject error = JSONObject.parseObject(e.getResponseBodyAsString());
                return Message.of(error.getInteger("code"), error.getString("message"));
            } catch (Exception je) {
                return Message.of(e.getRawStatusCode(), CharSequenceUtil.sub(e.getMessage(), 0, 100));
            }
        }
    }
}
