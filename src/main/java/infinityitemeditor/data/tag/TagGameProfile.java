package infinityitemeditor.data.tag;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
import infinityitemeditor.data.base.SingularData;
import infinityitemeditor.json.MinecraftHeadsResponse;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

public class TagGameProfile extends SingularData<GameProfile, CompoundTag> {
    public TagGameProfile(String username) {
        this(new GameProfile(null, username));
    }


    public TagGameProfile(CompoundTag nbt) {
        this(NbtUtils.readGameProfile(nbt));
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
    public CompoundTag getTag() {
        return NbtUtils.writeGameProfile(new CompoundTag(), data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        IFormattableTextComponent text = new TextComponent(data.getName() == null ? "Player" : data.getName());
//        if(data.getId() != null){
//            text.append(data.getId().toString());
//        }
//        return text;
//    }
}
