package infinityitemeditor.tab;

import infinityitemeditor.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TabNearbyBlocks extends TabCreative {
    public TabNearbyBlocks() {
        super("nearbyblocks");
    }


    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        Minecraft mc = Minecraft.getInstance();
        ClientWorld world = mc.level;
        ClientPlayerEntity player = mc.player;
        BlockPos playerPos = new BlockPos(player.position());
        HashMap<Item, Integer> map = new HashMap<>();
        int radius = Config.NEARBYBLOCKS_TAB_RADIUS.get();
        int diameter = radius * 2;
        BlockPos original = playerPos.offset(new Vector3i(-radius, -radius, -radius));

        for (int x = 0; x < diameter; x++) {
            for (int y = 0; y < diameter; y++) {
                if (y > world.dimensionType().logicalHeight() || y < 0) // logicalHeight = actualHeight?
                    continue;

                for (int z = 0; z < diameter; z++) {
                    BlockPos pos = new BlockPos(original).offset(new Vector3i(x, y, z));
                    BlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();
                    Item item = block.asItem();

                    if (block == Blocks.BEDROCK) {
                        continue;
                    }

                    if (block instanceof FlowingFluidBlock) {
                        FlowingFluidBlock fluidBlock = (FlowingFluidBlock) block;
                        FluidState fluidState = fluidBlock.getFluidState(state);
                        if (fluidState.getAmount() != 8) {
                            continue;
                        }
                        Fluid fluid = fluidBlock.getFluid();
                        item = fluid.getBucket();
                    }

                    if (item == Items.AIR) {
                        continue;
                    }

                    if (block.hasTileEntity(state)) {
                        TileEntity te = world.getBlockEntity(pos);
                        ItemStack stack = new ItemStack(item);
                        mc.addCustomNbtData(stack, te);

                        boolean found = false;
                        for (ItemStack ex : items) {
                            if (ex.equals(stack, false)) {
                                found = true;
                                if (ex.getCount() < 64) {
                                    ex.setCount(ex.getCount() + 1);
                                }
                                break;
                            }
                        }

                        if (!found)
                            items.add(stack);

                        continue;
                    }

                    Integer value = map.putIfAbsent(item, 1);
                    if (value != null)
                        map.put(item, value + 1);
                }
            }
        }

        LinkedList<Map.Entry<Item, Integer>> entries = new LinkedList<>(map.entrySet());
        entries.sort((entry1, entry2) -> {
            return entry1.getValue().compareTo(entry2.getValue());
        });

        if (Config.NEARBYBLOCKS_TAB_MULTIPLESTACKS.get()) {
            entries.forEach(entry -> {
                Item item = entry.getKey().asItem();
                int count = entry.getValue();

                if (count <= 64) {
                    items.add(new ItemStack(item, count));
                } else {
                    int div = count / 64;
                    int rem = count % 64;
                    for (int i = 0; i < div; i++) {
                        items.add(new ItemStack(item, 64));
                    }
                    if (rem > 0) {
                        items.add(new ItemStack(item, rem));
                    }
                }
            });
        } else {
            entries.forEach(entry -> {
                Item item = entry.getKey().asItem();
                int count = entry.getValue();
                items.add(new ItemStack(item, Math.min(64, count)));
            });
        }
    }


    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Blocks.PURPLE_GLAZED_TERRACOTTA);
    }
}
