package creativeeditor.view;

import creativeeditor.screen.ParentItemScreen;

public class ItemView extends AbstractView {
    public ParentItemScreen parent;


    public ItemView(int x, int y, int width, int height, ParentItemScreen parent) {
        super( x, y, width, height );
        this.parent = parent;
    }


    @Override
    public void backRender() {
    }


    @Override
    public void mainRender() {
    }


    @Override
    public void overlayRender() {
    }
}
