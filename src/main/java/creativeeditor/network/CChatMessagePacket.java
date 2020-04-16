package creativeeditor.network;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CChatMessagePacket extends net.minecraft.network.play.client.CChatMessagePacket {
    private String message;

    public CChatMessagePacket() {
    }

    public CChatMessagePacket(String messageIn) {
        message = messageIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
       this.message = buf.readString(512);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
       buf.writeString(this.message);
    }

    public void processPacket(IServerPlayNetHandler handler) {
       handler.processChatMessage(this);
    }

    public String getMessage() {
       return this.message;
    }
 }