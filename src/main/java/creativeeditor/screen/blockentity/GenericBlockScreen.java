package creativeeditor.screen.blockentity;

import creativeeditor.data.DataItem;
import creativeeditor.screen.ParentItemScreen;
import creativeeditor.screen.widgets.StyledTFToggle;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class GenericBlockScreen extends ParentItemScreen {

    private StyledTFToggle toggleLock;


    public GenericBlockScreen(Screen lastScreen, DataItem editing) {
        super(new TranslationTextComponent("gui.genericblock"), lastScreen, editing);
    }


    @Override
    protected void init() {
        super.init();

//        toggleLock = addButton(new StyledTFToggle(10, 10, 10, 10, "", item.getTag().getBlockEntityTag().);
    }


}
