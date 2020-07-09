package creativeeditor.screen.widgets;

import creativeeditor.screen.ParentScreen;
import net.minecraft.client.gui.widget.list.ExtendedList;

public class ListWidget extends ExtendedList<ListWidget.ListEntry> {
    private final int listWidth;

    private ParentScreen parent;

    @SuppressWarnings( "resource" )
    public ListWidget(ParentScreen parent, int listWidth) {
        super( parent.getMinecraftInstance(), listWidth, parent.height, 32, parent.height - 91 + 4, parent.getFontRenderer().FONT_HEIGHT * 2 + 8 );
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


    @Override
    protected void renderBackground() {
        this.parent.renderBackground();
    }


    class ListEntry extends ExtendedList.AbstractListEntry<ListEntry> {
        @Override
        public void render( int p_render_1_, int p_render_2_, int p_render_3_, int p_render_4_, int p_render_5_, int p_render_6_, int p_render_7_, boolean p_render_8_, float p_render_9_ ) {

        }
    }
}
