package infinityitemeditor.mixin;

import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({SharedConstants.class})
public class SharedConstantsMixin {

    /**
     * @author ThiemeH
     * @reason Removes the section sign from invalid chat character
     */
    @Overwrite
    public static boolean isAllowedChatCharacter(char p) { //func_71566_a
        return (p >= ' ' && p != '');
    }

}
