package infinityitemeditor.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import infinityitemeditor.util.ColorUtils;
import infinityitemeditor.util.TextComponentUtils;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.*;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.Util;

import java.util.List;

public class ShieldRenderer extends ItemStackTileEntityRenderer {
    // private final BannerTileEntity banner = new BannerTileEntity();
    private final ShieldModel modelShield = new ShieldModel();


    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Item item = itemStackIn.getItem();
        if (item == Items.SHIELD) {
            String displayName = TextComponentUtils.getPlainText(itemStackIn.getDisplayName());
            boolean use = displayName.contains("Spectrum") || displayName.contains("Creative") || displayName.contains("Chroma");

            if (!use) {
                super.renderByItem(itemStackIn, transformType, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
                return;
            }

            matrixStackIn.pushPose();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            RenderMaterial material = ModelBakery.SHIELD_BASE;
            IVertexBuilder ivertexbuilder = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(bufferIn, this.modelShield.renderType(material.atlasLocation()), false, item.isFoil(itemStackIn)));


            this.modelShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1F, 1F, 1F, 1F);

            List<Pair<BannerPattern, DyeColor>> list;
            if (itemStackIn.getTagElement("BlockEntityTag") != null) {
                list = BannerTileEntity.createPatterns(ShieldItem.getColor(itemStackIn), BannerTileEntity.getItemPatterns(itemStackIn));
            } else {
                list = BannerTileEntity.createPatterns(DyeColor.WHITE, BannerTileEntity.getItemPatterns(ItemStack.EMPTY));
            }
            renderBannerPatterns(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, this.modelShield.plate(), material, false, list);


            this.modelShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1F);


            matrixStackIn.popPose();

            // TODO Add Shield spectrum tooltip information
        }
    }


    public static void renderBannerPatterns(MatrixStack matrixStack, IRenderTypeBuffer renderType, int combinedLight, int combinedOverlay, ModelRenderer modelRenderer, RenderMaterial materialIn, boolean isBanner, List<Pair<BannerPattern, DyeColor>> patterns) {
        modelRenderer.render(matrixStack, materialIn.buffer(renderType, RenderType::entitySolid), combinedLight, combinedOverlay);

        float hue = ((float) (Util.getMillis() % 50000)) / 50000;
        ColorUtils.Color color = ColorUtils.Color.fromHSV(hue, 1f, 1f);
        float[] colorComponentValues = new float[]{(float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F};


        for (int i = 0; i < 17 && i < patterns.size(); ++i) {
            Pair<BannerPattern, DyeColor> pair = patterns.get(i);
            float[] afloat = pair.getSecond() == DyeColor.WHITE ? colorComponentValues : pair.getSecond().getTextureDiffuseColors();
            RenderMaterial material = new RenderMaterial(isBanner ? Atlases.BANNER_SHEET : Atlases.SHIELD_SHEET, pair.getFirst().location(isBanner));
            modelRenderer.render(matrixStack, material.buffer(renderType, RenderType::entityNoOutline), combinedLight, combinedOverlay, afloat[0], afloat[1], afloat[2], 1.0F);
        }

    }
}
