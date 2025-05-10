package com.github.razorplay01.template;

import com.github.razorplay.packet_handler.network.IPacket;
import com.github.razorplay.packet_handler.network.PacketTCP;
import com.github.razorplay.packet_handler.network.packet.EmptyPacket;

public class TemplateCommon {
    public static final String PROJECT_ID = "template";
    public static final String PACKET_BASE_CHANNEL = PROJECT_ID + ":packets_channel";

    private TemplateCommon() {
        //  []
    }

    public static void registerPackets() {
        Class<? extends IPacket>[] packetClasses = new Class[]{
                EmptyPacket.class
        };
        PacketTCP.registerPackets(packetClasses);
    }
}
