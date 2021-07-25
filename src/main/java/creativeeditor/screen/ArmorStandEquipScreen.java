package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import creativeeditor.CreativeEditor;
import creativeeditor.screen.armorstand.ArmorstandContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class ArmorStandEquipScreen extends ContainerScreen {

    private Screen lastScreen;
    private float xMouse;
    private float yMouse;
    public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation(CreativeEditor.MODID, "textures/gui/equipment.png");

    public ArmorStandEquipScreen(Screen lastScreen, ArmorstandContainer armorstandContainer) {
        super(armorstandContainer, Minecraft.getInstance().player.inventory, StringTextComponent.EMPTY);
        this.lastScreen = lastScreen;
    }

    @Override
    public void render(MatrixStack matrix, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
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
    protected void slotClicked(Slot p_184098_1_, int p_184098_2_, int p_184098_3_, ClickType p_184098_4_) {
        System.out.println(p_184098_1_ != null ? p_184098_1_.index : "");
        super.slotClicked(p_184098_1_, p_184098_2_, p_184098_3_, p_184098_4_);
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {

    }
}
