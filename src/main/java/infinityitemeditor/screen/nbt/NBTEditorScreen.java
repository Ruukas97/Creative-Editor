package infinityitemeditor.screen.nbt;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.screen.widgets.ScrollableScissorWindow;
import infinityitemeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class NBTEditorScreen extends WindowScreen {
    protected final Data<?, ?> nbt;
    private String key;
    private ScrollableScissorWindow treeView;

    public NBTEditorScreen(Screen lastScreen, Data<?, ?> nbt, String key) {
        super(lastScreen, new TranslationTextComponent("gui.nbt"));
        this.nbt = nbt;
        this.key = key;
    }

    @Override
    protected void init() {
        super.init();
        treeView = addButton(new ScrollableScissorWindow(5, 5, width - 10, height - 10, title));
        NBTNodeControl nbtControl = new NBTNodeControl(nbt, 0, key);
        nbtControl.setExpanded(true);
        treeView.setPadding(0);
        List<Widget> list = new ArrayList<>();
        nbtControl.addChildren(list);
        treeView.getWidgets().add(addButton(nbtControl));
        for (Widget w : list) {
            treeView.getWidgets().add(addButton(w));
        }
//
//        Size screenSize = new Size(width, height);
//
//        if (windows.isEmpty()) {
//            WindowWidget window = new WindowWidget(10, 10, 200, 200 + WindowWidget.TITLEBAR_HEIGHT, new StringTextComponent("Hello"), new StringTextComponent("Testing"), screenSize);
//            WindowWidget child = new WindowWidget(5, 5, 50, 50, new StringTextComponent("World"), new StringTextComponent("Testing"), Size.square(200));
//            child.setColor(new ColorUtils.Color(0xFF00FF99));
//            child.setInsets(EdgeInsets.fromLTRB(0, 0, 2, 1));
//            window.children.add(child);
//            window.setCloseable(w -> {
//                windows.remove(w);
//                buttons.remove(w);
//                children.remove(w);
//            });
//            windows.add(addButton(window));
//        }
//        for (WindowWidget w : windows) {
//            w.setScreenSize(screenSize);
//            //w.setInsets(EdgeInsets.all(6));
//        }
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        return super.keyPressed(key1, key2, key3);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double onX, double onY) {
        return super.mouseDragged(mouseX, mouseY, button, onX, onY);
    }

    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.backRender(matrix, mouseX, mouseY, p3, color);
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
    }

    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);
    }
}
