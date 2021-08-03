package creativeeditor.screen;

import creativeeditor.data.DataItem;
import creativeeditor.screen.widgets.StyledTextButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class PotionEditorScreen extends ParentItemScreen {

    public PotionEditorScreen(Screen lastScreen, DataItem editing) {
        super(new TranslationTextComponent("gui.potion"), lastScreen, editing);
    }

    @Override
    protected void init() {
        super.init();
        addButton(new StyledTextButton(width / 2 - 40, 141, 80, I18n.get("gui.potion.color"), t -> minecraft.setScreen(new ColorScreen(this, item, item.getTag().getPotionColor(), false))));

    }




}
