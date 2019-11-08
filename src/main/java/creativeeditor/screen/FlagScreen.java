package creativeeditor.screen;

import creativeeditor.nbt.NBTItemBase;
import creativeeditor.util.CEStringUtils;
import creativeeditor.util.HideFlagUtils;
import creativeeditor.widgets.CEWButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class FlagScreen extends ParentScreen {

	public FlagScreen(ITextComponent title, Screen lastScreen, NBTItemBase editing) {
		super(new StringTextComponent(I18n.format("gui.itemflag")), lastScreen, editing);
	}

	@Override
	protected void init() {
		int extraH = 0;
		int extraW = 0;
		int amount = HideFlagUtils.Flags.values().length;
		for (int i = 0; i < amount; i++) {

			//after 50% of the buttons are set to the left side, the rest will go to the right side
			if (i == ((int) amount / 2)) {
				extraW = 160;
				extraH = 0;
			}
			//later: make this a toggle button
			this.addButton(new CEWButton(this.width / 4 - 20 + extraW, this.height / 7 * 2 - 10 + extraH, 120, 20,
					CEStringUtils.uppercaseFirstLowercaseRest(HideFlagUtils.Flags.values()[i].name()), null));
			//adds space in between buttons
			extraH += 30;
		}
		this.addButton(new CEWButton(this.width / 2 - 70, this.height / 7 * 4, 140, 20, "Switch all", null));
		super.init();
	}

	@Override
	public void render(int mouseX, int mouseY, float p3) {
		super.render(mouseX, mouseY, p3);
	}
}
