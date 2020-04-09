package creativeeditor.util;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;

public class GuiUtil extends GuiUtils {
    @SuppressWarnings( "resource" )
    public static FontRenderer getFontRenderer() {
        return Minecraft.getInstance().fontRenderer;
    }


    public static boolean isMouseIn( int mouseX, int mouseY, int x, int y, int width, int height ) {
        return x <= mouseX && mouseX < x + width && y <= mouseY && mouseY < y + height;
    }


    public static void drawFrame( int x, int y, int width, int height, int border, Color color ) {
        AbstractGui.fill( x, y, width, y + border, color.getInt() );
        AbstractGui.fill( x, y + border, x + border, height - border, color.getInt() );
        AbstractGui.fill( width - border, y + border, width, height - border, color.getInt() );
        AbstractGui.fill( x, height - border, width, height, color.getInt() );
    }


    /**
     * Public version of {@link AbstractGui#fillGradient}
     */
    public static void fillGradient( ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd, int fromColor, int toColor ) {
        float f = (float) (fromColor >> 24 & 255) / 255.0F;
        float f1 = (float) (fromColor >> 16 & 255) / 255.0F;
        float f2 = (float) (fromColor >> 8 & 255) / 255.0F;
        float f3 = (float) (fromColor & 255) / 255.0F;
        float f4 = (float) (toColor >> 24 & 255) / 255.0F;
        float f5 = (float) (toColor >> 16 & 255) / 255.0F;
        float f6 = (float) (toColor >> 8 & 255) / 255.0F;
        float f7 = (float) (toColor & 255) / 255.0F;
        GlStateManager.disableTexture();
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.blendFuncSeparate( GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO );
        GlStateManager.shadeModel( 7425 );
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin( 7, DefaultVertexFormats.POSITION_COLOR );
        bufferbuilder.pos( (double) xEnd, (double) yStart, (double) screen.getBlitOffset() ).color( f1, f2, f3, f ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yStart, (double) screen.getBlitOffset() ).color( f1, f2, f3, f ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yEnd, (double) screen.getBlitOffset() ).color( f5, f6, f7, f4 ).endVertex();
        bufferbuilder.pos( (double) xEnd, (double) yEnd, (double) screen.getBlitOffset() ).color( f5, f6, f7, f4 ).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel( 7424 );
        GlStateManager.disableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();
    }


    @SuppressWarnings( "resource" )
    @Nullable
    public static Screen getCurrentScreen() {
        return Minecraft.getInstance().currentScreen;
    }


    public static boolean isMouseInRegion( double mouseX, double mouseY, int xPos, int yPos, int width, int height ) {
        return mouseX >= xPos && mouseY >= yPos && mouseX < xPos + width && mouseY < yPos + height;
    }


    public static void addToolTip( Screen screen, int xPos, int yPos, int width, int height, int mouseX, int mouseY, String... str ) {
        if (isMouseInRegion( mouseX, mouseY, xPos, yPos, width, height )) {
            addToolTip( screen, mouseX, mouseY, str );
        }
    }


    public static void addToolTip( Screen screen, Widget w, int mouseX, int mouseY, String... str ) {
        if (isMouseInRegion( mouseX, mouseY, w.x, w.y, w.getWidth(), w.getHeight() )) {
            addToolTip( screen, mouseX, mouseY, str );
        }
    }


    public static void addToolTip( Screen screen, int xPos, int yPos, String... str ) {
        List<String> list = Arrays.asList( str );
        drawHoveringText( null, list, xPos, yPos, screen.width, screen.height, -1, getFontRenderer() );
    }


    @SuppressWarnings( "resource" )
    public static void dropStack( ItemStack stack ) {
        if (!stack.isEmpty()) {
            Minecraft.getInstance().player.inventory.player.dropItem( stack, true );
            Minecraft.getInstance().playerController.sendPacketDropItem( stack );
        }
    }


    public static int getColorFromRGB( int alpha, int red, int green, int blue ) {
        int color = alpha << 24;
        color += red << 16;
        color += green << 8;
        color += blue;
        return color;
    }


    public static void openWebLink( URI url ) {
        try {
            Class<?> oclass = Class.forName( "java.awt.Desktop" );
            Object object = oclass.getMethod( "getDesktop" ).invoke( (Object) null );
            oclass.getMethod( "browse", URI.class ).invoke( object, url );
        }
        catch (Throwable throwable1) {
            // Throwable throwable = throwable1.getCause();
        }
    }
}
