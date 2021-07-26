package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import creativeeditor.CreativeEditor;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import creativeeditor.screen.container.EquipmentContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;

public class ArmorStandEquipScreen extends ContainerScreen<EquipmentContainer> {
    public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation(CreativeEditor.MODID, "textures/gui/equipment.png");

    private Screen lastScreen;
    private float xMouse;
    private float yMouse;
    private TagEntityArmorStand armorStandTag;
    private ArmorStandEntity entity;


    public ArmorStandEquipScreen(Screen lastScreen, TagEntityArmorStand armorStandTag) {
        super(new EquipmentContainer(armorStandTag.getInventory()), Minecraft.getInstance().player.inventory, StringTextComponent.EMPTY);
        this.lastScreen = lastScreen;
        this.armorStandTag = armorStandTag;
        this.entity = armorStandTag.getData();
    }

    @Override
    public void render(MatrixStack matrix, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        for(int i=0; i<2; i++){
            if(armorStandTag.getHandItems().get()[i] != null){
                entity.setItemSlot(EquipmentSlotType.byTypeAndIndex(EquipmentSlotType.Group.HAND, i), armorStandTag.getHandItems().get()[i].getItemStack());
            }
        }

        for(int i=0; i<4; i++){
            if(armorStandTag.getArmorItems().get()[i] != null){
                entity.setItemSlot(EquipmentSlotType.byTypeAndIndex(EquipmentSlotType.Group.ARMOR, i), armorStandTag.getArmorItems().get()[i].getItemStack());
            }
        }

        super.render(matrix, p_230430_2_, p_230430_3_, p_230430_4_);
        this.xMouse = (float)p_230430_2_;
        this.yMouse = (float)p_230430_3_;
    }

    @Override
    protected void renderBg(MatrixStack matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(INVENTORY_LOCATION);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrix, i, j, 0, 0, this.imageWidth, this.imageHeight);
        InventoryScreen.renderEntityInInventory(i + 51, j + 75, 30, (float)(i + 51) - this.xMouse, (float)(j + 75 - 50) - this.yMouse, this.minecraft.player);
        renderArmorStand(i + 126, j + 75, 30, (float)(i + 126) - this.xMouse, (float)(j + 75 - 50) - this.yMouse);
    }

    public void renderArmorStand(int x, int y, int scale, float lookX, float lookY) {
        float lvt_6_1_ = (float)Math.atan(lookX / 40.0F);
        float lvt_7_1_ = (float)Math.atan(lookY / 40.0F);
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)x, (float)y, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack lvt_8_1_ = new MatrixStack();
        lvt_8_1_.translate(0.0D, 0.0D, 1000.0D);
        lvt_8_1_.scale((float)scale, (float)scale, (float)scale);
        Quaternion lvt_9_1_ = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion lvt_10_1_ = Vector3f.XP.rotationDegrees(lvt_7_1_ * 20.0F);
        lvt_9_1_.mul(lvt_10_1_);
        lvt_8_1_.mulPose(lvt_9_1_);
        float lvt_11_1_ = entity.yBodyRot;
        float lvt_12_1_ = entity.yRot;
        float lvt_13_1_ = entity.xRot;
        float lvt_14_1_ = entity.yHeadRotO;
        float lvt_15_1_ = entity.yHeadRot;
        entity.yBodyRot = 180.0F + lvt_6_1_ * 20.0F;
        entity.yRot = 180.0F + lvt_6_1_ * 40.0F;
        entity.xRot = -lvt_7_1_ * 20.0F;
        entity.yHeadRot = entity.yRot;
        entity.yHeadRotO = entity.yRot;
        EntityRendererManager lvt_16_1_ = Minecraft.getInstance().getEntityRenderDispatcher();
        lvt_10_1_.conj();
        lvt_16_1_.overrideCameraOrientation(lvt_10_1_);
        lvt_16_1_.setRenderShadow(false);
        IRenderTypeBuffer.Impl lvt_17_1_ = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            lvt_16_1_.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, lvt_8_1_, lvt_17_1_, 15728880);
        });
        lvt_17_1_.endBatch();
        lvt_16_1_.setRenderShadow(true);
        entity.yBodyRot = lvt_11_1_;
        entity.yRot = lvt_12_1_;
        entity.xRot = lvt_13_1_;
        entity.yHeadRotO = lvt_14_1_;
        entity.yHeadRot = lvt_15_1_;
        RenderSystem.popMatrix();
    }

    @Override
    public void onClose() {
        minecraft.setScreen(lastScreen);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    @Override
    protected void slotClicked(Slot slot, int index, int button, ClickType clickType) {
        if (slot != null) {
            index = slot.index;

            if (!(slot.container instanceof PlayerInventory)) {
                menu.clicked(index, button, clickType, minecraft.player);
                return;
            }
        }

        minecraft.gameMode.handleInventoryMouseClick(menu.containerId, index, button, clickType, minecraft.player);
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {

    }
}
