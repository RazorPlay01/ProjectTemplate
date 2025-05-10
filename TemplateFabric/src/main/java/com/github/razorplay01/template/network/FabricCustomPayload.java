package com.github.razorplay01.template.network;

import com.github.razorplay.packet_handler.exceptions.PacketSerializationException;
import com.github.razorplay.packet_handler.network.IPacket;
import com.github.razorplay.packet_handler.network.PacketTCP;
import com.github.razorplay01.template.TemplateCommon;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record FabricCustomPayload(IPacket packet) implements CustomPayload {
    public static final Id<FabricCustomPayload> CUSTOM_PAYLOAD_ID = new Id<>(Identifier.of(TemplateCommon.PACKET_BASE_CHANNEL));
    public static final PacketCodec<RegistryByteBuf, FabricCustomPayload> CODEC = PacketCodec.tuple(
            new PacketCodec<ByteBuf, IPacket>() {
                public IPacket decode(ByteBuf byteBuf) {
                    try {
                        byte[] data = new byte[byteBuf.readableBytes()];
                        byteBuf.readBytes(data);
                        ByteArrayDataInput in = ByteStreams.newDataInput(data);
                        return PacketTCP.read(in);
                    } catch (Exception e) {
                        return null;
                    }
                }

                public void encode(ByteBuf byteBuf, IPacket packet) {
                    try {
                        byteBuf.writeBytes(PacketTCP.write(packet));
                    } catch (PacketSerializationException ignored) {
                        // []
                    }
                }
            },
            FabricCustomPayload::packet, FabricCustomPayload::new);

    @Override
    public Id<? extends FabricCustomPayload> getId() {
        return CUSTOM_PAYLOAD_ID;
    }
}