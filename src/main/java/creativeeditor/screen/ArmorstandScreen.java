package creativeeditor.screen;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import creativeeditor.data.tag.entity.TagEntityArmorStand.Pose;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.SliderTag;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class ArmorstandScreen extends ParentItemScreen {

    private ArmorStandEntity armorStand = null;

    private int buttonWidth = 80;
    private int buttonHeight = 15;
    private int divideX = 120;
    private int divideY = 7;

    ArrayList<String> bodyTypes = new ArrayList<String>();


    public ArmorstandScreen(Screen lastScreen, DataItem item) {
        super( new TranslationTextComponent( "gui.armorstandeditor" ), lastScreen, item );
        this.renderItem = false;
        getStandData().getShowArms().set( true );
    }
    
    public TagEntityArmorStand getStandData() {
        return item.getTag().getArmorStandTag();
    }


    @Override
    protected void init() {
        super.init();
        int x1 = width / divideX;
        int y1 = height / divideY;
        int i = 1;
        Pose pose = getStandData().getPose();
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getHead().getX() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getHead().getY() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getHead().getZ() ) );
        i = 1;
        y1 += (int) (buttonHeight * 1.5);
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getBody().getX() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getBody().getY() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getBody().getZ() ) );
        i = 1;
        y1 += (int) (buttonHeight * 1.5);
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getRightArm().getX() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getRightArm().getY() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getRightArm().getZ() ) );
        i = 1;
        y1 += (int) (buttonHeight * 1.5);
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getLeftArm().getX() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getLeftArm().getY() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getLeftArm().getZ() ) );
        i = 1;
        y1 += (int) (buttonHeight * 1.5);
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getRightLeg().getX() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getRightLeg().getY() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getRightLeg().getZ() ) );
        i = 1;
        y1 += (int) (buttonHeight * 1.5);
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getLeftLeg().getX() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getLeftLeg().getY() ) );
        addButton( new SliderTag( x1 + ((buttonWidth + 5) * i++), y1, buttonWidth, buttonHeight, pose.getLeftLeg().getZ() ) );
        i = 1;
        y1 += (int) (buttonHeight * 1.5);
        
        
        if (armorStand == null) {
            ArmorStandEntity entity = getStandData().getData();
            armorStand = entity;
        }
        
        if (bodyTypes.isEmpty()) {
            bodyTypes.add( "head" );
            bodyTypes.add( "body" );
            bodyTypes.add( "rightarm" );
            bodyTypes.add( "leftarm" );
            bodyTypes.add( "rightleg" );
            bodyTypes.add( "leftleg" );
        }

    }


    @Override
    public void backRender( int mouseX, int mouseY, float p3, Color color ) {
        updateArmorStand();
        super.backRender( mouseX, mouseY, p3, color );
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color color ) {
        int x1 = width / divideX;
        int y1 = height / divideY;

        for (String s : bodyTypes) {
            drawCenteredString( font, I18n.format( "gui.armorstandeditor." + s ), x1 + (buttonWidth / 3 * 2), y1 + (buttonHeight / 4), color.getInt() );
            y1 += (int) (buttonHeight * 1.5);
        }


        super.mainRender( mouseX, mouseY, p3, color );
        if (armorStand != null) {
            drawArmorStand( (int) (this.width / 3 * 2.5), (int) (this.height / 5 * 3.8), 70 );
        }
    }


    @Override
    public void overlayRender( int mouseX, int mouseY, float p3, Color color ) {
        super.overlayRender( mouseX, mouseY, p3, color );
    }
    
    private void updateArmorStand() {
        if (armorStand != null) {
            armorStand.readAdditional( getStandData().getNBT() );
        }
    }


    public void drawArmorStand( int posX, int posY, int scale ) {
        ArmorStandEntity ent = armorStand;

        GlStateManager.pushMatrix();
        GlStateManager.color3f( 1f, 1f, 1f );
        GlStateManager.enableColorMaterial();
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
        GL11.glPushAttrib( GL11.GL_ALL_ATTRIB_BITS );
        rendermanager.setPlayerViewY( 180.0F );
        rendermanager.setRenderShadow( false );
        rendermanager.renderEntity( armorStand, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false );
        rendermanager.setRenderShadow( true );
        GL11.glPopAttrib();
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableTexture();
        GlStateManager.popMatrix();
    }


}