package creativeeditor.screen;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import creativeeditor.data.DataItem;
import creativeeditor.util.MinecraftHead;
import net.java.games.input.Keyboard;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class HeadCollectionScreen extends ParentScreen {

	public static final String[] CATEGORIES = { "alphabet", "animals", "blocks", "decoration", "food-drinks", "humans",
			"humanoid", "miscellaneous", "monsters", "plants" };
	private static final String API_URL = "https://minecraft-heads.com/scripts/api.php?cat=";

	private static int selCat = 0;
	private static int currentElement = 0;
	private static String filteredString = null;
	private static String searchString = "";

	private int maxInRow;
	private int amountInPage;

	private int loaded = -1;

	private final Screen lastScreen;
	private List<ItemStack> allSkulls = new LinkedList<ItemStack>();
	private List<ItemStack> filteredSkulls = new LinkedList<ItemStack>();

	public HeadCollectionScreen(Screen lastScreen) {
		super(new TranslationTextComponent("gui.headcollection"), lastScreen);
		this.lastScreen = lastScreen;

	}

	@Override
	public void init() {
		super.init();
		Keyboard.enableRepeatEvents(true);
		if (loaded != selCat) {
			try {
				loadSkulls();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!searchString.equals(filteredString)) {
			filteredSkulls.clear();

			for (ItemStack s : allSkulls) {
				if (s.getDisplayName().toString().toLowerCase().contains(searchString.toLowerCase())) {
					filteredSkulls.add(s);
				}
			}

			currentElement = 0;
			filteredString = searchString;
		}

		maxInRow = (width - 250) / 16;
		amountInPage = maxInRow * 10;
	}

	@Override
	public void onClose() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton != 0) {
			return false;
		}

		int letterSpace = 80;
		int space = ((width - (maxInRow * 16)) - letterSpace) / 2;
		int nextPageW = fontRenderer.getStringWidth("-->");
		int topbar = 20;

		int currentPage = currentElement / amountInPage;
		int amountPages = ((int) Math.ceil(filteredSkulls.size() / amountInPage) + 1);

		int searchW = fontRenderer.getStringWidth(searchString);
		if (searchString.length() > 0 && !searchString.equals(filteredString)
				&& HelperGui.isMouseInRegion(mouseX, mouseY, (width / 2) - searchW / 2, 56, searchW, 8)) {
			init();
		}

		// Next page
		else if (currentPage + 1 < amountPages && HelperGui.isMouseInRegion(mouseX, mouseY,
				space + letterSpace + maxInRow * 16 - 3 - nextPageW, 50 + topbar + 168, nextPageW, 8)) {
			currentElement = Math.min(filteredSkulls.size() - 1, (currentPage + 1) * amountInPage);
			return false;
		} else if (currentPage > 0 && HelperGui.isMouseInRegion(mouseX, mouseY,
				space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, 50 + topbar + 168, nextPageW, 8)) {
			currentElement = Math.max(0, (currentPage - 1) * amountInPage);
			return false;
		}

		for (int i = 0; i < CATEGORIES.length; i++) {
			int x = space + letterSpace / 2;
			int y = i * 15 + 59 + topbar;

			int sWH = fontRenderer.getStringWidth(CATEGORIES[i]) / 2;
			if (mouseX > x - sWH && mouseX < x + sWH && mouseY > y - 1 && mouseY < y + 9) {
				selCat = i;
				currentElement = 0;
				init();
				return false;
			}
		}

		if (filteredSkulls.size() > 0) {
			for (int i = (int) Math.min(filteredSkulls.size() - 1, currentPage * amountInPage); i < (int) Math
					.min(filteredSkulls.size(), (currentPage + 1) * amountInPage); i++) {
				int x = space + letterSpace + (16 * (i % maxInRow));
				int y = 50 + topbar + (16 * ((i % amountInPage) / maxInRow));

				if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
					if (isShiftKeyDown()) {
						mc.playerController.sendPacketDropItem(filteredSkulls.get(i));
					} else {
						int slot = InventoryUtils.getEmptySlot(mc.player.inventory);
						if (slot < 0)
							mc.playerController.sendPacketDropItem(filteredSkulls.get(i));
						else
							mc.playerController.sendSlotPacket(filteredSkulls.get(i), slot);
					}
					return true;
				}
			}
		}
	}

	@Override
	public boolean keyPressed(int key1, int key2, int key3) {
		if (keyCode == 1) {
			this.mc.displayGuiScreen(lastScreen);

			if (this.mc.currentScreen == null) {
				this.mc.setGameFocused(true);
			}
		} else if (keyCode == Keyboard.KEY_BACK) {
			searchString = searchString.substring(0, Math.max(searchString.length() - 1, 0));
			if (searchString.length() < 1 && !searchString.equals(filteredString)) {
				init();
			}
		} else if ((keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER)
				&& !searchString.equals(filteredString)) {
			init();
		} else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
			if (searchString.length() < 20) {
				searchString += typedChar;
			}
		}
		return super.keyPressed(key1, key2, key3);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		int topbar = 20;
		int letterSpace = 80;
		int space = ((width - (maxInRow * 16 + 3)) - letterSpace) / 2;
		int currentPage = currentElement / amountInPage;
		int blandColor = HelperGui.getColorFromRGB(255, 150, 200, 255);
		int amountPages = ((int) Math.ceil(filteredSkulls.size() / amountInPage) + 1);

		// Topbar
		drawRect(space, 50, space + letterSpace + (maxInRow * 16) + 3, 50 + topbar,
				HelperGui.getColorFromRGB(200, 0, 122, 255));
		// Outlines
		drawRect(space, 50 + topbar, space + 2, 50 + topbar + 161 + topbar,
				HelperGui.getColorFromRGB(200, 0, 122, 255));
		drawRect(space + letterSpace + maxInRow * 16 + 1, 50 + topbar, space + letterSpace + maxInRow * 16 + 3,
				50 + topbar + 161 + topbar, HelperGui.getColorFromRGB(200, 0, 122, 255));
		drawRect(space + 2, 50 + topbar + 161, space + letterSpace + (maxInRow * 16) + 1, 50 + topbar + 163,
				HelperGui.getColorFromRGB(200, 0, 122, 255));
		drawRect(space, 50 + topbar * 2 + 161, space + letterSpace + (maxInRow * 16) + 3, 50 + topbar * 2 + 163,
				HelperGui.getColorFromRGB(200, 0, 122, 255));

		// Background
		drawRect(space, 50 + topbar, space + letterSpace + (maxInRow * 16) + 3, 50 + topbar * 2 + 163,
				HelperGui.getColorFromRGB(100, 70, 50, 200));
		// Letterspace
		drawRect(space + 2, 50 + topbar, space + letterSpace - 2, 50 + topbar + 161,
				HelperGui.getColorFromRGB(100, 50, 50, 50));

		for (int i = 0; i < CATEGORIES.length; i++) {
			int x = space + letterSpace / 2;
			int y = i * 15 + 59 + topbar;
			int sWH = fontRenderer.getStringWidth(CATEGORIES[i]) / 2;

			drawCenteredString(fontRenderer, I18n.format("gui.headcollection.category." + CATEGORIES[i]), x, y,
					(i == selCat || (mouseX > x - sWH && mouseX < x + sWH && mouseY > y - 1 && mouseY < y + 9)
							? InfinityConfig.CONTRAST_COLOR
							: InfinityConfig.MAIN_COLOR));
		}

		drawString(fontRenderer, I18n.format("gui.headcollection") + " (" + filteredSkulls.size() + ")", space + 7, 56,
				blandColor);

		drawCenteredString(fontRenderer,
				searchString.length() > 0 ? searchString : I18n.format("gui.headcollection.typesearch"), width / 2, 56,
				blandColor);

		String pageString = I18n.format("gui.headcollection.currentpage", currentPage + 1, amountPages);
		drawString(fontRenderer, pageString,
				space + letterSpace + maxInRow * 16 - fontRenderer.getStringWidth(pageString), 56, blandColor);

		String nextPage = "-->";
		int nextPageW = fontRenderer.getStringWidth(nextPage);
		if (currentPage + 1 < amountPages) {
			boolean selectedN = HelperGui.isMouseInRegion(mouseX, mouseY,
					space + letterSpace + maxInRow * 16 - 3 - nextPageW, 50 + topbar + 168, nextPageW, 8);
			drawString(fontRenderer, nextPage, space + letterSpace + maxInRow * 16 - 3 - nextPageW, 50 + topbar + 168,
					selectedN ? InfinityConfig.CONTRAST_COLOR : blandColor);
		}

		drawCenteredString(fontRenderer, "" + (currentPage + 1), space + letterSpace + maxInRow * 16 - 13 - nextPageW,
				50 + topbar + 168, blandColor);

		if (currentPage > 0) {
			String previousPage = "<--";
			boolean selectedP = HelperGui.isMouseInRegion(mouseX, mouseY,
					space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, 50 + topbar + 168, nextPageW, 8);
			drawString(fontRenderer, previousPage, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2,
					50 + topbar + 168, selectedP ? InfinityConfig.CONTRAST_COLOR : blandColor);
		}

		drawString(fontRenderer, "From https://minecraft-heads.com API", space + 7, 50 + topbar + 168, blandColor);

		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableColorMaterial();
		GlStateManager.enableLighting();
		itemRender.zLevel = 100.0F;
		ItemStack hovered = null;
		if (filteredSkulls.size() > 0) {
			for (int i = (int) Math.min(filteredSkulls.size() - 1, currentPage * amountInPage); i < (int) Math
					.min(filteredSkulls.size(), (currentPage + 1) * amountInPage); i++) {
				int x = space + letterSpace + (16 * (i % maxInRow));
				int y = 50 + topbar + (16 * ((i % amountInPage) / maxInRow));
				itemRender.renderItemAndEffectIntoGUI(filteredSkulls.get(i), x, y);
				if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
					drawRect(x, y, x + 16, y + 16, HelperGui.getColorFromRGB(150, 150, 150, 150));
					hovered = filteredSkulls.get(i);
				}
			}
		}

		GlStateManager.enableLighting();
		GlStateManager.popMatrix();

		int searchW = fontRenderer.getStringWidth(searchString);

		if (hovered != null) {
			HelperGui.addToolTip(0, 0, width, height, mouseX, mouseY, hovered.getDisplayName(),
					I18n.format("gui.headcollection.clickhead"), I18n.format("gui.headcollection.clickheadshift"));
		} else if (!searchString.equals(filteredString)
				&& HelperGui.isMouseInRegion(mouseX, mouseY, (width / 2) - searchW / 2, 56, searchW, 8)) {
			drawHoveringText(I18n.format("gui.headcollection.clicksearch"), mouseX, mouseY);
		} else {
			HelperGui.addTooltipTranslated(space + 2, 50 + topbar, letterSpace - 4, 161, mouseX, mouseY,
					"gui.headcollection.changecategory");
		}

		drawCenteredString(fontRenderer, "Free slots: " + InventoryUtils.getEmptySlots(mc.player.inventory), width / 2,
				height - 45, blandColor);
		drawCenteredString(fontRenderer,
				"Heads in inventory: " + InventoryUtils.countItem(mc.player.inventory, Items.SKULL, 3), width / 2,
				height - 35, blandColor);

	}

	public void loadSkulls() throws IOException {
		allSkulls.clear();
		URL url = new URL(API_URL + CATEGORIES[selCat]);
		InputStreamReader reader = new InputStreamReader(url.openStream());
		Gson gson = new Gson();
		MinecraftHead[] headArray = gson.fromJson(reader, MinecraftHead[].class);
		reader.close();
		for (MinecraftHead head : headArray) {
			try {
				String nbt = "{SkullOwner:{Id:\"" + head.getUuid() + "\",Properties:{textures:[{Value:\""
						+ head.getValue() + "\"}]}}}";
				DataItem skull = new DataItem(Items.PLAYER_HEAD, nbt);
				skull.getDisplayNameTag().set(new StringTextComponent(head.getName()));
				allSkulls.add(skull.getItemStack());
			} catch (CommandSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		loaded = selCat;
		filteredString = null;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

}
