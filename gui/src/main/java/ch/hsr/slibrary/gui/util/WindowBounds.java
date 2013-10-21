package ch.hsr.slibrary.gui.util;

public class WindowBounds {

    public static final int WINDOW_POSITION_FILL_SCREEN = 0;
    public static final int WINDOW_POSITION_FILL_LEFT = 1;
    public static final int WINDOW_POSITION_FILL_RIGHT = 2;
    public static final int WINDOW_POSITION_RIGHT_TOP = 3;

    private int width = 0;
    private int height = 0;
    private int x = 0;
    private int y = 0;

    public WindowBounds(int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
