package creativeeditor.tab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import creativeeditor.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class TabNearbyBlocks extends TabCreative {
    public TabNearbyBlocks() {
        super( "nearbyblocks" );
    }


    @Override
    public void fill( NonNullList<ItemStack> items ) {
        Minecraft mc = Minecraft.getInstance();
        ClientWorld world = mc.world;
        ClientPlayerEntity player = mc.player;
        BlockPos playerPos = new BlockPos( player );
        HashMap<Item, Integer> map = new HashMap<>();
        int radius = Config.NEARBYBLOCKS_TAB_RADIUS.get();
        int diameter = radius * 2;
        BlockPos original = playerPos.add( new Vec3i( -radius, -radius, -radius ) );

        for (int x = 0; x < diameter; x++) {
            for (int y = 0; y < diameter; y++) {
                if (y > world.getActualHeight() || y < 0)
                    continue;

                for (int z = 0; z < diameter; z++) {
                    BlockPos pos = new BlockPos( original ).add( new Vec3i( x, y, z ) );
                    Block block = world.getBlockState( pos ).getBlock();
                    Item item = block.asItem();

                    if (item == Items.AIR) {
                        continue;
                    }

                    Integer value = map.putIfAbsent( item, 1 );
                    if (value != null)
                        map.put( item, value + 1 );
                }
            }
        }

        LinkedList<Map.Entry<Item, Integer>> entries = new LinkedList<>( map.entrySet() );
        entries.sort( ( entry1, entry2 ) -> {
            return entry1.getValue().compareTo( entry2.getValue() );
        } );

        if (Config.NEARBYBLOCKS_TAB_MULTIPLESTACKS.get()) {
            entries.forEach( entry -> {
                Item item = entry.getKey().asItem();
                int count = entry.getValue();

                if (count <= 64) {
                    items.add( new ItemStack( item, count ) );
                }
                else {
                    int div = count / 64;
                    int rem = count % 64;
                    for (int i = 0; i < div; i++) {
                        items.add( new ItemStack( item, 64 ) );
                    }
                    if (rem > 0) {
                        items.add( new ItemStack( item, rem ) );
                    }
                }
            } );
        }
        else {
            entries.forEach( entry -> {
                Item item = entry.getKey().asItem();
                int count = entry.getValue();
                items.add( new ItemStack( item, Math.min( 64, count ) ) );
            } );
        }
    }


    @Override
    public ItemStack createIcon() {
        return new ItemStack( Blocks.PURPLE_GLAZED_TERRACOTTA );
    }
}
