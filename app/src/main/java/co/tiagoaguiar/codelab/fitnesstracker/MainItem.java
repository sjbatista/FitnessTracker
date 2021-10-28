package co.tiagoaguiar.codelab.fitnesstracker;

public class MainItem {

    private int id;
    private int colorId;
    private int drawableId;
    private int titleId;

    public MainItem(int id, int colorId, int drawableId, int titleId) {
        this.id = id;
        this.colorId = colorId;
        this.drawableId = drawableId;
        this.titleId = titleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }
}
