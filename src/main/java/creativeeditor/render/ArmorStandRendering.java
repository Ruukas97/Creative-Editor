package creativeeditor.render;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.util.ResourceLocation;

public class ArmorStandRendering {
    public static <E extends ArmorStandItem> void addPropertyOverrides( E stand ) {
        stand.addPropertyOverride( new ResourceLocation( "nobaseplate" ), propertyGetter(TagEntityArmorStand::getNoBasePlate) );
        stand.addPropertyOverride( new ResourceLocation( "showarms" ), propertyGetter(TagEntityArmorStand::getShowArms) );
    }


    private static IItemPropertyGetter propertyGetter( BooleanTag tag ) {
        return ( stack, world, ent ) -> {
            TagEntityArmorStand data = new DataItem( stack ).getTag().getArmorStandTag();
            DataBoolean b = tag.get( data );
            return b.get() ? 1f : 0f;
        };
    }


    private interface BooleanTag {
        DataBoolean get(TagEntityArmorStand tag);
    }
}
