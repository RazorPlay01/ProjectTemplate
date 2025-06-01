package com.github.razorplay01.template;

import co.aikar.commands.PaperCommandManager;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.github.razorplay01.template.network.NetworkHandler;
import com.github.razorplay01.template.util.UtilMessage;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class PaperTemplate extends JavaPlugin {
    public static String PLUGIN_NAME;
    public static Logger LOGGER;
    private @Getter PaperCommandManager commandManager;
    private @Getter TaskChainFactory taskChainFactory;

    @Override
    public void onEnable() {
        PLUGIN_NAME = getInstance().getName();
        LOGGER = getInstance().getLogger();
        // Plugin startup logic
        this.taskChainFactory = BukkitTaskChainFactory.create(this);
        NetworkHandler networkHandler = new NetworkHandler();
        networkHandler.initialize();
        this.registerCommands();

        UtilMessage.sendStartupMessage(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (this.commandManager != null) {
            this.commandManager.unregisterCommands();
        }

        UtilMessage.sendShutdownMessage(this);
    }

    public static PaperTemplate getInstance() {
        return getPlugin(PaperTemplate.class);
    }

    private void registerCommands() {
        this.commandManager = new PaperCommandManager(this);

        //this.commandManager.registerCommand(new NotificationCommand());
    }

    public static <T> TaskChain<T> newChain() {
        return Objects.requireNonNull(getInstance().getTaskChainFactory()).newChain();
    }

    public static <T> TaskChain<T> newSharedChain(String name) {
        return Objects.requireNonNull(getInstance().getTaskChainFactory()).newSharedChain(name);
    }
}
