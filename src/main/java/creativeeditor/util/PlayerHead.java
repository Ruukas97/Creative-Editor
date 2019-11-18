package creativeeditor.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

public class PlayerHead {

	private static ItemStack skull;

	public PlayerHead(ItemStack is) {
		if (is.getItem() == Items.PLAYER_HEAD) {
			this.skull = is;
		}

	}

	public void setTexture(String texture) {
		if (!skull.getTag().contains("Properties")) {
			skull.getTag().put("Properties", new CompoundNBT());
		}
		skull.getTag().putString("Properties", texture);
		System.out.println(skull.getTag().toString());

	}

	public static ItemStack getLatest() {
		return skull;
	}

	public void setUUID() {

	}

	public void setName() {

	}
}
