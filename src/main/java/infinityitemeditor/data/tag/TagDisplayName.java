package infinityitemeditor.data.tag;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.base.DataTextComponent;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

public class TagDisplayName extends DataTextComponent {
    private boolean returnEmpty = true;
    private @Getter
    final DataItem item;


    public TagDisplayName(StringTag name, DataItem item) {
        this(name.getAsString(), item);
    }


    public TagDisplayName(String name, DataItem item) {
        super(name.equals("") ? new TranslatableComponent(item.getItem().getItem().getDescriptionId()) : MutableComponent.Serializer.fromJson(name));
        returnEmpty = false;
        this.item = item;
    }


    @Override
    public boolean isDefault() {
        if (returnEmpty || getUnformatted().length() == 0 || getUnformatted().equals("Custom Name")) {
            return true;
        }

        return data.getString().equals(getDefault().getString()) || data instanceof TranslatableComponent;
    }


    public void reset() {
        set(getDefault());
    }


    public Component getDefault() {
        NBTKeys keys = NBTKeys.keys;
        returnEmpty = true;
        ItemStack copy = item.getItemStack();
        returnEmpty = false;
        CompoundTag display = copy.getTagElement(keys.tagDisplay());
        if (display != null) {
            display.remove(keys.displayName());
        }
        return copy.getDisplayName();
    }
}
