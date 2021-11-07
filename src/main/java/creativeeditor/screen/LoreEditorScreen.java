package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataString;
import creativeeditor.screen.widgets.LoreWidget;
import creativeeditor.screen.widgets.ScrollableScissorWindow;
import creativeeditor.screen.widgets.StyledButton;
import creativeeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
        renderColorHelper = true;
        super.init();
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
        setRenderItem(true, 1f, 10);
        scrollWidth = width / 6 * 4;
        int xScroll = (width - scrollWidth) / 2;
        scrollWindow = addButton(new ScrollableScissorWindow(xScroll, height / 4, scrollWidth, height / 4 * 2, new StringTextComponent("Lores")));
        int scrollTop = height / 4;
        addButton(new StyledButton(xScroll - buttonWidth - distanceFromScroll, scrollTop, buttonWidth, 20, I18n.get("gui.loreeditor.addline"), t -> addLine()));
        addButton(new StyledButton(xScroll - buttonWidth - distanceFromScroll, scrollTop + 25, buttonWidth, 20, I18n.get("gui.loreeditor.copy"), t -> copyLoreToClipboard()));
        loreLines.clear();
        List<DataString> l = item.getTag().getDisplay().getLore().get();
        for (int i = 0; i < l.size(); i++) {
            String loreText = "";
            IFormattableTextComponent textFromJson = ITextComponent.Serializer.fromJson(l.get(i).get());
            if (textFromJson != null) {
                loreText = textFromJson.getString();
            }
            loreLines.add(new LoreWidget(i + 1, scrollWidth, font, this, loreText));
        }
        scrollWindow.getWidgets().addAll(loreLines);
        setInitialFocus(scrollWindow);
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
        applyLoreToItem();
        scrollWindow.getWidgets().clear();
        for (int i = 0; i < loreLines.size(); i++) {
            scrollWindow.getWidgets().add(loreLines.get(i).updateCount(i + 1));
        }
    }

    private void addLine() {
        addLine(loreLines.size());
    }

    private void addLine(int i) {
        loreLines.add(i, new LoreWidget(scrollWindow.getWidgets().size() + 1, scrollWidth, minecraft.font, this));
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
    public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) {
        applyLoreToItem();
        return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
    }

    private void applyLoreToItem() {
        item.getTag().getDisplay().getLore().clear();
        for (LoreWidget widget : loreLines) {
            String text = widget.field.getText();
            if (text.isEmpty()) text = "";
            item.getTag().getDisplay().getLore().add(new DataString(ITextComponent.Serializer.toJson(new StringTextComponent(text))));
        }
    }

    @Override
    public void reset(Widget w) {
        item.getTag().getDisplay().getLore().clear();
        init();
    }

    public void moveUp(LoreWidget w) {
        int i = loreLines.indexOf(w);
        loreLines.remove(w);
        loreLines.add(i > 0 ? i - 1 : loreLines.size(), w);
        updateLines();
    }

    public void moveDown(LoreWidget w) {
        int i = loreLines.indexOf(w);
        int size = loreLines.size();
        loreLines.remove(w);
        loreLines.add(i < size - 1 ? i + 1 : 0, w);
        updateLines();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!isInColorWidget((int) mouseX, (int) mouseY)) {
            loreLines.forEach(t -> t.setFocusField(false));
        }
        applyLoreToItem();
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private LoreWidget getFocusedLoreWidget() {
        for (LoreWidget w : loreLines) {
            if (w.isFocused()) return w;
        }
        return null;
    }

    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        if (key2 == 47 && key3 == 2) { // V + ctrl modifier
            String clip = minecraft.keyboardHandler.getClipboard();
            if (!clip.isEmpty() && getFocusedLoreWidget() == null) {
                String[] lines = new String[1];
                lines[0] = clip;
                if (clip.contains("\n")) {
                    lines = clip.split("\n");
                }
                for (int i = 0; i < lines.length; i++) {
                    loreLines.add(new LoreWidget(i + 1, scrollWidth, font, this, lines[i]));
                    updateLines();
                }
                return true;

            }
        }
        if (key1 == 258 || key1 == 264 || key1 == 265) { // tab or key down or key up
            if (handleArrowsAndTab(key1 == 265)) return true;
        }
        if (key2 == 28) { // enter
            if (getFocusedLoreWidget() != null) {
                LoreWidget loreWidget = getFocusedLoreWidget();
                addLine(loreWidget.getCount());
                handleArrowsAndTab(false);
            }

        }
        return super.keyPressed(key1, key2, key3);
    }

    private boolean handleArrowsAndTab(boolean goesDown) {
        if (getFocusedLoreWidget() != null) {
            LoreWidget focused = getFocusedLoreWidget();
            focused.setFocusField(false);
            int i = focused.getCount() - 1;
            int get = goesDown ? i - 1 >= 0 ? i - 1 : loreLines.size() - 1 : i + 1 < loreLines.size() ? i + 1 : 0;
            LoreWidget lw = loreLines.get(get);
            lw.setFocusField(true);
            scrollWindow.setFocused(lw);
            return true;
        }
        return false;
    }


    @Override
    public void onClose() {
        minecraft.keyboardHandler.setSendRepeatsToGui(false);
        super.onClose();
    }

    @Override
    public boolean changeFocus(boolean p_231049_1_) {
        return false;
    }
}
