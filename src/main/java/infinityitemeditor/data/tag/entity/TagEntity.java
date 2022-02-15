package infinityitemeditor.data.tag.entity;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.tag.TagItemList;
import infinityitemeditor.screen.container.EquipmentInventory;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public abstract class TagEntity<E extends Entity> implements Data<E, CompoundTag> {
    @Getter
    protected TagItemList armorItems;

    @Getter
    protected TagItemList handItems;

    public TagEntity(TagItemList armorItems, TagItemList handItems) {
        this.armorItems = armorItems;
        this.handItems = handItems;
    }

    public EquipmentInventory getInventory() {
        return new EquipmentInventory(armorItems, handItems);
    }

    @Override
    public boolean isDefault() {
        return handItems.isDefault() && armorItems.isDefault();
    }

    @Override
    public CompoundTag getTag() {
        CompoundTag nbt = new CompoundTag();
        if(!armorItems.isDefault())
            nbt.put("ArmorItems", armorItems.getTag());
        if(!handItems.isDefault())
            nbt.put("HandItems", handItems.getTag());
        return nbt;
    }
}
