package creativeeditor.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import creativeeditor.data.DataItem;
import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
public class ItemRendererUtils {
    private final ItemRenderer itemRenderer;

    public void renderItemIntoGUI(ItemStack stack, int x, int y, float xRot, float yRot) {
        this.renderItemModelIntoGUI(stack, x, y, xRot, yRot, itemRenderer.getModel(stack, null, null));
    }

    public static void renderFormattedItemNBT(MatrixStack matrix, DataItem item, int mouseX, int mouseY, int width, int height, int maxTextWidth, FontRenderer font) {
        List<ITextProperties> list = new ArrayList<>();
        ITextProperties formatted = (Minecraft.getInstance().options.advancedItemTooltips ? item : item.getTag()).getPrettyDisplay(" ", 0);
        list.add(formatted);
        int w = font.width(formatted);
        if (w < maxTextWidth) {
            maxTextWidth = w - 5;
        }
        GuiUtil.drawHoveringText(item.getItemStack(), matrix, list, mouseX, mouseY, width, height, maxTextWidth, font);
    }

    public TextureManager getTextureManager() {
        return Minecraft.getInstance().textureManager;
    }

    protected void renderItemModelIntoGUI(ItemStack stack, int x, int y, float xRot, float yRot, IBakedModel bakedmodel) {
        RenderSystem.pushMatrix();
        getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
        getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS).setBlurMipmap(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        setupGuiTransform(x, y, xRot, yRot, bakedmodel.isGui3d());
        MatrixStack matrixstack = new MatrixStack();
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            RenderHelper.setupForFlatItems();
        }

        itemRenderer.render(stack, ItemCameraTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        irendertypebuffer$impl.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            RenderHelper.setupFor3DItems();
        }

        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
    }

    private void setupGuiTransform(int xPosition, int yPosition, float xRotation, float yRotation, boolean isGui3d) {
        RenderSystem.translatef((float) xPosition, (float) yPosition, 100.0F + itemRenderer.blitOffset);
        RenderSystem.translatef(8.0F, 8.0F, 0.0F);
        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
        RenderSystem.scalef(16.0F, 16.0F, 16.0F);
        RenderSystem.rotatef(xRotation, 0f, 1f, 0f);
        RenderSystem.rotatef(yRotation, 1f, 0f, 0f);
        GlStateManager._rotatef(1.0f, xRotation, yRotation, 0f);
        if (isGui3d) {
            RenderSystem.enableLighting();
        } else {
            RenderSystem.disableLighting();
        }
    }
}
