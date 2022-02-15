package infinityitemeditor.screen.widgets;

import infinityitemeditor.screen.ParentScreen;
import net.minecraft.client.gui.widget.list.ExtendedList;

public class ListWidget extends ExtendedList {
    private final int listWidth;

    private final ParentScreen parent;

    @SuppressWarnings("resource")
    public ListWidget(ParentScreen parent, int listWidth) {
        super(parent.getMinecraft(), listWidth, parent.height, 32, parent.height - 91 + 4, parent.getFont().lineHeight * 2 + 8);
        this.parent = parent;
        this.listWidth = listWidth;
    }


    @Override
    protected int getScrollbarPosition() {
        return this.listWidth;
    }


    @Override
    public int getRowWidth() {
        return this.listWidth;
    }


}
