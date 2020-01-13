package creativeeditor.data.tag;

import javax.annotation.Nonnull;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataInteger;
import creativeeditor.data.base.DataList;
import creativeeditor.data.base.DataMap;
import creativeeditor.util.ColorUtils.Color;

public class TagDisplay extends DataMap {
	@Nonnull
	public DataInteger getColorTag() {
		return (DataInteger) getDataDefaulted("color", new DataInteger(new Color(255, 255, 255, 255).getInt()));
	}
	
	@Nonnull
	public TagDisplayName getNameTag(DataItem item) {
		return (TagDisplayName) getDataDefaultedForced("Name", new TagDisplayName(item));
	}
	
	@Nonnull
	public DataList getLoreTag() {
		return (DataList) getDataDefaulted("Lore", new DataList());
	}
}
