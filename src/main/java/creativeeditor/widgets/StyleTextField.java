package creativeeditor.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class StyleTextField extends TextFieldWidget{

	public StyleTextField(int p_i51137_2_, int p_i51137_3_, int p_i51137_4_, int p_i51137_5_,
			String msg) {
		super(Minecraft.getInstance().fontRenderer, p_i51137_2_, p_i51137_3_, p_i51137_4_, p_i51137_5_, msg);
	}

	
}
