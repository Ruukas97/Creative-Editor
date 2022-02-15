package infinityitemeditor.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.ParentScreen;
import infinityitemeditor.screen.widgets.ColorHelperWidget;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.MutableComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.client.gui.GuiUtils;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GuiUtil extends GuiUtils {
    @SuppressWarnings("resource")
    public static Font getFont() {
        return Minecraft.getInstance().font;
    }


    public static boolean isMouseIn(int mouseX, int mouseY, int x, int y, int width, int height) {
        return x <= mouseX && mouseX < x + width && y <= mouseY && mouseY < y + height;
    }

    public static boolean isMouseInColorWidget(int mouseX, int mouseY, ColorHelperWidget w) {
        return w.x <= mouseX && mouseX < w.x + w.getWidth() && w.y <= mouseY && mouseY < w.y + w.getHeight();
    }


    public static void drawFrame(PoseStack poseStack, int xStart, int yStart, int xEnd, int yEnd, int border, Color color) {
        int c = color.getInt();
        AbstractGui.fill(poseStack,xStart, yStart, xEnd, yStart + border, c);
        AbstractGui.fill(poseStack,xStart, yStart + border, xStart + border, yEnd - border, c);
        AbstractGui.fill(poseStack,xEnd - border, yStart + border, xEnd, yEnd - border, c);
        AbstractGui.fill(poseStack,xStart, yEnd - border, xEnd, yEnd, c);
    }


    /**
     * Public version of {@link AbstractGui#}
     */
    public static void fillGradient(ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd, int fromColor, int toColor) {
        float f = (float) (fromColor >> 24 & 255) / 255.0F;
        float f1 = (float) (fromColor >> 16 & 255) / 255.0F;
        float f2 = (float) (fromColor >> 8 & 255) / 255.0F;
        float f3 = (float) (fromColor & 255) / 255.0F;
        float f4 = (float) (toColor >> 24 & 255) / 255.0F;
        float f5 = (float) (toColor >> 16 & 255) / 255.0F;
        float f6 = (float) (toColor >> 8 & 255) / 255.0F;
        float f7 = (float) (toColor & 255) / 255.0F;
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.vertex((double) xEnd, (double) yStart, (double) screen.getBlitOffset()).color(f1, f2, f3, f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yStart, (double) screen.getBlitOffset()).color(f1, f2, f3, f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yEnd, (double) screen.getBlitOffset()).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.vertex((double) xEnd, (double) yEnd, (double) screen.getBlitOffset()).color(f5, f6, f7, f4).endVertex();
        tessellator.end();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }


    public static void fillVerticalGradient(ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd, int topColor, int bottomColor) {
        float f = (float) (topColor >> 24 & 255) / 255.0F;
        float f1 = (float) (topColor >> 16 & 255) / 255.0F;
        float f2 = (float) (topColor >> 8 & 255) / 255.0F;
        float f3 = (float) (topColor & 255) / 255.0F;
        float f4 = (float) (bottomColor >> 24 & 255) / 255.0F;
        float f5 = (float) (bottomColor >> 16 & 255) / 255.0F;
        float f6 = (float) (bottomColor >> 8 & 255) / 255.0F;
        float f7 = (float) (bottomColor & 255) / 255.0F;
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.vertex((double) xEnd, (double) yStart, (double) screen.getBlitOffset()).color(f1, f2, f3, f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yStart, (double) screen.getBlitOffset()).color(f1, f2, f3, f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yEnd, (double) screen.getBlitOffset()).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.vertex((double) xEnd, (double) yEnd, (double) screen.getBlitOffset()).color(f5, f6, f7, f4).endVertex();
        tessellator.end();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }


    public static void fillHorizontalGradient(ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd, int leftColor, int rightColor) {
        float leftAlpha = (float) (leftColor >> 24 & 255) / 255.0F;
        float leftRed = (float) (leftColor >> 16 & 255) / 255.0F;
        float leftGreen = (float) (leftColor >> 8 & 255) / 255.0F;
        float leftBlue = (float) (leftColor & 255) / 255.0F;
        float rightAlpha = (float) (rightColor >> 24 & 255) / 255.0F;
        float rightRed = (float) (rightColor >> 16 & 255) / 255.0F;
        float rightGreen = (float) (rightColor >> 8 & 255) / 255.0F;
        float rightBlue = (float) (rightColor & 255) / 255.0F;
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.vertex((double) xEnd, (double) yStart, (double) screen.getBlitOffset()).color(rightRed, rightGreen, rightBlue, rightAlpha).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yStart, (double) screen.getBlitOffset()).color(leftRed, leftGreen, leftBlue, leftAlpha).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yEnd, (double) screen.getBlitOffset()).color(leftRed, leftGreen, leftBlue, leftAlpha).endVertex();
        bufferbuilder.vertex((double) xEnd, (double) yEnd, (double) screen.getBlitOffset()).color(rightRed, rightGreen, rightBlue, rightAlpha).endVertex();
        tessellator.end();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }


    public static void fillColorPicker(ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd, float hue) {
        Color sat = Color.fromHSB(hue, 1f, 1f);
        float satRed = (float) sat.getRed() / 255.0F;
        float satGreen = (float) sat.getGreen() / 255.0F;
        float satBlue = (float) sat.getBlue() / 255.0F;

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.vertex((double) xEnd, (double) yStart, (double) screen.getBlitOffset()).color(1f, 1f, 1f, 1f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yStart, (double) screen.getBlitOffset()).color(1f, 1f, 1f, 1f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yEnd, (double) screen.getBlitOffset()).color(1f, 1f, 1f, 1f).endVertex();
        bufferbuilder.vertex((double) xEnd, (double) yEnd, (double) screen.getBlitOffset()).color(1f, 1f, 1f, 1f).endVertex();
        tessellator.end();

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.vertex((double) xEnd, (double) yStart, (double) screen.getBlitOffset()).color(satRed, satGreen, satBlue, 1f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yStart, (double) screen.getBlitOffset()).color(satRed, satGreen, satBlue, 0f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yEnd, (double) screen.getBlitOffset()).color(satRed, satGreen, satBlue, 0f).endVertex();
        bufferbuilder.vertex((double) xEnd, (double) yEnd, (double) screen.getBlitOffset()).color(satRed, satGreen, satBlue, 1f).endVertex();
        tessellator.end();

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.vertex((double) xEnd, (double) yStart, (double) screen.getBlitOffset()).color(0f, 0f, 0f, 0f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yStart, (double) screen.getBlitOffset()).color(0f, 0f, 0f, 0f).endVertex();
        bufferbuilder.vertex((double) xStart, (double) yEnd, (double) screen.getBlitOffset()).color(0f, 0f, 0f, 1f).endVertex();
        bufferbuilder.vertex((double) xEnd, (double) yEnd, (double) screen.getBlitOffset()).color(0f, 0f, 0f, 1f).endVertex();
        tessellator.end();

        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }


    public static void fillHueSlider(ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd) {
        int gradWidth = (xEnd - xStart) / 6;
        int i = 0;
        fillHorizontalGradient(screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFFFF0000, 0xFFFFFF00);
        fillHorizontalGradient(screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFFFFFF00, 0xFF00FF00);
        fillHorizontalGradient(screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFF00FF00, 0xFF00FFFF);
        fillHorizontalGradient(screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFF00FFFF, 0xFF0000FF);
        fillHorizontalGradient(screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFF0000FF, 0xFFFF00FF);
        fillHorizontalGradient(screen, xStart + (gradWidth * i++), yStart, xStart + (gradWidth * i), yEnd, 0xFFFF00FF, 0xFFFF0000);
    }


    @SuppressWarnings("resource")
    @Nullable
    public static Screen getCurrentScreen() {
        return Minecraft.getInstance().screen;
    }


    public static boolean isMouseInRegion(double mouseX, double mouseY, int xPos, int yPos, int width, int height) {
        return mouseX >= xPos && mouseY >= yPos && mouseX < xPos + width && mouseY < yPos + height;
    }


    public static void addToolTip(PoseStack poseStack, Screen screen, int xPos, int yPos, int width, int height, int mouseX, int mouseY, String... str) {
        if (isMouseInRegion(mouseX, mouseY, xPos, yPos, width, height)) {
            addToolTip(poseStack,screen, mouseX, mouseY, str);
        }
    }


    public static void addToolTip(PoseStack poseStack, Screen screen, Widget w, int mouseX, int mouseY, String... str) {
        if (isMouseInRegion(mouseX, mouseY, w.x, w.y, w.getWidth(), w.getHeight())) {
            addToolTip(poseStack,screen, mouseX, mouseY, str);
        }
    }


    public static void addToolTip(PoseStack poseStack, Screen screen, int xPos, int yPos, String... str) {
        drawHoveringText(ItemStack.EMPTY, poseStack,Arrays.stream(str).map(TextComponent::new).collect(Collectors.toList()), xPos, yPos, screen.width, screen.height, -1, getFont());
    }

    public static void addToolTip(PoseStack poseStack, Screen screen, int xPos, int yPos, ITextProperties... str) {
        drawHoveringText(ItemStack.EMPTY, poseStack,Lists.newArrayList(str), xPos, yPos, screen.width, screen.height, -1, getFont());
    }


    public static void addToolTip(PoseStack poseStack, Screen screen, int xPos, int yPos, DataItem item) {
        ItemStack stack = item.getData();
        drawHoveringText(stack, poseStack,getTooltipFromItem(stack), xPos, yPos, screen.width, screen.height, -1, getFont());
    }


    public static List getTooltipFromItem(ItemStack stack) {
        Minecraft mc = Minecraft.getInstance();
        List<MutableComponent> list = stack.getTooltipLines(mc.player, mc.options.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
        List<String> list1 = Lists.newArrayList();

        for (MutableComponent itextcomponent : list) {
            list1.add(itextcomponent.getString());
        }

        return list1;
    }


    public static void dropStack(ItemStack stack) {
        if (!stack.isEmpty()) {
            Minecraft mc = Minecraft.getInstance();
            mc.player.inventory.player.drop(stack, true);
//            mc.gameMode.sendPacketDropItem(stack);
        }
    }


    public static int getColorFromRGB(int alpha, int red, int green, int blue) {
        int color = alpha << 24;
        color += red << 16;
        color += green << 8;
        color += blue;
        return color;
    }


    public static void openWebLink(URI url) {
        try {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, url);
        } catch (Throwable throwable1) {
            // Throwable throwable = throwable1.getCause();
        }
    }
}
