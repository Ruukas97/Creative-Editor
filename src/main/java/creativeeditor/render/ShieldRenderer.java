package creativeeditor.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerTileEntity;

public class ShieldRenderer extends ItemStackTileEntityRenderer {
    private final BannerTileEntity banner = new BannerTileEntity();
    private final ShieldModel modelShield = new ShieldModel();


    @Override
    public void render( ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn ) {
        /*Item item = itemStackIn.getItem();
        if (item == Items.SHIELD) {
            Minecraft mc = Minecraft.getInstance();

            boolean flag = itemStackIn.getChildTag( "BlockEntityTag" ) != null;
            matrixStackIn.push();
            matrixStackIn.scale( 1.0F, -1.0F, -1.0F );
            Material material = flag ? ModelBakery.LOCATION_SHIELD_BASE : ModelBakery.LOCATION_SHIELD_NO_PATTERN;
            IVertexBuilder ivertexbuilder = material.getSprite().wrapBuffer( ItemRenderer.getBuffer( bufferIn, this.modelShield.getRenderType( material.getAtlasLocation() ), false, itemStackIn.hasEffect() ) );
            this.modelShield.func_228294_b_().render( matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F );
            if (flag) {
                List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.func_230138_a_( ShieldItem.getColor( itemStackIn ), BannerTileEntity.func_230139_a_( itemStackIn ) );
                BannerTileEntityRenderer.func_230180_a_( matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, this.modelShield.func_228293_a_(), material, false, list );
            }
            else {
                this.modelShield.func_228293_a_().render( matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F );
            }

            matrixStackIn.pop();


            // TODO Add Shield spectrum tooltip information
            // TODO Shield spectrum condition
            String displayName = itemStackIn.getDisplayName().getUnformattedComponentText();
            if (mc.world != null && (displayName.contains( "Spectrum" ) || displayName.contains( "Creative" ) || displayName.contains( "Chroma" ))) {
                float hue = ((float) (Util.milliTime() % 50000)) / 50000;
                Color color = Color.fromHSV( hue, 1f, 1f );
                GlStateManager.color3f( color.getRed(), color.getGreen(), color.getBlue() );
            }

            this.modelShield.render();
            if (itemStackIn.hasEffect()) {
                this.renderEffect( this.modelShield::render );
            }

            GlStateManager.popMatrix();
        }*/
        //TODO Redo
    }


    private void renderEffect( Runnable renderModelFunction ) {
        /*
        GlStateManager.color3f( 0.5019608F, 0.2509804F, 0.8F );
        Minecraft.getInstance().getTextureManager().bindTexture( ItemRenderer.RES_ITEM_GLINT );
        ItemRenderer.renderEffect( Minecraft.getInstance().getTextureManager(), renderModelFunction, 1 );
        */
        //TODO redo
    }
}
