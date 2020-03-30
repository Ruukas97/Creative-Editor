package creativeeditor.screen;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import creativeeditor.data.DataItem;
import creativeeditor.util.ColorUtils;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtils;
import creativeeditor.util.MinecraftHead;
import creativeeditor.widgets.StyledButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.TranslationTextComponent;

public class HeadCollectionScreen extends ParentScreen {
	
	private Color hoverColor;
	
	private HeadCategories selectedCategory = HeadCategories.values()[0];
	
	private String API_URL = "https://minecraft-heads.com/scripts/api.php?cat=";
	
	private HashMap<HeadCategories, ArrayList<DataItem>> headsMap = new HashMap<HeadCollectionScreen.HeadCategories, ArrayList<DataItem>>();
	
	
	
	public enum HeadCategories {
		alphabet, animals, blocks, decoration, food_drinks, humans, humanoid, miscellaneous, monsters, plants
	}

	public HeadCollectionScreen(Screen lastScreen) {
		super(new TranslationTextComponent("gui.headcollection"), lastScreen);

	}

	@Override
	protected void init() {
		super.init();
		
		minecraft.keyboardListener.enableRepeatEvents(true);

		addButton(new StyledButton(width / 24 - 12, height / 20 * 18, 60, 20, I18n.format("gui.main.back"),
				(Button b) -> {
					mc.displayGuiScreen(lastScreen);
				}));

		postInit();
	}

	protected void postInit() {
		if(headsMap.get(selectedCategory) == null) {
			loadSkulls(selectedCategory);
		}
	} 
	
	private void loadSkulls(HeadCategories cat) {
		if(headsMap.get(cat) != null) {
			headsMap.get(cat).clear();
		}
		headsMap.put(cat, new ArrayList<DataItem>());
		URL url;
		MinecraftHead[] headArray = null;
		try {
			url = new URL(API_URL + cat.toString().replaceAll("_", "-"));
			InputStreamReader reader = new InputStreamReader(url.openStream());
			Gson gson = new Gson();
			headArray = gson.fromJson(reader, MinecraftHead[].class);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(headArray != null) {
			for (MinecraftHead head : headArray) {
				DataItem skull = new DataItem(new ItemStack(Items.PLAYER_HEAD));
				
				
				
				headsMap.get(cat).add(skull);
				
			}
			
		}
		
		
	}

	@Override
	public void removed() {
		minecraft.keyboardListener.enableRepeatEvents(false);

	}

	@Override
	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		// Backspace not supported yet
		for (Widget w : renderWidgets) {
			if (w instanceof TextFieldWidget) {
				if (((TextFieldWidget) w).charTyped(p_charTyped_1_, p_charTyped_2_)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);
		
		hoverColor = ColorUtils.multiplyColor(color, new Color(1, 180, 180, 180));
		Color c = ColorUtils.multiplyColor(color, new Color(1, 220, 220, 220));
		
		int rightSideFromFrame = width / 12;
		int topFromBottomFrame = height / 10 * 9 - 3;
		int topFromTopFrame = height / 18;
		
		//Background
		fill(rightSideFromFrame, topFromTopFrame, width / 12 * 11, topFromBottomFrame, new Color(100, 0, 0, 0).getInt());
		

		// Main frame
		GuiUtils.drawFrame(rightSideFromFrame, height / 10, width / 12 * 11, topFromBottomFrame, 2, c);

		// Split line
		int leftSideFromLeft = width / 7 * 2 - 10;
		fill(width / 7 * 2 - 8, height / 10, leftSideFromLeft, height / 10 * 8 + 7, c.getInt());

		// Top bar
		int bottomFromTop = height / 16 * 2 + 2;
		fill(rightSideFromFrame, topFromTopFrame, width / 12 * 11, bottomFromTop, c.getInt());

		// Search bar (not fixed)
		fill(rightSideFromFrame, topFromTopFrame, width / 12 * 11, height / 13 * 2 + 2, new Color(10, 240, 240, 240).getInt());

		// Bottom bar
		int topFromBottom = height / 10 * 8 + 6;
		int bottomFromBottom = height / 10 * 8 + 8;
		fill(width / 12, topFromBottom, width / 12 * 11, bottomFromBottom, c.getInt());

		
		
		int i = 0;
		int add = (topFromBottom - bottomFromTop) / (HeadCategories.values().length);
		int posX1 = leftSideFromLeft - rightSideFromFrame - 2;
		for (HeadCategories hc : HeadCategories.values()) {
			
			
			int posY1 = bottomFromTop + i + (add / 3);
			Color textColor = color;
			if (GuiUtils.isMouseIn(mouseX, mouseY, posX1 / 2, posY1 - 2, posX1, 11)) {
				textColor = hoverColor;
			} else {
			}
			drawCenteredString(mc.fontRenderer, I18n.format("gui.heads.category." + hc.name()), posX1, posY1,
					textColor.getInt());
			i += add;
		}

		// Credits to head api
		drawCenteredString(mc.fontRenderer, I18n.format("gui.heads.credit"), width / 2,
				(topFromBottomFrame - topFromBottom) /2 + topFromBottom - 3, color.getInt());

	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

}
