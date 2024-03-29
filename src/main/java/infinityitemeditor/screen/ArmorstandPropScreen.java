package infinityitemeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.entity.TagEntityArmorStand;
import infinityitemeditor.screen.widgets.StyledTFToggle;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;

public class ArmorstandPropScreen extends BaseArmorstandScreen {

    ArmorStandEntity armorstand;
    ArrayList<StyledTFToggle> buttonList;

    public ArmorstandPropScreen(Screen lastScreen, DataItem item) {
        super(new TranslationTextComponent("gui.armorstandeditorproperties"), lastScreen, item);
        this.renderItem = false;
    }

    @Override
    public void postInit() {
        super.init();
        buttonList = new ArrayList<>();
        TagEntityArmorStand tagArmor = item.getTag().getArmorStandTag();
        int x1 = 100;
        int y1 = 40;
        buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.get("gui.armorstandeditorproperties.showarms"), tagArmor.getShowArms()));
        buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.get("gui.armorstandeditorproperties.small"), tagArmor.getSmall()));
        buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.get("gui.armorstandeditorproperties.marker"), tagArmor.getMarker()));
        buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.get("gui.armorstandeditorproperties.invisible"), tagArmor.getInvisible()));
        buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.get("gui.armorstandeditorproperties.nobaseplate"), tagArmor.getNoBasePlate()));
        buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.get("gui.armorstandeditorproperties.nogravity"), tagArmor.getNoGravity()));
        int sideY = 0;
        for (StyledTFToggle but : buttonList) {
            int sideX = ((buttonList.indexOf(but) % 2) == 0) ? 120 : 0;
            if (sideX > 0) {
                sideY += 30;
            }
            but.y += sideY;
            but.x += sideX;
            addButton(but);

        }

    }

    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
        if (armorstand != null) {
            drawArmor.drawArmorStand((int) (this.width / 3 * 2.5),
                    (int) (this.height / 5 * 3.8), 70);
        }

    }

    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.backRender(matrix, mouseX, mouseY, p3, color);
        drawArmor.updateArmorStand();
    }
}
