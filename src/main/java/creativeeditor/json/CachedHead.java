package creativeeditor.json;

import creativeeditor.data.DataItem;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;

public class CachedHead {
    @Getter
    private final MinecraftHeadsResponse data;
    @Getter
    private boolean skinLoaded = false;
    @Getter
    private ResourceLocation skin = null;


    public CachedHead(MinecraftHeadsResponse data) {
        this.data = data;
    }


    public void loadTexture() {
        if (skinLoaded)
            return;
        synchronized (this) {
            if (!skinLoaded) {
                skinLoaded = true;
                Minecraft mc = Minecraft.getInstance();
                mc.getSkinManager().loadProfileTextures( data.getGameProfile(), null, false );
            }
        }
    }


    public ItemStack getItemStack() {
        ItemStack head = new ItemStack( Items.PLAYER_HEAD );
        head.getOrCreateChildTag( "display" ).putString( "Name", data.getName() );
        NBTUtil.writeGameProfile( head.getOrCreateChildTag( "SkullOwner" ), data.getGameProfile() );
        return head;
    }


    public DataItem getItem() {
        DataItem head = new DataItem( new ItemStack( Items.PLAYER_HEAD ) );
        head.getDisplayNameTag().set( data.getName() );
        head.getTag().getSkullOwner().set( data );
        return head;
    }
}
