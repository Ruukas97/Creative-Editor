package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataString;
import creativeeditor.screen.widgets.LoreWidget;
import creativeeditor.screen.widgets.ScrollableScissorWindow;
import creativeeditor.screen.widgets.StyledButton;
import creativeeditor.util.ColorUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class LoreEditorScreen extends ParentItemScreen {

    private ScrollableScissorWindow scrollWindow;
    public ArrayList<LoreWidget> loreLines = new ArrayList<>();

    private final int buttonWidth = 50;
    private final int distanceFromScroll = 10;
    private int scrollWidth;

    public LoreEditorScreen(Screen lastScreen, DataItem editing) {
        super(new TranslationTextComponent("gui.loreeditor"), lastScreen, editing);
    }

    @Override
    protected void init() {
        super.init();
        setRenderItem(true, 1f, 10);
        scrollWidth = width / 6 * 4;
        int xScroll = (width - scrollWidth) / 2;
        scrollWindow = addButton(new ScrollableScissorWindow(xScroll, height / 4, scrollWidth, height / 4 * 2, "Scroll"));
        int scrollTop = height / 4;
        addButton(new StyledButton(xScroll - buttonWidth - distanceFromScroll, scrollTop, buttonWidth, 20, I18n.get("gui.loreeditor.addline"), t -> addLine(font)));
        addButton(new StyledButton(xScroll - buttonWidth - distanceFromScroll, scrollTop + 25, buttonWidth, 20, I18n.get("gui.loreeditor.copy"), t -> copyLoreToClipboard()));
        if (loreLines.isEmpty()) {
            List<DataString> l = item.getTag().getDisplay().getLore().get();
            for (int i = 0; i < l.size(); i++) {
                loreLines.add(new LoreWidget(i + 1, scrollWidth, font, this, l.get(i).get()));
            }
        } else {
            ArrayList<LoreWidget> copy = new ArrayList<>(loreLines);
            loreLines.clear();
            for (int i = 0; i < copy.size(); i++) {
                loreLines.add(new LoreWidget(i + 1, scrollWidth, font, this, copy.get(i).getText()));
            }
        }
        scrollWindow.getWidgets().addAll(loreLines);

    }

    private void copyLoreToClipboard() {
        ArrayList<String> loreList = new ArrayList<>();
        for (LoreWidget widget : loreLines) {
            String text = widget.field.getText();
            if (text.isEmpty()) text = " ";
            loreList.add(text);
        }
        minecraft.keyboardHandler.setClipboard(String.join("\n", loreList));
    }

    private void updateLines() {
        scrollWindow.getWidgets().clear();
        for (int i = 0; i < loreLines.size(); i++) {
            scrollWindow.getWidgets().add(loreLines.get(i).updateCount(i + 1));
        }
    }

    private void addLine(FontRenderer font) {
        loreLines.add(new LoreWidget(scrollWindow.getWidgets().size() + 1, scrollWidth, font, this));
        updateLines();
    }

    public void removeLine(LoreWidget widget) {
        loreLines.remove(widget);
        updateLines();
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
    }

    @Override
    public void tick() {
        super.tick();
        loreLines.forEach(t -> t.field.tick());
    }

    @Override
    public void save(Widget w) {
        item.getTag().getDisplay().getLore().clear();
        for (LoreWidget widget : loreLines) {
            String text = widget.field.getText();
            if (text.isEmpty()) text = " ";
            item.getTag().getDisplay().getLore().add(new DataString(text));
        }
        super.save(w);
    }

    public void moveUp(LoreWidget w) {
        int i = loreLines.indexOf(w);
        loreLines.remove(w);
        loreLines.add(i > 0 ? i - 1 : 0, w);
        updateLines();
    }

    public void moveDown(LoreWidget w) {
        int i = loreLines.indexOf(w);
        int size = loreLines.size();
        loreLines.remove(w);
        loreLines.add(i < size - 1 ? i + 1 : size - 1, w);
        updateLines();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        loreLines.forEach(t -> t.setFocusField(false));
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        if (key1 == 258) {
            System.out.println("258");
            for (int i = 0; i < loreLines.size(); i++) {
                if (loreLines.get(i).isFocused()) {
                    loreLines.get(i).setFocusField(false);
                    loreLines.get(i + 1 < loreLines.size() ? i + 1 : 0).setFocusField(true);
                    return true;
                }
            }
        }
        return super.keyPressed(key1, key2, key3);
    }
}
