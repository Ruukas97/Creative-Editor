package creativeeditor.widgets;

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CountSlider extends AbstractSlider {
	protected final Slidable onSlide;

	public CountSlider(int xIn, int yIn, int widthIn, int heightIn, double valueIn, Slidable onSlide) {
		super(xIn, yIn, widthIn, heightIn, valueIn);
		this.onSlide = onSlide;
	}
	
	// Can't be 64 cause slider will be used for other purposes
	public int getCount() {
		return (int) (this.value * 64);
	}
	

	@Override
	protected void updateMessage() {
		this.setMessage("" + getCount());
	}

	@Override
	protected void applyValue() {
		onSlide.onSlide(this);
	}

	@OnlyIn(Dist.CLIENT)
	public interface Slidable {
		void onSlide(CountSlider slider);
	}
}
