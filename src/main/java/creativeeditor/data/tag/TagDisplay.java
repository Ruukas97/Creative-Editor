package creativeeditor.data.tag;

import java.util.Map;

import javax.annotation.Nonnull;

import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataInteger;
import creativeeditor.data.base.DataList;
import creativeeditor.data.base.DataMap;
import creativeeditor.util.ColorUtils.Color;

public class TagDisplay extends DataMap {
	public TagDisplay() {
	}

	public TagDisplay(Map<String, Data<?,?>> map) {
		super(map);
	}

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

	@Override
	public TagDisplay copy() {
		return new TagDisplay(getMapCopy());
	}
}
