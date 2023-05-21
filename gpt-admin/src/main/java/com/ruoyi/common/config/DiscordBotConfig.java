package com.ruoyi.common.config;

import com.neovisionaries.ws.client.ProxySettings;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.ruoyi.common.DiscordEventListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;

@Configuration
public class DiscordBotConfig {

    @Value("${discord.bot.token}")
    private String discordBotToken;



    @Resource
    private DiscordEventListener discordMessageListener;

    @Bean
    public void onApplicationEvent() {
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(discordBotToken,
                GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.addEventListeners(this.discordMessageListener);
        WebSocketFactory webSocketFactory = new WebSocketFactory();
        ProxySettings proxySettings = webSocketFactory.getProxySettings();
        proxySettings.setHost("127.0.0.1");
        //这里是你的代理
        proxySettings.setPort(7890);
        // 这里是你的代理
        builder.setWebsocketFactory(webSocketFactory);
        builder.build();
    }

//    @Bean
//    public JDABuilder jdaBuilder(DiscordEventListener discordEventListener) throws LoginException {
//        JDABuilder builder = JDABuilder.createDefault(discordBotToken);
//        builder.addEventListeners(discordEventListener);
//        return builder;
//    }

}
