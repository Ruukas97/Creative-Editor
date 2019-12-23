package creativeeditor.screen;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.SuggestionContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;

import creativeeditor.nbt.NBTItemBase;
import creativeeditor.styles.Style;
import creativeeditor.styles.StyleSpectrum;
import creativeeditor.styles.StyleVanilla;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.CEWButton;
import creativeeditor.widgets.CountSlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MainScreen extends ParentItemScreen {
	protected TextFieldWidget itemIDTextField;
	protected final List<String> currentSuggestions = Lists.newArrayList();
	protected int field_209112_u;
	protected int field_209113_v;
	protected ParseResults<ISuggestionProvider> suggestionProvider;
	protected CompletableFuture<Suggestions> futureSuggestions;
	protected MainScreen.SuggestionsList suggestionList;
	private boolean applying;

	public MainScreen(Screen lastScreen, NBTItemBase editing) {
		super(new TranslationTextComponent("gui.main"), lastScreen, editing);
	}

	public void tick() {
		this.itemIDTextField.tick();
	}

	@Override
	protected void init() {
		super.init();

		minecraft.keyboardListener.enableRepeatEvents(true);

		itemIDTextField = new TextFieldWidget(font, width / 2 - 150, 50, 300, 20, I18n.format("advMode.command"));
		itemIDTextField.setMaxStringLength(32500);
		itemIDTextField.setTextFormatter(this::formatCommand);
		itemIDTextField.func_212954_a(this::computeSuggestions);
		children.add(this.itemIDTextField);
		this.func_212928_a(this.itemIDTextField);
		this.itemIDTextField.setFocused2(true);
		this.computeSuggestions();

		
		// Might want to put the buttons in a list and loop them
		addButton(new CEWButton(width / 9 * 7 - 20, height / 11 * 2, 100, 20, I18n.format("gui.main.itemflag"), b -> {
			minecraft.displayGuiScreen(new FlagScreen(this, editing));
		}));

		addButton(new CEWButton(width / 9 * 7 - 20, height / 11 * 2 + 30, 100, 20, I18n.format("gui.main.colorstyle"),
				b -> {
					Style.setCurrentStyle(
							Style.getCurrentStyle() instanceof StyleVanilla ? new StyleSpectrum() : new StyleVanilla());
				}));

		addButton(new CEWButton(width / 9 * 7 - 20, height / 11 * 2 + 60, 100, 20, I18n.format("gui.main.head"), b -> {
			mc.displayGuiScreen(new HeadScreen(this));
		}));
		
		addButton(new CEWButton(width / 9 * 7 - 20, height / 11 * 2 + 90, 100, 20, I18n.format("gui.main.player"), b -> {
			mc.displayGuiScreen(new PlayerScreen(this));
		}));

		CountSlider countSlider = new CountSlider(width / 2 + 5, 60, 50, 20, (double) (editing.getCount() / 64.0),
				s -> {
					editing.setCount((byte) s.getCount());
				});
		countSlider.setMessage("" + editing.getCount());
		addButton(countSlider);
	}

	public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
		String s = this.itemIDTextField.getText();
		this.init(p_resize_1_, p_resize_2_, p_resize_3_);
		this.setCommand(s);
		this.computeSuggestions();
	}

	protected void close() {
		this.minecraft.displayGuiScreen((Screen) null);
	}

	public void removed() {
		this.minecraft.keyboardListener.enableRepeatEvents(false);
	}

	private void computeSuggestions(String text) {
		this.computeSuggestions();
	}

	public boolean keyPressed(int key1, int key2, int key3) {
		if (this.suggestionList != null
				&& this.suggestionList.keyPressed(key1, key2, key3)) {
			return true;
		} else if (this.getFocused() == this.itemIDTextField && key1 == 258) {
			this.func_209109_s();
			return true;
		} else if (super.keyPressed(key1, key2, key3)) {
			return true;
		} else if (key1 != 257 && key1 != 335) {
			if (key1 == 258 && this.getFocused() == this.itemIDTextField) {
				this.func_209109_s();
			}

			return false;
		} else {
			this.close();
			return true;
		}
	}

	public boolean mouseScrolled(double scroll1, double scroll2, double scroll3) {
		return this.suggestionList != null
				&& this.suggestionList.mouseScrolled(MathHelper.clamp(scroll3, -1.0D, 1.0D)) ? true
						: super.mouseScrolled(scroll1, scroll2, scroll3);
	}

	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return this.suggestionList != null
				&& this.suggestionList.mouseClicked((int) mouseX, (int) mouseY, mouseButton)
						? true
						: super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	protected void computeSuggestions() {
		String s = this.itemIDTextField.getText();
		if (this.suggestionProvider != null && !this.suggestionProvider.getReader().getString().equals(s)) {
			this.suggestionProvider = null;
		}

		if (!this.applying) {
			this.itemIDTextField.setSuggestion((String) null);
			this.suggestionList = null;
		}

		this.currentSuggestions.clear();
		CommandDispatcher<ISuggestionProvider> commanddispatcher = this.minecraft.player.connection.func_195515_i();
		StringReader stringreader = new StringReader(s);
		if (stringreader.canRead() && stringreader.peek() == '/') {
			stringreader.skip();
		}

		int i = stringreader.getCursor();
		if (this.suggestionProvider == null) {
			this.suggestionProvider = commanddispatcher.parse(stringreader,
					this.minecraft.player.connection.getSuggestionProvider());
		}

		int j = this.itemIDTextField.getCursorPosition();
		if (j >= i && (this.suggestionList == null || !this.applying)) {
			this.futureSuggestions = commanddispatcher.getCompletionSuggestions(this.suggestionProvider, j);
			this.futureSuggestions.thenRun(() -> {
				if (this.futureSuggestions.isDone()) {
					this.func_209107_u();
				}
			});
		}
	}

	private void func_209107_u() {
		if (this.futureSuggestions.join().isEmpty() && !this.suggestionProvider.getExceptions().isEmpty()
				&& this.itemIDTextField.getCursorPosition() == this.itemIDTextField.getText().length()) {
			int i = 0;

			for (Entry<CommandNode<ISuggestionProvider>, CommandSyntaxException> entry : this.suggestionProvider
					.getExceptions().entrySet()) {
				CommandSyntaxException commandsyntaxexception = entry.getValue();
				if (commandsyntaxexception.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
					++i;
				} else {
					this.currentSuggestions.add(commandsyntaxexception.getMessage());
				}
			}

			if (i > 0) {
				this.currentSuggestions.add(
						CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create().getMessage());
			}
		}

		this.field_209112_u = 0;
		this.field_209113_v = this.width;
		if (this.currentSuggestions.isEmpty()) {
			this.func_209108_a(TextFormatting.GRAY);
		}

		this.suggestionList = null;
		if (this.minecraft.gameSettings.autoSuggestCommands) {
			this.func_209109_s();
		}

	}

	private String formatCommand(String p_209104_1_, int p_209104_2_) {
		return this.suggestionProvider != null ? ChatScreen.func_212336_a(this.suggestionProvider, p_209104_1_, p_209104_2_)
				: p_209104_1_;
	}

	private void func_209108_a(TextFormatting p_209108_1_) {
		CommandContextBuilder<ISuggestionProvider> commandcontextbuilder = this.suggestionProvider.getContext();
		SuggestionContext<ISuggestionProvider> suggestioncontext = commandcontextbuilder
				.findSuggestionContext(this.itemIDTextField.getCursorPosition());
		Map<CommandNode<ISuggestionProvider>, String> map = this.minecraft.player.connection.func_195515_i()
				.getSmartUsage(suggestioncontext.parent, this.minecraft.player.connection.getSuggestionProvider());
		List<String> list = Lists.newArrayList();
		int i = 0;

		for (Entry<CommandNode<ISuggestionProvider>, String> entry : map.entrySet()) {
			if (!(entry.getKey() instanceof LiteralCommandNode)) {
				list.add(p_209108_1_ + (String) entry.getValue());
				i = Math.max(i, this.font.getStringWidth(entry.getValue()));
			}
		}

		if (!list.isEmpty()) {
			this.currentSuggestions.addAll(list);
			this.field_209112_u = MathHelper.clamp(this.itemIDTextField.func_195611_j(suggestioncontext.startPos), 0,
					this.itemIDTextField.func_195611_j(0) + this.itemIDTextField.getAdjustedWidth() - i);
			this.field_209113_v = i;
		}

	}

	public void func_209109_s() {
		if (this.futureSuggestions != null && this.futureSuggestions.isDone()) {
			Suggestions suggestions = this.futureSuggestions.join();
			if (!suggestions.isEmpty()) {
				int i = 0;

				for (Suggestion suggestion : suggestions.getList()) {
					i = Math.max(i, this.font.getStringWidth(suggestion.getText()));
				}

				int j = MathHelper.clamp(this.itemIDTextField.func_195611_j(suggestions.getRange().getStart()), 0,
						this.itemIDTextField.func_195611_j(0) + this.itemIDTextField.getAdjustedWidth() - i);
				this.suggestionList = new MainScreen.SuggestionsList(j, 72, i, suggestions);
			}
		}

	}

	protected void setCommand(String p_209102_1_) {
		this.itemIDTextField.setText(p_209102_1_);
	}

	@Nullable
	private static String func_212339_b(String p_212339_0_, String p_212339_1_) {
		return p_212339_1_.startsWith(p_212339_0_) ? p_212339_1_.substring(p_212339_0_.length()) : null;
	}

	@Override
	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		super.backRender(mouseX, mouseY, p3, color);

		// First vertical line
		fill(width / 3, 20, width / 3 + 1, height - 20, color.getInt());
		// Second vertical line
		fill(width * 2 / 3, 20, width * 2 / 3 + 1, height - 20, color.getInt());
		// Left horizontal line
		fill(20, 40, width / 3 - 15, 41, color.getInt());
		// Right horizontal line
		fill(width * 2 / 3 + 16, 40, width - 20, 41, color.getInt());
	}

	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);

		// Item Name
		String itemCount = editing.getCount() > 1 ? editing.getCount() + "x " : "";
		String itemOverview = itemCount + editing.getItemStack().getDisplayName().getFormattedText();
		drawCenteredString(font, itemOverview, width / 2, 27, color.getInt());

		String id = I18n.format("gui.main.id");
		int idWidth = font.getStringWidth(id);
		drawString(font, id, width / 2 - idWidth, 47, color.getInt());
		drawString(font, editing.getItem().getRegistryName().getPath(), width / 2 + 5, 47, color.getInt());

		String count = I18n.format("gui.main.count");
		int countWidth = font.getStringWidth(count);
		drawString(font, count, width / 2 - countWidth, 67, color.getInt());
		// drawString(font, ""+editing.getCount(), width / 2 + 5, 67, color.getInt());

		itemIDTextField.render(mouseX, mouseY, p3);

		int i = 75;

		if (this.suggestionList != null) {
			this.suggestionList.render(mouseX, mouseY);
		} else {
			i = 0;

			for (String s : this.currentSuggestions) {
				fill(this.field_209112_u - 1, 72 + 12 * i, this.field_209112_u + this.field_209113_v + 1, 84 + 12 * i,
						Integer.MIN_VALUE);
				this.font.drawStringWithShadow(s, (float) this.field_209112_u, (float) (74 + 12 * i), -1);
				++i;
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	class SuggestionsList {
		private final Rectangle2d bounds;
		private final Suggestions suggestions;
		private final String field_212467_d;
		private int scrollAmount;
		private int selectedSuggestion;
		private Vec2f lastMousePosition = Vec2f.ZERO;
		private boolean field_209141_h;

		private SuggestionsList(int p_i49843_2_, int p_i49843_3_, int p_i49843_4_, Suggestions p_i49843_5_) {
			this.bounds = new Rectangle2d(p_i49843_2_ - 1, p_i49843_3_, p_i49843_4_ + 1,
					Math.min(p_i49843_5_.getList().size(), 7) * 12);
			this.suggestions = p_i49843_5_;
			this.field_212467_d = MainScreen.this.itemIDTextField.getText();
			this.suggest(0);
		}

		public void render(int mouseX, int mouseY) {
			int i = Math.min(this.suggestions.getList().size(), 7);
			boolean flag = this.scrollAmount > 0;
			boolean flag1 = this.suggestions.getList().size() > this.scrollAmount + i;
			boolean flag2 = flag || flag1;
			boolean flag3 = this.lastMousePosition.x != (float) mouseX || this.lastMousePosition.y != (float) mouseY;
			if (flag3) {
				this.lastMousePosition = new Vec2f((float) mouseX, (float) mouseY);
			}

			if (flag2) {
				AbstractGui.fill(this.bounds.getX(), this.bounds.getY() - 1,
						this.bounds.getX() + this.bounds.getWidth(), this.bounds.getY(), Integer.MIN_VALUE);
				AbstractGui.fill(this.bounds.getX(), this.bounds.getY() + this.bounds.getHeight(),
						this.bounds.getX() + this.bounds.getWidth(), this.bounds.getY() + this.bounds.getHeight() + 1,
						Integer.MIN_VALUE);
				if (flag) {
					for (int l = 0; l < this.bounds.getWidth(); ++l) {
						if (l % 2 == 0) {
							AbstractGui.fill(this.bounds.getX() + l, this.bounds.getY() - 1, this.bounds.getX() + l + 1,
									this.bounds.getY(), -1);
						}
					}
				}

				if (flag1) {
					for (int j1 = 0; j1 < this.bounds.getWidth(); ++j1) {
						if (j1 % 2 == 0) {
							AbstractGui.fill(this.bounds.getX() + j1, this.bounds.getY() + this.bounds.getHeight(),
									this.bounds.getX() + j1 + 1, this.bounds.getY() + this.bounds.getHeight() + 1, -1);
						}
					}
				}
			}

			boolean flag4 = false;

			for (int i1 = 0; i1 < i; ++i1) {
				Suggestion suggestion = this.suggestions.getList().get(i1 + this.scrollAmount);
				AbstractGui.fill(this.bounds.getX(), this.bounds.getY() + 12 * i1,
						this.bounds.getX() + this.bounds.getWidth(), this.bounds.getY() + 12 * i1 + 12,
						Integer.MIN_VALUE);
				if (mouseX > this.bounds.getX() && mouseX < this.bounds.getX() + this.bounds.getWidth()
						&& mouseY > this.bounds.getY() + 12 * i1 && mouseY < this.bounds.getY() + 12 * i1 + 12) {
					if (flag3) {
						this.suggest(i1 + this.scrollAmount);
					}

					flag4 = true;
				}

				MainScreen.this.font.drawStringWithShadow(suggestion.getText(), (float) (this.bounds.getX() + 1),
						(float) (this.bounds.getY() + 2 + 12 * i1),
						i1 + this.scrollAmount == this.selectedSuggestion ? -256 : -5592406);
			}

			if (flag4) {
				Message message = this.suggestions.getList().get(this.selectedSuggestion).getTooltip();
				if (message != null) {
					MainScreen.this.renderTooltip(TextComponentUtils.toTextComponent(message).getFormattedText(),
							mouseX, mouseY);
				}
			}

		}

		public boolean mouseClicked(int mouseX, int mouseY, int button) {
			if (!this.bounds.contains(mouseX, mouseY)) {
				return false;
			} else {
				int i = (mouseY - this.bounds.getY()) / 12 + this.scrollAmount;
				if (i >= 0 && i < this.suggestions.getList().size()) {
					this.suggest(i);
					this.applySuggestion();
				}

				return true;
			}
		}

		public boolean mouseScrolled(double amount) {
			int i = (int) (MainScreen.this.minecraft.mouseHelper.getMouseX()
					* (double) MainScreen.this.minecraft.mainWindow.getScaledWidth()
					/ (double) MainScreen.this.minecraft.mainWindow.getWidth());
			int j = (int) (MainScreen.this.minecraft.mouseHelper.getMouseY()
					* (double) MainScreen.this.minecraft.mainWindow.getScaledHeight()
					/ (double) MainScreen.this.minecraft.mainWindow.getHeight());
			if (this.bounds.contains(i, j)) {
				this.scrollAmount = MathHelper.clamp((int) ((double) this.scrollAmount - amount), 0,
						Math.max(this.suggestions.getList().size() - 7, 0));
				return true;
			} else {
				return false;
			}
		}

		public boolean keyPressed(int p_209133_1_, int p_209133_2_, int p_209133_3_) {
			if (p_209133_1_ == 265) {
				this.func_209128_a(-1);
				this.field_209141_h = false;
				return true;
			} else if (p_209133_1_ == 264) {
				this.func_209128_a(1);
				this.field_209141_h = false;
				return true;
			} else if (p_209133_1_ == 258) {
				if (this.field_209141_h) {
					this.func_209128_a(Screen.hasShiftDown() ? -1 : 1);
				}

				this.applySuggestion();
				return true;
			} else if (p_209133_1_ == 256) {
				this.func_209132_b();
				return true;
			} else {
				return false;
			}
		}

		public void func_209128_a(int p_209128_1_) {
			this.suggest(this.selectedSuggestion + p_209128_1_);
			int i = this.scrollAmount;
			int j = this.scrollAmount + 7 - 1;
			if (this.selectedSuggestion < i) {
				this.scrollAmount = MathHelper.clamp(this.selectedSuggestion, 0,
						Math.max(this.suggestions.getList().size() - 7, 0));
			} else if (this.selectedSuggestion > j) {
				this.scrollAmount = MathHelper.clamp(this.selectedSuggestion - 7, 0,
						Math.max(this.suggestions.getList().size() - 7, 0));
			}

		}

		public void suggest(int suggestionIndex) {
			this.selectedSuggestion = suggestionIndex;
			if (this.selectedSuggestion < 0) {
				this.selectedSuggestion += this.suggestions.getList().size();
			}

			if (this.selectedSuggestion >= this.suggestions.getList().size()) {
				this.selectedSuggestion -= this.suggestions.getList().size();
			}

			Suggestion suggestion = this.suggestions.getList().get(this.selectedSuggestion);
			MainScreen.this.itemIDTextField.setSuggestion(MainScreen
					.func_212339_b(MainScreen.this.itemIDTextField.getText(), suggestion.apply(this.field_212467_d)));
		}

		public void applySuggestion() {
			Suggestion suggestion = this.suggestions.getList().get(this.selectedSuggestion);
			MainScreen.this.applying = true;
			MainScreen.this.setCommand(suggestion.apply(this.field_212467_d));
			int i = suggestion.getRange().getStart() + suggestion.getText().length();
			MainScreen.this.itemIDTextField.func_212422_f(i);
			MainScreen.this.itemIDTextField.setSelectionPos(i);
			this.suggest(this.selectedSuggestion);
			MainScreen.this.applying = false;
			this.field_209141_h = true;
		}

		public void func_209132_b() {
			MainScreen.this.suggestionList = null;
		}
	}
}
