package creativeeditor.view;

import net.minecraft.client.gui.AbstractGui;

public abstract class AbstractView extends AbstractGui {
	protected int x, y, width, height;
	
	public AbstractView(int x, int y, int width, int height) {
		updatePos(x, y, width, height);
	}
	
	public void updatePos(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void backRender();
	
	public abstract void mainRender();
	
	public abstract void overlayRender();
}
