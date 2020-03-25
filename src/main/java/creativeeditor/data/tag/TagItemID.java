package creativeeditor.data.tag;

import javax.annotation.Nonnull;

import creativeeditor.data.base.DataString;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;

public class TagItemID extends DataString {
	public TagItemID(String value) {
		super(value);
	}

	public TagItemID(Item item) {
		this(getIDFromItem(item));
	}

	public TagItemID() {
		this(Items.AIR);
	}

	@Nonnull
	public Item getItem() {
		return GameData.getWrapperDefaulted(Item.class).getOrDefault(new ResourceLocation(get()));
	}

	public void setItem(Item item) {
		set(getIDFromItem(item));
	}
	
	public static String getIDFromItem(Item item) {
		return item != null ? item.getRegistryName().getPath() : "minecraft:air";
	}
	
	@Override
	public boolean isDefault() {
		return false;
	}
}
