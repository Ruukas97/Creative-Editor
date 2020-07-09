package creativeeditor.screen.widgets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button.IPressable;

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
    private IPressable trigger;
    @Getter
    @Setter
    private Screen parent;


    public WidgetInfo withTrigger( IPressable trigger ) {
        setTrigger( trigger );
        return this;
    }
}
