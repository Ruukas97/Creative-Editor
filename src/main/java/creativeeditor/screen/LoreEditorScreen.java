package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.DataItem;
import creativeeditor.screen.widgets.LoreWidget;
import creativeeditor.screen.widgets.ScrollableScissorWindow;
import creativeeditor.screen.widgets.StyledButton;
import creativeeditor.util.ColorUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;

public class LoreEditorScreen extends ParentItemScreen {

    private ScrollableScissorWindow scrollWindow;
    private ArrayList<LoreWidget> loreLines = new ArrayList<>();

    private final int buttonWidth = 50;
    private final int distanceFromScroll = 10;

    public LoreEditorScreen(Screen lastScreen, DataItem editing) {
        super(new TranslationTextComponent("gui.loreeditor"), lastScreen, editing);
    }

    @Override
    protected void init() {
        super.init();
        setRenderItem(true, 1f);
        int scrollWidth = width / 6 * 4;
        int xScroll = (width - scrollWidth) / 2;
        scrollWindow = addButton(new ScrollableScissorWindow(xScroll, height / 4, scrollWidth, height / 4 * 2,"Scroll"));
        int scrollTop = height / 4;
        addButton(new StyledButton(xScroll - buttonWidth - distanceFromScroll, scrollTop, buttonWidth, 20, I18n.get("gui.loreeditor.addline"), t -> addLine(font)));
        scrollWindow.getWidgets().addAll(loreLines);
    }

    private void updateLines() {
        scrollWindow.getWidgets().clear();
        scrollWindow.getWidgets().addAll(loreLines);
    }

    private void addLine(FontRenderer font) {
        loreLines.add(new LoreWidget(1, font, this));
        updateLines();
    }

    public void removeLine(LoreWidget widget) {
        loreLines.remove(widget);
        updateLines();
    }


}
