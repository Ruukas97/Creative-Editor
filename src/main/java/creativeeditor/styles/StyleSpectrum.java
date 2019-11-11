package creativeeditor.styles;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtils;
import creativeeditor.widgets.CEWButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;

public class StyleSpectrum extends Style {
	public StyleSpectrum() {
		super(null);
		buttonColor = new SpectrumButtonColor(this);
	}

	private int spectrumTick = 0;

	@Override
	public Color getMainColor() {
		return ColorUtils.hsvToRGB(spectrumTick/5000f, 1f, 1f);
	}
	
	@Override
	public void tick() {
		spectrumTick = (spectrumTick+1)%5000;
	}

	@Override
	public void renderBackground(ParentScreen screen) {
		GuiUtils.fillGradient(screen, 0, 0, screen.width, screen.height, -1072689136, -804253680);
	}

	@Override
	public void renderButton(CEWButton button, int mouseX, int mouseY, float alpha) {
		Minecraft mc = Minecraft.getInstance();
		FontRenderer font = mc.fontRenderer;
		int j = button.getFGColor();
		GuiUtils.drawFrame(button.x, button.y, button.x+button.getWidth(), button.y+button.getHeight(), 1, new Color(j));
		button.renderBg(mc, mouseX, mouseY);
		button.drawCenteredString(font, button.getMessage(), button.x + button.getWidth() / 2, button.y + (button.getHeight() - 8) / 2, j | MathHelper.ceil(alpha * 255.0F) << 24);		
	}
	
	public static class SpectrumButtonColor extends ButtonColor {
		private StyleSpectrum specStyle;
		
		public SpectrumButtonColor(StyleSpectrum specStyle) {
			this.specStyle = specStyle;
		}
		
		@Override
		public Color standard() {
			return specStyle.getMainColor();
		}
		
		@Override
		public Color inactive() {
			return new Color(255, 120, 120, 120);
		}
		
		@Override
		public Color hovered() {
			return ColorUtils.multiplyColor(specStyle.getMainColor(), new ColorUtils.Color(255, 170, 170, 170));
		}
	}
}
