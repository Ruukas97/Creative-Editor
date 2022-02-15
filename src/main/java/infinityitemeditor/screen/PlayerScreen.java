package infinityitemeditor.screen;

import infinityitemeditor.screen.widgets.StyledButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.chat.TranslatableComponent;

public class PlayerScreen extends ParentScreen {

    public PlayerScreen(Screen lastScreen) {
        super(new TranslatableComponent("gui.player"), lastScreen);
    }


    @Override
    public void init(Minecraft p_init_1_, int p_init_2_, int p_init_3_) {
        super.init(p_init_1_, p_init_2_, p_init_3_);
        PlayerAbilities playerAb = minecraft.player.abilities;
        addRenderableWidget(new StyledButton(10, 10, 10, 10, I18n.get("gui.player.flight"), t -> {
//            if (playerAb.isCreativeMode || minecraft.isSingleplayer()) {
//                if (playerAb.isFlying) {
//                    playerAb.allowFlying = false;
//                    playerAb.isFlying = false;
//                }
//                else {
//                    playerAb.allowFlying = true;
//                    playerAb.isFlying = !minecraft.player.onGround;
//                }
//            }
        }));

        /*
         * SliderCount countSlider = addRenderableWidget(new SliderCount(width / 2 + 5, 60, 50,
         * 20, playerAb.getWalkSpeed(), s -> { playerAb.setWalkSpeed(s.getCount()); }));
         * countSlider.setMessage("" + playerAb.getWalkSpeed());
         */
    }


}
