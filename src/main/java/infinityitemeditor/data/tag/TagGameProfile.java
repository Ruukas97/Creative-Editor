package infinityitemeditor.data.tag;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.base.SingularData;
import infinityitemeditor.json.MinecraftHeadsResponse;
import infinityitemeditor.util.ItemRendererUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
        IFormattableTextComponent text = new StringTextComponent(data == null || data.getName() == null ? "Player" : data.getName());
        if (data != null && data.getId() != null) {
            text.append(data.getId().toString());
        }
        return text;
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        ItemStack head = new ItemStack(Items.PLAYER_HEAD);
        if (data != null) {
            NBTUtil.writeGameProfile(head.getOrCreateTagElement("SkullOwner"), data);
        }
        new ItemRendererUtils(mc.getItemRenderer()).renderItem(head, mc, matrix, x, y);
    }
}
