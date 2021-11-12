package infinityitemeditor.data.tag;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
import infinityitemeditor.data.base.SingularData;
import infinityitemeditor.json.MinecraftHeadsResponse;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TagGameProfile extends SingularData<GameProfile, CompoundNBT> {
    public TagGameProfile(String username) {
        this(new GameProfile(null, username));
    }


    public TagGameProfile(CompoundNBT nbt) {
        this(NBTUtil.readGameProfile(nbt));
    }


    public TagGameProfile(GameProfile profile) {
        super(profile);
    }


    public TagGameProfile(MinecraftProfilePropertiesResponse profile) {
        super(new GameProfile(profile.getId(), profile.getName()));
        data.getProperties().putAll(profile.getProperties());
    }


    public TagGameProfile(MinecraftHeadsResponse profile) {
        super(new GameProfile(profile.getUUID(), profile.getName()));
        data.getProperties().putAll(profile.getProperties());
    }

    public void set(MinecraftHeadsResponse element) {
        data = element.getGameProfile();
    }

    @Override
    public boolean isDefault() {
        return data == null;
    }


    @Override
    public CompoundNBT getNBT() {
        return NBTUtil.writeGameProfile(new CompoundNBT(), data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        IFormattableTextComponent text = new StringTextComponent(data.getName() == null ? "Player" : data.getName());
        if(data.getId() != null){
            text.append(data.getId().toString());
        }
        return text;
    }
}
