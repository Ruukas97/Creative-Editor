package infinityitemeditor.styles;

import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.widget.Widget;

public abstract class StyleBase implements Style {
    protected ButtonColor buttonColor;
    private static final Color color = new Color(100, 100, 100);


    public StyleBase(ButtonColor buttonColor) {
        this.buttonColor = buttonColor;
    }


    @Override
    public Color getMainColor() {
        return color.copy();
    }

    @Override
    public void update() {
    }


    @Override
    public Color getFGColor(Widget widget) {
        return getFGColor(widget.active, widget.isHovered());
    }


    @Override
    public Color getFGColor(boolean active, boolean hovered) {
        if (!active)
            return buttonColor.inactive();
        if (!hovered)
            return buttonColor.hovered();
        return buttonColor.standard();
    }


    public static class StaticButtonColor implements ButtonColor {
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
