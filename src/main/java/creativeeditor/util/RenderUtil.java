package creativeeditor.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class RenderUtil {
    public static void glScissorBox( int left, int top, int right, int bottom ) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.currentScreen == null)
            return;

        int width = right - left;
        int height = bottom - top;
        int bottomY = mc.currentScreen.height - bottom;
        double factor = mc.getMainWindow().getGuiScaleFactor();

        int scissorX = (int) (left * factor);
        int scissorY = (int) (bottomY * factor);
        int scissorWidth = (int) (width * factor);
        int scissorHeight = (int) (height * factor);
        GL11.glScissor( scissorX, scissorY, scissorWidth, scissorHeight );
    }


    public static void glScissorRectangle( int x, int y, int width, int height ) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.currentScreen == null)
            return;

        int bottomY = mc.currentScreen.height - (x + height);
        double factor = mc.getMainWindow().getGuiScaleFactor();

        int scissorX = (int) (x * factor);
        int scissorY = (int) (bottomY * factor);
        int scissorWidth = (int) (width * factor);
        int scissorHeight = (int) (height * factor);
        GL11.glScissor( scissorX, scissorY, scissorWidth, scissorHeight );
    }


    public static int scaled( int i, double scaleFactor ) {
        return (int) (i * scaleFactor);
    }

}
