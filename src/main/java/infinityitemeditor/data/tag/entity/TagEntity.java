package infinityitemeditor.data.tag.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.tag.TagItemList;
import infinityitemeditor.screen.container.EquipmentInventory;
import infinityitemeditor.util.ItemRendererUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;

public abstract class TagEntity<E extends Entity> implements Data<E, CompoundNBT> {
    @Getter
    @Setter
    protected Data<?, ?> parent;

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
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        if (!armorItems.isDefault())
            nbt.put("ArmorItems", armorItems.getNBT());
        if (!handItems.isDefault())
            nbt.put("HandItems", handItems.getNBT());
        return nbt;
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        ItemStack head = new ItemStack(Items.ZOMBIE_HEAD);
        new ItemRendererUtils(mc.getItemRenderer()).renderItem(head, mc, matrix, x, y);
    }
}
