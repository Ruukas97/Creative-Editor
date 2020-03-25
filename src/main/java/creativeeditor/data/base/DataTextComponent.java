package creativeeditor.data.base;

import creativeeditor.data.tag.TagDisplayName;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataTextComponent extends SingularData<ITextComponent, StringNBT> {
	public DataTextComponent(ITextComponent data) {
		super(data);
	}
	
	public String getUnformatted() {
		return data.getUnformattedComponentText();
	}
	
	public String getFormatted() {
		System.out.println(data.getFormattedText());
		if(this instanceof TagDisplayName) {
			TagDisplayName name = (TagDisplayName) this;
			System.out.println(name.getItem().getItem().getItem().getName());
		}
		return data.getFormattedText();
	}
	
	public void set(String s) {
		set(new StringTextComponent(s));
	}

	@Override
	public boolean isDefault() {
		return data.getUnformattedComponentText().length() == 0;
	}

	@Override
	public StringNBT getNBT() {
		return new StringNBT(ITextComponent.Serializer.toJson(data));
	}
}
