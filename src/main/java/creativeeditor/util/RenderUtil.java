package creativeeditor.util;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static void glScissorBox(int left, int top, int right, int bottom) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen == null)
            return;

        int width = right - left;
        int height = bottom - top;
        int bottomY = mc.screen.height - bottom;
        double factor = mc.getWindow().getGuiScale();

        int scissorX = (int) (left * factor);
        int scissorY = (int) (bottomY * factor);
        int scissorWidth = (int) (width * factor);
        int scissorHeight = (int) (height * factor);
        GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
    }


    public static void glScissorRectangle(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen == null)
            return;

        int bottomY = mc.screen.height - (y + height);
        double factor = mc.getWindow().getGuiScale();

        int scissorX = (int) (x * factor);
        int scissorY = (int) (bottomY * factor);
        int scissorWidth = (int) (width * factor);
        int scissorHeight = (int) (height * factor);
        GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
    }


    public static int scaled(int i, double scaleFactor) {
        return (int) (i * scaleFactor);
    }

}
