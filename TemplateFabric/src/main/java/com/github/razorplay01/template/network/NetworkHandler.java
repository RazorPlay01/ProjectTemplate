package com.github.razorplay01.template.network;

import com.github.razorplay.packet_handler.network.IPacket;
import com.github.razorplay.packet_handler.network.packet.EmptyPacket;
import com.github.razorplay01.template.FabricTemplate;
import com.github.razorplay01.template.TemplateCommon;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;

public class NetworkHandler {
    private static final Logger LOGGER = FabricTemplate.LOGGER;

    public static void initialize() {
        LOGGER.info("Registering Packets for " + FabricTemplate.MOD_ID);
        TemplateCommon.registerPackets();
        PayloadTypeRegistry.playC2S().register(FabricCustomPayload.CUSTOM_PAYLOAD_ID, FabricCustomPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(FabricCustomPayload.CUSTOM_PAYLOAD_ID, FabricCustomPayload.CODEC);

        registerPacketReceiver();
    }

    private static void registerPacketReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(FabricCustomPayload.CUSTOM_PAYLOAD_ID, (payload, context) ->
                context.client().execute(() -> {
                    IPacket packet = payload.packet();
                    //LOGGER.info("Packet received from server: {}", packet.getPacketId());

                    switch (packet) {
                        case EmptyPacket pkt -> clientHandleEmptyPacket(pkt);

                        default -> LOGGER.info("Packet UnknownPacket received from server.");
                    }
                }));

        ServerPlayNetworking.registerGlobalReceiver(FabricCustomPayload.CUSTOM_PAYLOAD_ID, (payload, context) ->
                context.server().execute(() -> {
                    IPacket packet = payload.packet();
                    //LOGGER.info("Packet received from client: {}", packet.getPacketId());

                    switch (packet) {
                        case EmptyPacket pkt -> serverHandleEmptyPacket(pkt, context);

                        default ->
                                LOGGER.info("Packet UnknownPacket received from client: {}", context.player().getName());
                    }
                }));
    }

    private static void clientHandleEmptyPacket(EmptyPacket pkt) {
        LOGGER.info("Packet EmptyPacket received from server.");
    }

    private static void serverHandleEmptyPacket(EmptyPacket pkt, ServerPlayNetworking.Context context) {
        LOGGER.info("Packet EmptyPacket received from client: {}", context.player().getName());
    }
}
