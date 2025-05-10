package com.github.razorplay01.template;

import com.github.razorplay01.template.network.NetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricTemplate implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "fabrictemplate";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
    }

    @Override
    public void onInitializeClient() {
        NetworkHandler.initialize();
    }
}
