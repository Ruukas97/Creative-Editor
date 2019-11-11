package creativeeditor.creativetabs;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.GameData;

public class TabUnavailable extends ItemGroup {

	public TabUnavailable() {
		super("unavailable");
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Items.BARRIER);
	}
	
	@Override
	public void fill(NonNullList<ItemStack> items) {
		super.fill(items);
		
		GameData.getWrapper(Item.class).forEach(i -> {
			if(i.getCreativeTabs().isEmpty()) {
				items.add(new ItemStack(i));
			}
		});
	}
	
}
