package creativeeditor.screen;

import java.util.List;

import com.google.common.collect.Lists;

import creativeeditor.styles.Style;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.StyleTextField;
import creativeeditor.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;

public class ParentScreen extends Screen {
	protected final Screen lastScreen;
	protected Minecraft mc;
	protected List<Widget> renderWidgets = Lists.newArrayList();;

	public ParentScreen(ITextComponent title, Screen lastScreen) {
		super(title);
		this.lastScreen = lastScreen;
		this.mc = Minecraft.getInstance();
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	public void resize(Minecraft mc, int width, int height) {
		super.resize(mc, width, height);
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public boolean keyPressed(int key1, int key2, int key3) {
		return super.keyPressed(key1, key2, key3);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	public int getBlitOffset() {
		return blitOffset;
	}

	@Override
	@Deprecated
	public void render(int mouseX, int mouseY, float p3) {
		Color color = Style.getCurrentStyle().getMainColor();
		backRender(mouseX, mouseY, p3, color);
		mainRender(mouseX, mouseY, p3, color);
		overlayRender(mouseX, mouseY, p3, color);
		Style.getCurrentStyle().tick();
	}

	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		Style.getCurrentStyle().renderBackground(this);

		// Frame
		GuiUtils.drawFrame(5, 5, width - 5, height - 5, 1, color);

		// GUI Title
		drawCenteredString(font, getTitle().getFormattedText(), width / 2, 9, color.getInt());
		int sWidthHalf = font.getStringWidth(getTitle().getFormattedText()) / 2 + 3;

		// Title underline
		AbstractGui.fill(width / 2 - sWidthHalf, 20, width / 2 + sWidthHalf, 21, color.getInt());
	}

	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		buttons.forEach(b -> {
			b.render(mouseX, mouseY, p3);
		});

		renderWidgets.forEach(w -> {
			w.render(mouseX, mouseY, p3);
		});

	}

	@Override
	public void tick() {
		renderWidgets.forEach(w -> {
			if (w instanceof StyleTextField) {
				((StyleTextField) w).tick();
			}
		});
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		renderWidgets.forEach(w -> {
			if (w instanceof StyleTextField) {
				((StyleTextField) w).mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
			}
		});
		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}

	/**
	 * Should always be called last in render, but only once.
	 */
	public void overlayRender(int mouseX, int mouseY, float p3, Color color) {
	}
}
