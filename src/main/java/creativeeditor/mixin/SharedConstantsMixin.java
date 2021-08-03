package creativeeditor.mixin;

import net.minecraft.util.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({SharedConstants.class})
public class SharedConstantsMixin {

    @Overwrite
    public static boolean isAllowedChatCharacter(char p) { //func_71566_a
        return (p >= ' ' && p != '');
    }

}
