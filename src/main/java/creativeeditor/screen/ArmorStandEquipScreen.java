package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.DataItem;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class ArmorStandEquipScreen extends ParentItemScreen{

	ArmorStandEntity armorstand;
	
	public ArmorStandEquipScreen(Screen lastScreen, DataItem editing, ArmorStandEntity arm) {
		super(new TranslationTextComponent("gui.armorstandequipment"), lastScreen, editing);
		this.armorstand = arm;
		this.renderItem = false;
	}
	
	@Override
	protected void init() {
		super.init();
		
	}
	
	@Override
	public void overlayRender(int mouseX, int mouseY, float p3, Color color) {
		super.overlayRender(mouseX, mouseY, p3, color);
	}
	
	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);
	}
	
	@Override
	public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
		super.backRender(matrix, mouseX, mouseY, p3, color);
	}

}
