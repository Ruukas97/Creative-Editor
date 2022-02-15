package infinityitemeditor.mixin;

import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;

@Mixin({ChatFormatting.class})
public class ChatFormattingMixin {

    /**
     * @author ThiemeH
     * @reason Not strip with the strip formatting
     */
    @Overwrite
    public static String stripFormatting(@Nullable String string) { // func_110646_a
        return string;
    }
}
