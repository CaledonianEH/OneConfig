package cc.polyfrost.oneconfig.gui.elements;

import cc.polyfrost.oneconfig.lwjgl.RenderManager;
import cc.polyfrost.oneconfig.utils.ColorUtils;
import cc.polyfrost.oneconfig.utils.InputUtils;

public class BasicElement {
    protected int width, height;
    protected int colorPalette;
    protected int hitBoxX, hitBoxY;
    protected boolean hoverFx;
    protected boolean hovered = false;
    protected boolean clicked = false;
    protected boolean toggled = false;
    protected boolean disabled = false;
    protected int currentColor;

    public BasicElement(int width, int height, int colorPalette, boolean hoverFx) {
        this.height = height;
        this.width = width;
        this.colorPalette = colorPalette;
        this.hoverFx = hoverFx;
    }

    public BasicElement(int width, int height, boolean hoverFx) {
        this.height = height;
        this.width = width;
        this.colorPalette = -1;
        this.hoverFx = hoverFx;
    }


    public void draw(long vg, int x, int y) {
        RenderManager.drawRectangle(vg, x, y, width, height, currentColor);

        update(x, y);
        if (hoverFx) {
            currentColor = ColorUtils.getColor(currentColor, colorPalette, hovered, clicked);
        }
    }

    public void update(int x, int y) {
        if (disabled) {
            hovered = false;
            clicked = false;
            return;
        }
        hovered = InputUtils.isAreaHovered(x - hitBoxX, y - hitBoxY, width + hitBoxX, height + hitBoxY);
        clicked = InputUtils.isClicked() && hovered;

        if (clicked) {
            toggled = !toggled;
            onClick();
        }
    }


    public void onClick() {

    }

    public void setCustomHitbox(int x, int y) {
        hitBoxX = x;
        hitBoxY = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setColorPalette(int colorPalette) {
        this.colorPalette = colorPalette;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHovered() {
        return hovered;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void disable(boolean state) {
        disabled = state;
    }
}
