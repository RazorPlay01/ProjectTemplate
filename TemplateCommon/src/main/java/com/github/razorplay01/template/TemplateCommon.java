package com.github.razorplay01.template;

import com.github.razorplay.packet_handler.network.IPacket;
import com.github.razorplay.packet_handler.network.PacketTCP;
import com.github.razorplay.packet_handler.network.packet.EmptyPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateCommon {
    public static final String PACKET_BASE_CHANNEL = BuildConstants.ID.toLowerCase() + ":packets_channel";
    public static final Logger LOGGER = LoggerFactory.getLogger(BuildConstants.NAME);

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
