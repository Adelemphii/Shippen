package me.adelemphii.shippen;

import lombok.Getter;
import me.adelemphii.shippen.discord.DiscordBot;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Core {

    private static @Getter DiscordBot discordBot;
    private static @Getter JDA discordApi;

    private static @Getter Logger logger;

    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(Core.class);

        logger.info("Starting Discord Bot...");
        discordBot = new DiscordBot();
        discordApi = discordBot.getJDA();

        registerShutdownHook();
    }

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down...");
            discordApi.shutdown();
        }, "Shutdown-Hook"));
    }
}
