package creative.editor.screen;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.SoundSlider;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CEOptionSoundScreen extends Screen {
	private final Screen parent;
	private final GameSettings game_settings_4;

	public CEOptionSoundScreen(Screen parentIn, GameSettings settingsIn) {
		super(new TranslationTextComponent("options.sounds.title"));
		this.parent = parentIn;
		this.game_settings_4 = settingsIn;
	}

	protected void init() {
		int i = 0;
		this.addButton(new SoundSlider(this.minecraft, this.width / 2 - 155 + i % 2 * 160,
				this.height / 6 - 12 + 24 * (i >> 1), SoundCategory.MASTER, 310));
		i = i + 2;

		for (SoundCategory soundcategory : SoundCategory.values()) {
			if (soundcategory != SoundCategory.MASTER) {
				this.addButton(new SoundSlider(this.minecraft, this.width / 2 - 155 + i % 2 * 160,
						this.height / 6 - 12 + 24 * (i >> 1), soundcategory, 150));
				System.out.println(this.width / 2 - 155 + i % 2 * 160 + ":");
				System.out.println(this.height / 6 - 12 + 24 * (i >> 1));
				++i;
			}
		}
		this.addButton(new Button(245, 158, 150, 20, "Sound output", null));
		int j = this.width / 2 - 75;
		int k = this.height / 6 - 12;
		++i;
		this.addButton(new OptionButton(j, k + 24 * (i >> 1), 150, 20, AbstractOption.SHOW_SUBTITLES,
				AbstractOption.SHOW_SUBTITLES.func_216743_c(this.game_settings_4), (p_213105_1_) -> {
					AbstractOption.SHOW_SUBTITLES.func_216740_a(this.minecraft.gameSettings);
					p_213105_1_.setMessage(AbstractOption.SHOW_SUBTITLES.func_216743_c(this.minecraft.gameSettings));
					this.minecraft.gameSettings.saveOptions();
				}));
		this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, I18n.format("gui.done"),
				(p_213104_1_) -> {
					this.minecraft.displayGuiScreen(this.parent);
				}));
	}

	public void removed() {
		this.minecraft.gameSettings.saveOptions();
	}

	public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground();
		this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 15, 16777215);
		super.render(p_render_1_, p_render_2_, p_render_3_);
	}
}