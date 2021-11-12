package infinityitemeditor.data.tag;

import infinityitemeditor.data.base.DataString;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

public class TagItemID extends DataString {
    public TagItemID(INBT value) {
        super(value instanceof StringNBT ? value.getAsString() : "minecraft:air");
    }


    public TagItemID(StringNBT value) {
        super(value);
    }


    public TagItemID(String value) {
        super(value);
    }


    public TagItemID(Item item) {
        this(getIDFromItem(item));
        data = getIDExcludingMC();
    }


    public TagItemID() {
        this(Items.AIR);
    }


    @Nonnull
    public Item getItem() {
        Item item = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(get()));
        return item != null ? item : Items.AIR;
    }

    public String getIDExcludingMC() {
        if (data.startsWith("minecraft:")) {
            return data.substring(10);
        }
        return data;
    }

    public boolean isBlockItem(){
        return getItem() instanceof BlockItem;
    }

    public void setItem(Item item) {
        set(getIDFromItem(item));
    }


    public static String getIDFromItem(Item item) {
        return item != null ? item.getRegistryName().toString() : "minecraft:air";
    }


    @Override
    public boolean isDefault() {
        return false;
    }
}
