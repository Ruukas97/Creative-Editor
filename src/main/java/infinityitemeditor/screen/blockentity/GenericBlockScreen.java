package infinityitemeditor.screen.blockentity;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.screen.ParentItemScreen;
import infinityitemeditor.screen.widgets.StyledTFToggle;
import infinityitemeditor.screen.widgets.StyledTextField;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class GenericBlockScreen extends ParentItemScreen {

    private StyledTextField nbtField;
    private StyledTFToggle nbtLockToggle;
    private DataBoolean locked;


    public GenericBlockScreen(Screen lastScreen, DataItem editing) {
        super(new TranslationTextComponent("gui.genericblock"), lastScreen, editing);
    }


    @Override
    protected void init() {
        super.init();
        int fieldWidth = this.width / 3 * 1;
        int fieldHeight = 20;
        int fieldY = (this.height - fieldHeight) / 2;
        locked = new DataBoolean(false);
        nbtField = new StyledTextField(font, (this.width - fieldWidth) / 2, fieldY, fieldWidth, fieldHeight, "nbt");
        nbtLockToggle = new StyledTFToggle(10, 10, fieldHeight, fieldHeight, "", locked);
        nbtField.setMaxStringLength(9999);
        renderWidgets.add(nbtField);
    }


}
