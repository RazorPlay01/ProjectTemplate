package com.github.razorplay01.template.network;

import com.github.razorplay.packet_handler.exceptions.PacketInstantiationException;
import com.github.razorplay.packet_handler.exceptions.PacketSerializationException;
import com.github.razorplay.packet_handler.network.IPacket;
import com.github.razorplay.packet_handler.network.PacketTCP;
import com.github.razorplay.packet_handler.network.packet.EmptyPacket;
import com.github.razorplay01.template.PaperTemplate;
import com.github.razorplay01.template.TemplateCommon;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

@NoArgsConstructor
public class NetworkHandler implements PluginMessageListener {
    private static final Logger LOGGER = PaperTemplate.LOGGER;

    public void initialize() {
        LOGGER.info("Registering Packets for " + PaperTemplate.PLUGIN_NAME);
        TemplateCommon.registerPackets();
        PaperTemplate.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(PaperTemplate.getInstance(), TemplateCommon.PACKET_BASE_CHANNEL);
        PaperTemplate.getInstance().getServer().getMessenger().registerIncomingPluginChannel(PaperTemplate.getInstance(), TemplateCommon.PACKET_BASE_CHANNEL, this);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
        if (channel.equals(TemplateCommon.PACKET_BASE_CHANNEL)) {
            ByteArrayDataInput buf = ByteStreams.newDataInput(message);

            try {
                IPacket packet = PacketTCP.read(buf);
                LOGGER.info("Packet received from the user: " + player.getName() + ", type: " + packet.getPacketId());

                if (packet instanceof EmptyPacket pkt) {
                    LOGGER.info("Packet " + pkt.getPacketId() + ", received from player: " + player.getName());
                }
            } catch (PacketSerializationException | PacketInstantiationException e) {
                LOGGER.severe("Error processing packet from " + player.getName() + ": " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public static void sendPacketToClient(Player targetPlayer, IPacket packet) {
        try {
            targetPlayer.sendPluginMessage(PaperTemplate.getInstance(), TemplateCommon.PACKET_BASE_CHANNEL, PacketTCP.write(packet));
        } catch (PacketSerializationException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    public static void sendPacketToAllClient(IPacket packet) {
        PaperTemplate.getInstance().getServer().getOnlinePlayers().forEach(targetPlayer -> {
            try {
                targetPlayer.sendPluginMessage(PaperTemplate.getInstance(), TemplateCommon.PACKET_BASE_CHANNEL, PacketTCP.write(packet));
            } catch (PacketSerializationException e) {
                throw new RuntimeException(e);
            }
        });
    }
}