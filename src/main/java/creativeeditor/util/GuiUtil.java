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


    public static void drawFrame( int xStart, int yStart, int xEnd, int yEnd, int border, Color color ) {
        AbstractGui.fill( xStart, yStart, xEnd, yStart + border, color.getInt() );
        AbstractGui.fill( xStart, yStart + border, xStart + border, yEnd - border, color.getInt() );
        AbstractGui.fill( xEnd - border, yStart + border, xEnd, yEnd - border, color.getInt() );
        AbstractGui.fill( xStart, yEnd - border, xEnd, yEnd, color.getInt() );
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


    public static void fillVerticalGradient( ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd, int topColor, int bottomColor ) {
        float f = (float) (topColor >> 24 & 255) / 255.0F;
        float f1 = (float) (topColor >> 16 & 255) / 255.0F;
        float f2 = (float) (topColor >> 8 & 255) / 255.0F;
        float f3 = (float) (topColor & 255) / 255.0F;
        float f4 = (float) (bottomColor >> 24 & 255) / 255.0F;
        float f5 = (float) (bottomColor >> 16 & 255) / 255.0F;
        float f6 = (float) (bottomColor >> 8 & 255) / 255.0F;
        float f7 = (float) (bottomColor & 255) / 255.0F;
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


    public static void fillHorizontalGradient( ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd, int leftColor, int rightColor ) {
        float leftAlpha = (float) (leftColor >> 24 & 255) / 255.0F;
        float leftRed = (float) (leftColor >> 16 & 255) / 255.0F;
        float leftGreen = (float) (leftColor >> 8 & 255) / 255.0F;
        float leftBlue = (float) (leftColor & 255) / 255.0F;
        float rightAlpha = (float) (rightColor >> 24 & 255) / 255.0F;
        float rightRed = (float) (rightColor >> 16 & 255) / 255.0F;
        float rightGreen = (float) (rightColor >> 8 & 255) / 255.0F;
        float rightBlue = (float) (rightColor & 255) / 255.0F;
        GlStateManager.disableTexture();
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.blendFuncSeparate( GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO );
        GlStateManager.shadeModel( 7425 );
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin( 7, DefaultVertexFormats.POSITION_COLOR );
        bufferbuilder.pos( (double) xEnd, (double) yStart, (double) screen.getBlitOffset() ).color( rightRed, rightGreen, rightBlue, rightAlpha ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yStart, (double) screen.getBlitOffset() ).color( leftRed, leftGreen, leftBlue, leftAlpha ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yEnd, (double) screen.getBlitOffset() ).color( leftRed, leftGreen, leftBlue, leftAlpha ).endVertex();
        bufferbuilder.pos( (double) xEnd, (double) yEnd, (double) screen.getBlitOffset() ).color( rightRed, rightGreen, rightBlue, rightAlpha ).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel( 7424 );
        GlStateManager.disableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();
    }


    public static void fillColorPicker( ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd, float hue ) {
        float alpha = 1f;
        Color sat = new Color( 0xFFFF0000 );
        float satRed = (float) sat.getRed() / 255.0F;
        float satGreen = (float) sat.getGreen() / 255.0F;
        float satBlue = (float) sat.getBlue() / 255.0F;

        GlStateManager.disableTexture();
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.blendFuncSeparate( GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO );
        GlStateManager.shadeModel( 7425 );
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin( 7, DefaultVertexFormats.POSITION_COLOR );
        bufferbuilder.pos( (double) xEnd, (double) yStart, (double) screen.getBlitOffset() ).color( 1f, 1f, 1f, 1f ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yStart, (double) screen.getBlitOffset() ).color( 1f, 1f, 1f, 1f ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yEnd, (double) screen.getBlitOffset() ).color( 1f, 1f, 1f, 1f ).endVertex();
        bufferbuilder.pos( (double) xEnd, (double) yEnd, (double) screen.getBlitOffset() ).color( 1f, 1f, 1f, 1f ).endVertex();
        tessellator.draw();
        
        bufferbuilder.begin( 7, DefaultVertexFormats.POSITION_COLOR );
        bufferbuilder.pos( (double) xEnd, (double) yStart, (double) screen.getBlitOffset() ).color( satRed, satGreen, satBlue, 1f ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yStart, (double) screen.getBlitOffset() ).color( satRed, satGreen, satBlue, 0f ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yEnd, (double) screen.getBlitOffset() ).color( satRed, satGreen, satBlue, 0f ).endVertex();
        bufferbuilder.pos( (double) xEnd, (double) yEnd, (double) screen.getBlitOffset() ).color( satRed, satGreen, satBlue, 1f ).endVertex();
        tessellator.draw();
        
        bufferbuilder.begin( 7, DefaultVertexFormats.POSITION_COLOR );
        bufferbuilder.pos( (double) xEnd, (double) yStart, (double) screen.getBlitOffset() ).color( 0f, 0f, 0f, 0f ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yStart, (double) screen.getBlitOffset() ).color( 0f, 0f, 0f, 0f ).endVertex();
        bufferbuilder.pos( (double) xStart, (double) yEnd, (double) screen.getBlitOffset() ).color( 0f, 0f, 0f, 1f ).endVertex();
        bufferbuilder.pos( (double) xEnd, (double) yEnd, (double) screen.getBlitOffset() ).color( 0f, 0f, 0f, 1f ).endVertex();
        tessellator.draw();
        
        GlStateManager.shadeModel( 7424 );
        GlStateManager.disableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();
    }


    public static void fillHueSlider( ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd ) {
        int gradWidth = (xEnd - xStart) / 6;
        int i = 0;
        fillHorizontalGradient( screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFFFF0000, 0xFFFFFF00 );
        fillHorizontalGradient( screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFFFFFF00, 0xFF00FF00 );
        fillHorizontalGradient( screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFF00FF00, 0xFF00FFFF );
        fillHorizontalGradient( screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFF00FFFF, 0xFF0000FF );
        fillHorizontalGradient( screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFF0000FF, 0xFFFF00FF );
        fillHorizontalGradient( screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFFFF00FF, 0xFFFF0000 );
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
