package creativeeditor.data.tag;

import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;

import creativeeditor.data.base.SingularData;
import creativeeditor.json.MinecraftHeadsResponse;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;

public class TagGameProfile extends SingularData<GameProfile, CompoundNBT> {
    public TagGameProfile(String username) {
        this( new GameProfile(null, username ) );
    }


    public TagGameProfile(CompoundNBT nbt) {
        this( NBTUtil.readGameProfile( nbt ) );
    }


    public TagGameProfile(GameProfile profile) {
        super( profile );
    }


    public TagGameProfile(MinecraftProfilePropertiesResponse profile) {
        super( new GameProfile( profile.getId(), profile.getName() ) );
        data.getProperties().putAll( profile.getProperties() );
    }


    public TagGameProfile(MinecraftHeadsResponse profile) {
        super( new GameProfile( profile.getUUID(), profile.getName() ) );
        data.getProperties().putAll( profile.getProperties() );
    }

    public void set( MinecraftHeadsResponse element ) {
        data = element.getGameProfile();
    }

    @Override
    public boolean isDefault() {
        return data == null;
    }


    @Override
    public CompoundNBT getNBT() {
        return NBTUtil.writeGameProfile( new CompoundNBT(), data );
    }
}
