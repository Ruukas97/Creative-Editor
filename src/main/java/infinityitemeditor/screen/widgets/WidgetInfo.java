package infinityitemeditor.screen.widgets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;

@AllArgsConstructor
public class WidgetInfo {
    @Getter
    @Setter
    private int posX, posY, width, height;
    @Getter
    @Setter
    private String text;
    @Getter
    @Setter
    private Button.OnPress trigger;
    @Getter
    @Setter
    private Screen parent;
    @Getter
    @Setter
    private Font font;


    public WidgetInfo withTrigger(Button.OnPress trigger) {
        setTrigger(trigger);
        return this;
    }
}
