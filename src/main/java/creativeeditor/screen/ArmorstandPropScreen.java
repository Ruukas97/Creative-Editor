package creativeeditor.screen;

import java.util.ArrayList;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import creativeeditor.screen.widgets.StyledTFToggle;
import creativeeditor.util.ArmorStandDrawUtils;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class ArmorstandPropScreen extends ParentItemScreen {

	ArmorStandEntity armorstand;
	ArrayList<StyledTFToggle> buttonList;
	private ArmorStandDrawUtils drawArmor;
	
	public ArmorstandPropScreen(Screen lastScreen, DataItem item, ArmorStandEntity arm) {
		super(new TranslationTextComponent("gui.armorstandeditorproperties"), lastScreen, item);
		this.renderItem = false;
		this.armorstand = arm;
		drawArmor = new ArmorStandDrawUtils(armorstand, item);
	}

	@Override
	public void init() {
		super.init();
		buttonList = new ArrayList<StyledTFToggle>();
		TagEntityArmorStand tagArmor = drawArmor.getStandData();
		int x1 = 100;
		int y1 = 40;
		buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.format("gui.armorstandeditorproperties.showarms"), tagArmor.getShowArms()));
		buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.format("gui.armorstandeditorproperties.small"), tagArmor.getSmall()));
		buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.format("gui.armorstandeditorproperties.marker"), tagArmor.getMarker()));
		buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.format("gui.armorstandeditorproperties.invisible"), tagArmor.getInvisible()));
		buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.format("gui.armorstandeditorproperties.nobaseplate"), tagArmor.getNoBasePlate()));
		buttonList.add(new StyledTFToggle(x1, y1, 100, 20, I18n.format("gui.armorstandeditorproperties.nogravity"), tagArmor.getNoGravity()));
		int sideY = 0;
		for (StyledTFToggle but : buttonList) {
			int sideX = ((buttonList.indexOf(but) % 2) == 0) ? 120 : 0;
			if(sideX > 0) {
				sideY += 30;
			}
			but.y += sideY;
			but.x += sideX;
			addButton(but);

		}

	}

	@Override
	public void overlayRender(int mouseX, int mouseY, float p3, Color color) {
		super.overlayRender(mouseX, mouseY, p3, color);
	}

	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);
		if (armorstand != null) {
			drawArmor.drawArmorStand(armorstand, (int) (this.width / 3 * 2.5),
					(int) (this.height / 5 * 3.8), 70);
		}
		
	}

	@Override
	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		super.backRender(mouseX, mouseY, p3, color);
		drawArmor.updateArmorStand();
	}
}
