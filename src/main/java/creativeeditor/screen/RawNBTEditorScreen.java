package creativeeditor.screen;

import creativeeditor.data.DataItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class RawNBTEditorScreen extends ParentScreen {

    private DataItem item;

    public RawNBTEditorScreen(Screen lastScreen, DataItem item) {
        super(new TranslationTextComponent("gui.rawnbt"), lastScreen);
        this.item = item;
    }

    @Override
    protected void init() {
        super.init();

    }
}
