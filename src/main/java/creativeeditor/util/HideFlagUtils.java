package creativeeditor.util;

public class HideFlagUtils {

    public enum Flags {
        ENCHANTMENTS(1, "flag.enchantment"), ATTRIBUTEMODIFIERS(2, "flag.attributemod"), UNBREAKABLE(4, "flag.unbreakable"), CANDESTROY(8, "flag.candestroy"), CANPLACEON(16, "flag.canplaceon"), ITEMINFO(32, "flag.iteminfo"), DYEHIDDEN(64, "flag.dyehidden");

        private final int denom;
        private final String key;


        Flags(int denom, String key) {
            this.denom = denom;
            this.key = key;
        }


        public int getDenom() {
            return denom;
        }


        public String getKey() {
            return key;
        }


        public boolean hidden(int value) {
            return (value & denom) > 0;
        }
    }

}
