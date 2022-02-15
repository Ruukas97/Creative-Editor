package infinityitemeditor.screen.widgets;

import net.minecraft.util.text.TextComponent;

public class StyledOptionSwitcher extends StyledButton {
    private int index = 0;
    private final Option[] options;

    public StyledOptionSwitcher(WidgetInfo info, Option[] options) {
        super(info.withTrigger(onPressed));
        this.options = options;
        setMessage(getOption().getName());
    }

    public StyledOptionSwitcher(int x, int y, int width, int height, Option[] options) {
        super(x, y, width, height, options[0].getName(), onPressed);
        this.options = options;
    }

    public StyledOptionSwitcher(int x, int y, int width, int height, Option[] options, Option option) {
        this(x, y, width, height, options);
        for (int i = 0, optionsLength = options.length; i < optionsLength; i++) {
            Option o = options[i];
            if(o == option){
                index = i;
                break;
            }
        }
    }

    public interface Option {
        TextComponent getName();
    }

    private static final Button.OnPress onPressed = button -> {
        if (button instanceof StyledOptionSwitcher) {
            StyledOptionSwitcher switcher = (StyledOptionSwitcher) button;
            switcher.next();
        }
    };

    public void next() {
        index = (index + 1) % options.length;
        setMessage(getOption().getName());
    }

    public Option getOption(){
        return options[index];
    }
}
