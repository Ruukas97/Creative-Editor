package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRange;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.SliderTag;
import creativeeditor.widgets.StyledButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.util.text.TranslationTextComponent;

public class ArmorstandScreen extends ParentItemScreen {

    private ArmorStandEntity armorStand = null;

    private StyledButton entityButton, armsButton, smallButton, invisibleButton, baseButton, markerButton, inventoryButton, poseButton;
    private SliderTag poseHead, poseBody, poseLeftLeg, poseRightLeg, poseLeftArm, poseRightArm;
    private int buttonWidth = 80;
    private int buttonHeight = 15;
    private int divideX = 120;
    private int divideY = 5;


    public ArmorstandScreen(Screen lastScreen, DataItem item) {
        super( new TranslationTextComponent( "gui.armorstandeditor" ), lastScreen, item );
        this.renderItem = false;

    }


    @Override
    protected void init() {
        super.init();
        int x1 = width / divideX;
        int y1 = height / divideY;
        int i = 1;

        poseHead = addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, new NumberRange( 0, 360 ) ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, new NumberRange( 0, 360 ) ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, new NumberRange( 0, 360 ) ) );

        updateArmorStand();

    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color color ) {
        int x1 = width / divideX;
        int y1 = height / divideY;

        drawCenteredString( font, I18n.format("gui.armorstandeditor.head"), x1 + (buttonWidth / 3 * 2), y1 + (buttonHeight / 4), color.getInt() );

        super.mainRender( mouseX, mouseY, p3, color );
    }


    @Override
    public void backRender( int mouseX, int mouseY, float p3, Color color ) {
        if (armorStand != null) {
            drawArmorStand( (int) (this.width / 3 * 2.5), (int) (this.height / 5 * 3.8), 70 );
        }
        super.backRender( mouseX, mouseY, p3, color );
    }


    private void updateArmorStand() {
        if (item.getItemStack().getItem() instanceof ArmorStandItem) {
            ArmorStandEntity entity = new ArmorStandEntity( mc.world, mc.player.posX, mc.player.posY, mc.player.posZ );
            if (entity != null) {
                armorStand = (ArmorStandEntity) entity;

            }
        }
    }


    public void drawArmorStand( int posX, int posY, int scale ) {
        ArmorStandEntity ent = armorStand;

        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translatef( (float) posX, (float) posY, 50.0F );
        GlStateManager.scalef( (float) (-scale), (float) scale, (float) scale );
        GlStateManager.rotatef( 180.0F, 0.0F, 0.0F, 1.0F );
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotatef( 40.0F, 0.0F, 1.0F, 0.0F );
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotatef( -50.0F, 0.0F, 1.0F, 0.0F );
        GlStateManager.rotatef( 10F, 1.0F, 0.0F, 0.0F );
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translatef( 0.0F, 0.0F, 0.0F );
        EntityRendererManager rendermanager = mc.getRenderManager();
        rendermanager.setPlayerViewY( 180.0F );
        rendermanager.setRenderShadow( false );

        rendermanager.renderEntity( armorStand, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false );

        rendermanager.setRenderShadow( true );
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableTexture();
    }


}
