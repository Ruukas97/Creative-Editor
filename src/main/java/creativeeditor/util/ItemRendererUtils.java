package creativeeditor.util;

import com.mojang.blaze3d.platform.GlStateManager;

import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

@SuppressWarnings( "deprecation" )
@AllArgsConstructor
public class ItemRendererUtils {
    private ItemRenderer itemRenderer;
    
    public void renderItemIntoGUI(ItemStack stack, int x, int y, float xRot, float yRot) {
       this.renderItemModelIntoGUI(stack, x, y, xRot, yRot, itemRenderer.getModelWithOverrides(stack));
    }
    
    @SuppressWarnings( "resource" )
    public TextureManager getTextureManager() {
        return Minecraft.getInstance().textureManager;
    }

    protected void renderItemModelIntoGUI(ItemStack stack, int x, int y, float xRot, float yRot, IBakedModel bakedmodel) {
       GlStateManager.pushMatrix();
       getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
       getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
       GlStateManager.enableRescaleNormal();
       GlStateManager.enableAlphaTest();
       GlStateManager.alphaFunc(516, 0.1F);
       GlStateManager.enableBlend();
       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
       GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
       this.setupGuiTransform(x, y, xRot, yRot, bakedmodel.isGui3d());
       bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.GUI, false);
       itemRenderer.renderItem(stack, bakedmodel);
       GlStateManager.disableAlphaTest();
       GlStateManager.disableRescaleNormal();
       GlStateManager.disableLighting();
       GlStateManager.popMatrix();
       getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
       getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }

    private void setupGuiTransform(int xPosition, int yPosition, float xRotation, float yRotation, boolean isGui3d) {
       GlStateManager.translatef((float)xPosition, (float)yPosition, 100.0F + itemRenderer.zLevel);
       GlStateManager.translatef(8.0F, 8.0F, 0.0F);
       GlStateManager.scalef(1.0F, -1.0F, 1.0F);
       GlStateManager.scalef(16.0F, 16.0F, 16.0F);
       GlStateManager.rotatef( xRotation, 0f, 1f, 0f );
       GlStateManager.rotatef( yRotation, 1f, 0f, 0f );
       //GlStateManager.rotatef( 1.0f, xRotation, yRotation, 0f );
       if (isGui3d) {
          GlStateManager.enableLighting();
       } else {
          GlStateManager.disableLighting();
       }

    }
}
