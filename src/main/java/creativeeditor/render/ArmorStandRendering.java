package creativeeditor.render;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.util.ResourceLocation;

public class ArmorStandRendering {
    public static <E extends ArmorStandItem> void addPropertyOverrides( E stand ) {
        stand.addPropertyOverride( new ResourceLocation( "nobaseplate" ), propertyGetter( ( at ) -> {
            return at.getNoBasePlate();
        } ) );
        stand.addPropertyOverride( new ResourceLocation( "showarms" ), propertyGetter( ( at ) -> {
            return at.getShowArms();
        } ) );
    }


    private static IItemPropertyGetter propertyGetter( BooleanTag tag ) {
        return ( stack, world, ent ) -> {
            TagEntityArmorStand data = new DataItem( stack ).getTag().getArmorStandTag();
            DataBoolean b = tag.get( data );
            return b.get() ? 1f : 0f;
        };
    }


    private static interface BooleanTag {
        public DataBoolean get( TagEntityArmorStand tag );
    }
}
