package creativeeditor.data.base;

import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;

public class DataTextComponent extends SingularData<ITextComponent, StringNBT> {
	public DataTextComponent(ITextComponent data) {
		super(data);
	}
	
	public String getUnformattedText() {
		return data.getUnformattedComponentText();
	}
	
	public String getFormattedText() {
		return data.getFormattedText();
	}

	@Override
	public DataTextComponent copy() {
		return new DataTextComponent(data.deepCopy());
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
