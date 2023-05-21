package com.ruoyi.web.service;


import com.ruoyi.common.Message;

public interface DiscordService {

	Message<Void> imagine(String prompt, String serverId);

	Message<Void> upscale(String messageId, int index, String messageHash, String serverId);

	Message<Void> variation(String messageId, int index, String messageHash, String serverId);

}
