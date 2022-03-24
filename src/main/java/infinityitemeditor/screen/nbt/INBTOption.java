package infinityitemeditor.screen.nbt;

import net.minecraft.util.text.ITextComponent;

public interface INBTOption {
    ITextComponent getOptionName();
    boolean isEnabled();
    INBTOption getSubOptions();
    void onClicked();
}
