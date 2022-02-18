package infinityitemeditor.data.tag;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.NumberRangeInt;
import infinityitemeditor.data.base.DataInteger;
import infinityitemeditor.data.tag.block.TagBlockEntity;
import infinityitemeditor.data.version.NBTKeys;
import infinityitemeditor.util.ItemRendererUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Map;

@AllArgsConstructor
public class TagEnchantment implements Data<TagEnchantment, CompoundNBT> {
    @Getter
    @Setter
    protected Data<?, ?> parent;

    @Getter
    @Setter
    private Enchantment enchantment;
    @Getter
    private final NumberRangeInt level;

    public TagEnchantment(INBT nbt) {
        this(nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT());
    }


    public TagEnchantment(CompoundNBT nbt) {
        try {
            ResourceLocation rl = new ResourceLocation(nbt.getString("id"));
            enchantment = GameRegistry.findRegistry(Enchantment.class).getValue(rl);
        } catch (ResourceLocationException e) {
            enchantment = Enchantment.byId(0);
        }

        level = new NumberRangeInt(nbt.getInt("lvl"), 1, Integer.MAX_VALUE);
    }

    public TagEnchantment(Enchantment enchantment, int level) {
        this(enchantment, new NumberRangeInt(level, 1, Integer.MAX_VALUE));
    }

    public TagEnchantment(Enchantment enchantment, NumberRangeInt level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public TagEnchantment(Map.Entry<RegistryKey<Enchantment>, Enchantment> registryEntry) {
        this(registryEntry.getValue(), new NumberRangeInt(1, Integer.MAX_VALUE));
    }


    @Override
    public boolean isDefault() {
        return level.get() == 0;
    }

    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("id", enchantment.delegate.name().toString());
        nbt.putInt("lvl", level.get());
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return enchantment.getFullname(level.get());
    }

    @Override
    public TagEnchantment getData() {
        return this;
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        ItemStack banner = new ItemStack(Items.ENCHANTED_BOOK);
        new ItemRendererUtils(mc.getItemRenderer()).renderItem(banner, mc, matrix, x, y);
    }
}
