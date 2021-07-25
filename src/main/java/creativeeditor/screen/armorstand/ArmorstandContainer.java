package creativeeditor.screen.armorstand;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;

public class ArmorstandContainer extends Container {
//    private static final EquipmentSlotType[] VALID_EQUIPMENT_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};

    public ArmorstandContainer(ArmorstandInventory armorstandInventory) {
        super(null, 0);
        PlayerInventory playerInventory = Minecraft.getInstance().player.inventory;
        for (int k = 0; k < 4; ++k) {
            this.addSlot(new Slot(playerInventory, 39 - k, 8, 8 + k * 18));
            this.addSlot(new Slot(armorstandInventory, 5 - k, 152, 8 + k * 18));
        }
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }
        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }

        // Player off-hand
        this.addSlot(new Slot(playerInventory, 40, 77, 62));

        // Equipment hands armor
        this.addSlot(new Slot(armorstandInventory, 0, 83, 8));

        this.addSlot(new Slot(armorstandInventory, 1, 83, 26));
    }


    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }
}
