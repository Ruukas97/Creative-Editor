//package infinityitemeditor.mixin;
//
//import net.minecraft.util.ICharacterConsumer;
//import net.minecraft.util.text.ChatFormatting;
//import net.minecraft.util.text.Style;
//import net.minecraft.util.text.TextProcessing;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//
//@Mixin({TextProcessing.class})
//public class TextProcessingMixin {
//
//    /**
//     * @author ThiemeH
//     * @reason Render section sign
//     */
//    @Overwrite
//    public static boolean iterateFormatted(String p_238340_0_, int p_238340_1_, Style p_238340_2_, Style p_238340_3_, ICharacterConsumer p_238340_4_) {
//        int i = p_238340_0_.length();
//        Style style = p_238340_2_;
//
//        for (int j = p_238340_1_; j < i; ++j) {
//            char c0 = p_238340_0_.charAt(j);
//            if (c0 == 167) {
//                if (!(j + 1 >= i)) {
//                    char c1 = p_238340_0_.charAt(j + 1);
//                    ChatFormatting textformatting = ChatFormatting.getByCode(c1);
//                    if (textformatting != null) {
//                        style = textformatting == ChatFormatting.RESET ? p_238340_3_ : style.applyLegacyFormat(textformatting);
//                        ++j;
//                        continue;
//                    }
//                }
//            }
//            if (Character.isHighSurrogate(c0)) {
//                if (j + 1 >= i) {
//                    if (!p_238340_4_.accept(j, style, 65533)) {
//                        return false;
//                    }
//                    break;
//                }
//
//                char c2 = p_238340_0_.charAt(j + 1);
//                if (Character.isLowSurrogate(c2)) {
//                    if (!p_238340_4_.accept(j, style, Character.toCodePoint(c0, c2))) {
//                        return false;
//                    }
//
//                    ++j;
//                } else if (!p_238340_4_.accept(j, style, 65533)) {
//                    return false;
//                }
//            } else if (!feedChar(style, p_238340_4_, j, c0)) {
//                return false;
//            }
//
//        }
//
//        return true;
//    }
//
//    private static boolean feedChar(Style p_238344_0_, ICharacterConsumer p_238344_1_, int p_238344_2_, char p_238344_3_) {
//        return Character.isSurrogate(p_238344_3_) ? p_238344_1_.accept(p_238344_2_, p_238344_0_, 65533) : p_238344_1_.accept(p_238344_2_, p_238344_0_, p_238344_3_);
//    }
//}
