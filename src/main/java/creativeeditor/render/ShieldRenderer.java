package creativeeditor.render;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.Util;

public class ShieldRenderer extends ItemStackTileEntityRenderer {
    private final BannerTileEntity banner = new BannerTileEntity();
    private final ShieldModel modelShield = new ShieldModel();


    @Override
    public void renderByItem( ItemStack itemStackIn ) {
        Item item = itemStackIn.getItem();
        if (item == Items.SHIELD) {
            Minecraft mc = Minecraft.getInstance();

            if (itemStackIn.getChildTag( "BlockEntityTag" ) != null) {
                this.banner.loadFromItemStack( itemStackIn, ShieldItem.getColor( itemStackIn ) );
                mc.getTextureManager().bindTexture( BannerTextures.SHIELD_DESIGNS.getResourceLocation( this.banner.getPatternResourceLocation(), this.banner.getPatternList(), this.banner.getColorList() ) );
            }
            else {
                mc.getTextureManager().bindTexture( BannerTextures.SHIELD_BASE_TEXTURE );
            }

            GlStateManager.pushMatrix();
            GlStateManager.scalef( 1.0F, -1.0F, -1.0F );

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
        }
    }


    private void renderEffect( Runnable renderModelFunction ) {
        GlStateManager.color3f( 0.5019608F, 0.2509804F, 0.8F );
        Minecraft.getInstance().getTextureManager().bindTexture( ItemRenderer.RES_ITEM_GLINT );
        ItemRenderer.renderEffect( Minecraft.getInstance().getTextureManager(), renderModelFunction, 1 );
    }
}
