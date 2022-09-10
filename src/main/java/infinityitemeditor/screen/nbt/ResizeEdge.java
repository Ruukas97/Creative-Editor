package infinityitemeditor.screen.nbt;

import lombok.Getter;

public enum ResizeEdge {
    TOP(Cursor.VERTICAL_RESIZE),
    TOPRIGHT(Cursor.DIAGONAL_RESIZE_NESW),
    RIGHT(Cursor.HORIZONTAL_RESIZE),
    BOTTOMRIGHT(Cursor.DIAGONAL_RESIZE_NWSE),
    BOTTOM(Cursor.VERTICAL_RESIZE),
    BOTTOMLEFT(Cursor.DIAGONAL_RESIZE_NESW),
    LEFT(Cursor.HORIZONTAL_RESIZE),
    TOPLEFT(Cursor.DIAGONAL_RESIZE_NWSE);

    @Getter
    private final Cursor cursor;

    ResizeEdge(Cursor cursor) {
        this.cursor = cursor;
    }

    public static ResizeEdge byEdges(boolean left, boolean right, boolean top, boolean bottom) {
        if (right) {
            if (top) {
                return TOPRIGHT;
            }
            if (bottom) {
                return BOTTOMRIGHT;
            }
            return RIGHT;
        }
        if (left) {
            if (top) {
                return TOPLEFT;
            }
            if (bottom) {
                return BOTTOMLEFT;
            }
            return LEFT;
        }
        if (top) {
            return TOP;
        }
        if (bottom) {
            return BOTTOM;
        }
        return null;
    }
}
