package creativeeditor.tab;

import com.mojang.serialization.Lifecycle;
import creativeeditor.data.DataItem;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.GameData;

public class TabUnavailable extends TabCreative {

    public TabUnavailable() {
        super( "unavailable" );
    }


    @Override
    public ItemStack makeIcon() {
        return new ItemStack( Items.BARRIER );
    }


    @Override
    public void fillItemList( NonNullList<ItemStack> items ) {
        GameData.getWrapper(Registry.ITEM_REGISTRY, Lifecycle.stable()).forEach(i -> {
            if (i != Items.AIR && (i.getCreativeTabs().isEmpty() || (i.getCreativeTabs().size() == 1 && i.getCreativeTabs().contains( null )))) {
                items.add( new ItemStack( i ) );
            }
        } );
        
        DataItem stand = new DataItem( Items.ARMOR_STAND, 1, new CompoundNBT(), 0 );
        TagEntityArmorStand data = stand.getTag().getArmorStandTag();
        
        data.getShowArms().set( true );
        items.add( stand.getData() );
        data.getNoBasePlate().set( true );
        items.add( stand.getData() );
        data.getShowArms().set( false );
        items.add( stand.getData() );
    }
}
