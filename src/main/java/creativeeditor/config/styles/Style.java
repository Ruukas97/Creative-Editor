package creativeeditor.config.styles;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.CEWButton;

public abstract class Style {
	protected ButtonColor buttonColor;
	
	public Style(ButtonColor buttonColor) {
		this.buttonColor = buttonColor;
	}
	
	private static Style currentStyle = new StyleSpectrum();

	public static Style getCurrentStyle() {
		return currentStyle;
	}

	public static void setCurrentStyle(Style style) {
		if (style != null)
			currentStyle = style;
	}

	public abstract Color getMainColor();
	
	public abstract void renderBackground(ParentScreen screen);
	
	public abstract void renderButton(CEWButton button, int mouseX, int mouseY, float alpha);
	
	public void tick() {
	}
	
	public ButtonColor getButtonColor() {
		return this.buttonColor;
	}
	
	public static abstract class ButtonColor {
		public abstract Color hovered();
		public abstract Color standard();
		public abstract Color inactive();
	}
	
	public static class StaticButtonColor extends ButtonColor{
		private static final Color[] colors = new Color[3];
		
		public StaticButtonColor(Color hovered, Color standard, Color inactive) {
			colors[0] = hovered;
			colors[1] = standard;
			colors[2] = inactive;
		}
		
		public StaticButtonColor(int hovered, int standard, int inactive) {
			this(new Color(hovered), new Color(standard), new Color(inactive));
		}

		@Override
		public Color hovered() {
			return colors[0];
		}

		@Override
		public Color standard() {
			return colors[1];
		}

		@Override
		public Color inactive() {
			return colors[2];
		}
	}
}
