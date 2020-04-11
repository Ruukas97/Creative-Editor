package creativeeditor.screen;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataRotation;
import creativeeditor.data.tag.entity.TagEntityArmorStand.Pose;
import creativeeditor.util.ArmorStandDrawUtils;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import creativeeditor.widgets.SliderTag;
import creativeeditor.widgets.StyledButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class ArmorstandScreen extends ParentItemScreen {

	private ArmorStandEntity armorStand = null;
	private ArmorStandDrawUtils drawArmor;
	
	
	private int buttonWidth = 80;
	private int buttonHeight = 15;
	private int divideX = 120;
	private int divideY = 7;
	
	

	public ArmorstandScreen(Screen lastScreen, DataItem item) {
		super(new TranslationTextComponent("gui.armorstandeditor"), lastScreen, item);
		this.renderItem = false;

	}

	

	@Override
	protected void init() {
		super.init();
		int x1 = width / divideX;
		int y1 = height / divideY;
		if (armorStand == null) {
			ArmorStandEntity entity = new ArmorStandEntity(mc.world, 0, 0, 0);
			armorStand = entity;
			drawArmor = new ArmorStandDrawUtils(armorStand, item);
		}
		
		Pose pose = drawArmor.getStandData().getPose();

		addSliders(x1, y1, pose.getHead());
		y1 += (int) (buttonHeight * 1.5);
		addSliders(x1, y1, pose.getBody());
		y1 += (int) (buttonHeight * 1.5);
		if (drawArmor.getStandData().getShowArms().get()) {
			addSliders(x1, y1, pose.getRightArm());
			y1 += (int) (buttonHeight * 1.5);
			addSliders(x1, y1, pose.getLeftArm());
			y1 += (int) (buttonHeight * 1.5);
		}
		addSliders(x1, y1, pose.getRightLeg());
		y1 += (int) (buttonHeight * 1.5);
		addSliders(x1, y1, pose.getLeftLeg());
		y1 += (int) (buttonHeight * 2);

		int butWidth = 130;
		addButton(new StyledButton(x1 + (buttonWidth / 3), y1, butWidth, 18,
				I18n.format("gui.armorstandeditor.properties"), t -> {
					mc.displayGuiScreen(new ArmorstandPropScreen(this, item, armorStand));
				}));
		addButton(new StyledButton(x1 + (buttonWidth / 3) + butWidth + 5, y1, butWidth, 18,
				I18n.format("gui.armorstandeditor.equipment"), t -> {
					mc.displayGuiScreen(new ArmorStandEquipScreen(this, item, armorStand));
				}));

		
	}

	public void addSliders(int posX, int posY, DataRotation rot) {
		addButton(new SliderTag(posX + ((buttonWidth + 5) * 1), posY, buttonWidth, buttonHeight, rot.getX()));
		addButton(new SliderTag(posX + ((buttonWidth + 5) * 2), posY, buttonWidth, buttonHeight, rot.getY()));
		addButton(new SliderTag(posX + ((buttonWidth + 5) * 3), posY, buttonWidth, buttonHeight, rot.getZ()));
	}

	@Override
	public void reset(Widget w) {
		drawArmor.getStandData().getPose().reset();
	}

	@Override
	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		drawArmor.updateArmorStand();
		super.backRender(mouseX, mouseY, p3, color);
	}

	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		int x1 = width / divideX;
		int y1 = height / divideY;

		boolean arms = drawArmor.getStandData().getShowArms().get();
		for (BODY_PARTS s : (BODY_PARTS.values())) {
				if(!arms) {
					if(s == BODY_PARTS.LEFTARM || s == BODY_PARTS.RIGHTARM) {
						continue;
					}
				}
				drawCenteredString(font, I18n.format("gui.armorstandeditor." + s.toString().toLowerCase()), x1 + (buttonWidth / 3 * 2),
						y1 + (buttonHeight / 4), color.getInt());
				y1 += (int) (buttonHeight * 1.5);
			
		}

		super.mainRender(mouseX, mouseY, p3, color);
		if (armorStand != null) {
			drawArmor.drawArmorStand(armorStand, (int) (this.width / 3 * 2.5), (int) (this.height / 5 * 3.8), 70);
		}
	}

	@Override
	public void overlayRender(int mouseX, int mouseY, float p3, Color color) {
		super.overlayRender(mouseX, mouseY, p3, color);
		GuiUtil.addToolTip(this, resetButton, mouseX, mouseY, I18n.format("gui.armorstandeditor.reset"));
	}
	

	

	
	public enum BODY_PARTS {
		HEAD, BODY, RIGHTARM, LEFTARM, RIGHTLEG,
		LEFTLEG 
	}
}


