package creativeeditor.creativetabs;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class UnavailableTab extends ItemGroup {

	public UnavailableTab(String label) {
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Items.BARRIER);
	}

	
	
	

}
