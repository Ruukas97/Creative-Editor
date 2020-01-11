package creativeeditor.data.tag;

import javax.annotation.Nonnull;

import creativeeditor.data.base.DataString;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;

public class TagItem extends DataString {
	public TagItem(String value) {
		super(value);
	}

	public TagItem(Item item) {
		this(item != null ? item.getRegistryName().getPath() : null);
	}

	@Nonnull
	public Item getItem() {
		return GameData.getWrapperDefaulted(Item.class).getOrDefault(new ResourceLocation(get()));
	}

	public void setItem(Item item) {
		if (item != null) {
			set(item.getRegistryName().getPath());
		} else {
			set(null);
		}
	}
}
