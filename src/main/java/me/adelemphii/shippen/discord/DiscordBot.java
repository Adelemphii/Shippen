package me.adelemphii.shippen.discord;

import me.adelemphii.shippen.Core;
import me.adelemphii.shippen.discord.listeners.VouchListener;
import me.adelemphii.shippen.discord.listeners.WriteMessageToConsole;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    DiscordBot instance;
    JDA jda;

    public DiscordBot() {
        try {
            this.jda = buildJDA();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        Core.getLogger().info("Registering Listeners...");
        registerListeners(jda);

        Core.getLogger().info("You can invite the bot using: "
                + jda.getInviteUrl(Permission.MESSAGE_MANAGE, Permission.MESSAGE_READ, Permission.MESSAGE_WRITE,
                                    Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION, Permission.MANAGE_ROLES,
                                    Permission.MESSAGE_HISTORY));
    }

    public JDA getJDA() {
        if(jda == null) {
            try {
                jda = buildJDA();
            } catch (LoginException e) {
                e.printStackTrace();
            }
        }
        return jda;
    }

    public DiscordBot getInstance() {
        if(instance == null) {
            this.instance = this;
            return instance;
        }

        return instance;
    }

    private JDA buildJDA() throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(System.getenv().get("DISCORD_BOT_TOKEN"));

        builder.setActivity(Activity.watching("A Pioneer's Playground!"));
        builder.setStatus(OnlineStatus.ONLINE);

        return builder.build();
    }

    private void registerListeners(JDA jda) {
        jda.addEventListener(new WriteMessageToConsole());
        jda.addEventListener(new VouchListener());
    }
}
