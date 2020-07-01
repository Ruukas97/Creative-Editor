package creativeeditor.widgets;

import java.util.ArrayList;

import creativeeditor.screen.CreditsScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.list.ExtendedList;

public class ScrollableList extends Widget {
    private ArrayList<ListElement> list;
    private boolean showTitle = false, showIcons = false, drawBorders = true;
    private int titleBarHeight = 20, scrollbarWidth = 16, scrollButtonHeight = 24;
    
    private int selected;
    

    public ScrollableList(int xStart, int yStart, int xEnd, int yEnd, String title) {
        super( xStart, yStart, xEnd-xStart, yEnd-yStart, title );
    }
    
    /*public ListElement getElementUnderMouse(int mouseX, int mouseY) {
        //AdvancementsScreen 
    }*/
    
    @Override
    public void render( int p_render_1_, int p_render_2_, float p_render_3_ ) {
        super.render( p_render_1_, p_render_2_, p_render_3_ );
    }

    public static interface ListElement extends IGuiEventListener{
        public String getTitle();
        public int getHeight();
        public void render();
    }
}
