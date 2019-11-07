package creative.editor.screen;

import creative.editor.nbt.NBTItemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class ParentScreen extends Screen {
	protected final Screen lastScreen;
	protected NBTItemBase editing;

	// Back, reset, drop, save button
	boolean hasSave = false;
	boolean closing = false;

	public ParentScreen(ITextComponent title, Screen lastScreen, NBTItemBase editing) {
		super(title);
		this.lastScreen = lastScreen;
		this.editing = editing;
	}
	
	@Override
	protected void init() {
		super.init();
		if(!closing)
			minecraft.mainWindow.setGuiScale(3.0d);
	}
	
	@Override
	public void resize(Minecraft mc, int width, int height) {
		super.resize(mc, width, height);
	}
	
	@Override
	public void onClose() {
		super.onClose();
		closing = true;
	    int i = minecraft.mainWindow.calcGuiScale(minecraft.gameSettings.guiScale, minecraft.getForceUnicodeFont());
	    minecraft.mainWindow.setGuiScale(i);
	}

	@Override
	public boolean keyPressed(int key1, int key2, int key3) {
		return super.keyPressed(key1, key2, key3);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float p3) {
		super.render(mouseX, mouseY, p3);
	}
}
