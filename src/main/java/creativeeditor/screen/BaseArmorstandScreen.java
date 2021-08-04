package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.DataItem;
import creativeeditor.util.ColorUtils;
import creativeeditor.util.EntityDrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.text.ITextComponent;

public abstract class BaseArmorstandScreen extends ParentItemScreen {

    private static ArmorStandEntity armorStand = null;
    protected static EntityDrawUtils drawArmor;
    private boolean isInRegion = false;

    public BaseArmorstandScreen(ITextComponent title, Screen lastScreen, DataItem editing) {
        super(title, lastScreen, editing);
        this.renderItem = false;
    }

    @Override
    public void init(Minecraft p_231158_1_, int p_231158_2_, int p_231158_3_) {
        super.init(p_231158_1_, p_231158_2_, p_231158_3_);
        if (armorStand == null) {
            ArmorStandEntity entity = new ArmorStandEntity(minecraft.level, 0, 0, 0);
            armorStand = entity;
            drawArmor = new EntityDrawUtils(armorStand, item);
        }
        postInit();
    }


    protected void postInit() {}

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
        if (armorStand != null) {
            drawArmor.drawArmorStand(armorStand, (int) (this.width / 3 * 2.5), (int) (this.height / 5 * 3.8), 70);
        }
    }


    @Override
    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
        if (isInRegion) {
            drawArmor.addRotation = (int) p_mouseDragged_6_;
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        isInRegion = mouseX > (width / 11 * 8);
        return true;
    }
}
