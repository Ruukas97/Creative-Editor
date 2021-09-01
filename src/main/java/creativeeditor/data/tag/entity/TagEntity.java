package creativeeditor.data.tag.entity;

import creativeeditor.data.Data;
import creativeeditor.data.tag.TagItemList;
import creativeeditor.screen.container.EquipmentInventory;
import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

public abstract class TagEntity<E extends Entity> implements Data<E, CompoundNBT> {
    @Getter
    private TagItemList armorItems;

    @Getter
    private TagItemList handItems;

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


}
