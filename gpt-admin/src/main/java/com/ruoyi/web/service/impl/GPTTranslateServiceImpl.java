package com.ruoyi.web.service.impl;


import com.ruoyi.system.domain.GptOpenaiConfig;
import com.ruoyi.system.mapper.GptOpenaiConfigMapper;
import com.ruoyi.web.service.TranslateService;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class GPTTranslateServiceImpl implements TranslateService {

    @Autowired
    private GptOpenaiConfigMapper gptOpenaiConfigMapper;

	private static GptOpenaiConfig gptOpenaiConfig;

	private static final String model = "gpt-3.5-turbo";


	@PostConstruct
	public void loadConfig() throws Exception {
		List<GptOpenaiConfig> gptOpenaiConfigs = gptOpenaiConfigMapper.selectList();
		if (gptOpenaiConfigs.size() > 1){
			throw new Exception("ApiKey配置错误，请检查数据库是否多条");
		}
		gptOpenaiConfig = gptOpenaiConfigs.get(0);
	}

	// 获取配置信息
	public GptOpenaiConfig getGptOpenaiConfig() {
		return gptOpenaiConfig;
	}

	@Override
	public String translateToEnglish(String prompt) {
		if (!containsChinese(prompt)) {
			return prompt;
		}
		List<ChatMessage> chatMessages = Stream.of(
				new ChatMessage("system", "把中文翻译成英文"),
				new ChatMessage("user", prompt)
		).collect(Collectors.toList());
		ChatCompletionRequest request = ChatCompletionRequest.builder()
				.model(model)
				.temperature(Double.valueOf(getGptOpenaiConfig().getTemperature()))
				.maxTokens(Integer.valueOf(getGptOpenaiConfig().getMaxToken()))
				.messages(chatMessages)
				.build();
		try {
			OpenAiService openAiService = new OpenAiService(getGptOpenaiConfig().getOpenaiKey(), Duration.ofSeconds(30));
			List<ChatCompletionChoice> choices = openAiService.createChatCompletion(request).getChoices();
			if (!choices.isEmpty()) {
				return choices.get(0).getMessage().getContent();
			}
		} catch (Exception e) {
			log.warn("调用chat-gpt接口翻译中文失败: {}", e.getMessage());
		}
		return prompt;
	}
}