package infinityitemeditor.mixin;

import net.minecraft.util.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({SharedConstants.class})
public class SharedConstantsMixin {

    /**
     * @author Ruukas
     * @reason Adds the section sign to allowed chat characters
     */
    @Overwrite
    public static boolean isAllowedChatCharacter(char c) { //func_71566_a
        return SharedConstants.isAllowedChatCharacter(c) || c == '\u00a7';
    }

}
