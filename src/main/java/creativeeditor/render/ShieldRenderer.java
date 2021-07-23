package creativeeditor.render;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;

import creativeeditor.util.ColorUtils.Color;
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
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.Util;

public class ShieldRenderer extends ItemStackTileEntityRenderer {
    // private final BannerTileEntity banner = new BannerTileEntity();
    private final ShieldModel modelShield = new ShieldModel();


    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn ) {
//        Item item = itemStackIn.getItem();
//        if (item == Items.SHIELD) {
//            String displayName = itemStackIn.getDisplayName().getString();
//            boolean use = displayName.contains( "Spectrum" ) || displayName.contains( "Creative" ) || displayName.contains( "Chroma" );
//
//            if (!use) {
//                super.render( itemStackIn, transformType, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn );
//                return;
//            }
//
//            boolean hasBannerPatterns = itemStackIn.getTagElement( "BlockEntityTag" ) != null;
//            matrixStackIn.pushPose();
//            matrixStackIn.scale( 1.0F, -1.0F, -1.0F );
//            RenderMaterial material = hasBannerPatterns ? ModelBakery.SHIELD_BASE : ModelBakery.NO_PATTERN_SHIELD;
//            IVertexBuilder ivertexbuilder = material.sprite().wrap( ItemRenderer.getFoilBufferDirect( bufferIn, this.modelShield.renderType( material.atlasLocation() ), false, item.hasEffect( itemStackIn ) ) );
//
//
//            this.modelShield.plate().render( matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1F, 1F, 1F, 1F );
//
//            if (hasBannerPatterns) {
//                List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.func_230138_a_( ShieldItem.getColor( itemStackIn ), BannerTileEntity.func_230139_a_( itemStackIn ) );
//                renderBannerPatterns( matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, this.modelShield.plate(), material, false, list );
//            }
//            else
//                this.modelShield.handle().render( matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1F );
//
//
//            matrixStackIn.popPose();
//
//            // TODO Add Shield spectrum tooltip information
//        }
    }


    public static void renderBannerPatterns( MatrixStack matrixStack, IRenderTypeBuffer renderType, int combinedLight, int combinedOverlay, ModelRenderer modelRenderer, RenderMaterial materialIn, boolean isBanner, List<Pair<BannerPattern, DyeColor>> patterns ) {
//        modelRenderer.render( matrixStack, materialIn.getBuffer( renderType, RenderType::getEntitySolid ), combinedLight, combinedOverlay );
//
//        float hue = ((float) (Util.milliTime() % 50000)) / 50000;
//        Color color = Color.fromHSV( hue, 1f, 1f );
//        float[] colorComponentValues = new float[] { (float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F };
//
//
//        for (int i = 0; i < 17 && i < patterns.size(); ++i) {
//            Pair<BannerPattern, DyeColor> pair = patterns.get( i );
//            float[] afloat = pair.getSecond() == DyeColor.WHITE ? colorComponentValues : pair.getSecond().getColorComponentValues();
//            Material material = new Material( isBanner ? Atlases.BANNER_ATLAS : Atlases.SHIELD_ATLAS, pair.getFirst().func_226957_a_( isBanner ) );
//            modelRenderer.render( matrixStack, material.getBuffer( renderType, RenderType::getEntityNoOutline ), combinedLight, combinedOverlay, afloat[0], afloat[1], afloat[2], 1.0F );
//        }

    }
}
