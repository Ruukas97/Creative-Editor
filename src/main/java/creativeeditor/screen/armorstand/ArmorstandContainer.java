package creativeeditor.screen.armorstand;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArmorstandContainer extends Container {
    private static final EquipmentSlotType[] SLOT_IDS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
    private static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS, PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS, PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE, PlayerContainer.EMPTY_ARMOR_SLOT_HELMET};

    public ArmorstandContainer(ArmorstandInventory armorstandInventory) {
        super(null, 0);
        PlayerInventory playerInventory = Minecraft.getInstance().player.inventory;

        for (int k = 0; k < 4; ++k) {
            final EquipmentSlotType equipmentslottype = SLOT_IDS[k];
            this.addSlot(new Slot(playerInventory, 39 - k, 8, 8 + k * 18) {
                public int getMaxStackSize() {
                    return 1;
                }

                public boolean mayPlace(ItemStack stack) {
                    return stack.canEquip(equipmentslottype, playerInventory.player);
                }

                public boolean mayPickup(PlayerEntity player) {
                    return !getItem().isEmpty() && super.mayPickup(player);
                }

                @OnlyIn(Dist.CLIENT)
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(PlayerContainer.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentslottype.getIndex()]);
                }
            }).index += 5;
        }

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18)).index += 5;
            }
        }
        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142)).index += 5;
        }

        // Player off-hand
        this.addSlot(new Slot(playerInventory, 40, 77, 62)).index += 5;

        // Equipment hands armor
        this.addSlot(new Slot(armorstandInventory, 0, 83, 8));
        this.addSlot(new Slot(armorstandInventory, 1, 83, 26) {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(PlayerContainer.BLOCK_ATLAS, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        for (int k = 0; k < 4; ++k) {
            final EquipmentSlotType equipmentslottype = SLOT_IDS[k];
            this.addSlot(new Slot(armorstandInventory, 5 - k, 152, 8 + k * 18) {
                public boolean mayPickup(PlayerEntity player) {
                    return !getItem().isEmpty() && super.mayPickup(player);
                }

                @OnlyIn(Dist.CLIENT)
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(PlayerContainer.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentslottype.getIndex()]);
                }
            });
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return false;
    }
}
