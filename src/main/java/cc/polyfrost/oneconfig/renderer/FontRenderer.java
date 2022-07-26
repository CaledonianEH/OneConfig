package cc.polyfrost.oneconfig.renderer;

import cc.polyfrost.jtokens.objects.Typography;
import cc.polyfrost.oneconfig.polyUI.PolyUI;
import cc.polyfrost.oneconfig.renderer.font.Font;
import cc.polyfrost.oneconfig.renderer.font.Fonts;
import cc.polyfrost.oneconfig.utils.InputUtils;
import cc.polyfrost.oneconfig.utils.NetworkUtils;
import com.google.common.annotations.Beta;
import org.lwjgl.nanovg.NVGColor;

import java.util.ArrayList;

import static cc.polyfrost.oneconfig.renderer.RenderManager.color;
import static org.lwjgl.nanovg.NanoVG.*;

public class FontRenderer {
    private final Typography typography;
    private final Font font;

    public FontRenderer(Typography typography) {
        this.typography = typography;
        switch (typography.getFontWeight()) {
            case 400:
                font = Fonts.REGULAR;
                break;
            case 500:
                font = Fonts.MEDIUM;
                break;
            case 600:
                font = Fonts.SEMIBOLD;
                break;
            case 700:
                font = Fonts.BOLD;
                break;
            default:
                throw new IllegalArgumentException(typography.getFontWeight() + "Is not a valid font weight!");
        }
    }

    /**
     * Draws a String with the given parameters.
     *
     * @param vg            The NanoVG context.
     * @param text          The text.
     * @param x             The x position.
     * @param y             The y position.
     * @param color         The color.
     * @see cc.polyfrost.oneconfig.renderer.font.Font
     */
    public void drawText(long vg, String text, float x, float y, int color) {
        nvgBeginPath(vg);
        nvgFontSize(vg, typography.getFontSize().getPx());
        nvgTextLetterSpacing(vg, typography.getLetterSpacing().getPx());
        nvgFontFace(vg, font.getName());
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
        NVGColor nvgColor = color(vg, color);
        nvgText(vg, x, y, text);
        nvgFill(vg);
        nvgColor.free();
    }

    /**
     * Draws a String with the given parameters.
     *
     * @param vg            The NanoVG context.
     * @param text          The text.
     * @param x             The x position.
     * @param y             The y position.
     * @param color         The color.
     * @see cc.polyfrost.oneconfig.renderer.font.Font
     */
    public void drawText(long vg, ArrayList<String> text, float x, float y, int color) {
        float textY = y;
        for (String line : text) {
            drawText(vg, line, x, textY, color);
            textY += typography.getFontSize().getPx() * typography.getLineHeight();
        }
    }

    /**
     * Draws a String wrapped at the given width, with the given parameters.
     *
     * @param vg            The NanoVG context.
     * @param text          The text.
     * @param x             The x position.
     * @param y             The y position.
     * @param width         The width.
     * @param color         The color.
     */
    public void drawWrappedString(long vg, String text, float x, float y, float width, int color) {
        nvgBeginPath(vg);
        nvgFontSize(vg, typography.getFontSize().getPx());
        nvgTextLetterSpacing(vg, typography.getLetterSpacing().getPx());
        nvgTextLineHeight(vg, typography.getFontSize().getPx() * typography.getLineHeight());
        nvgFontFace(vg, font.getName());
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
        NVGColor nvgColor = color(vg, color);
        nvgTextBox(vg, x, y, width, text);
        nvgFill(vg);
        nvgColor.free();
    }

    /**
     * Draw a formatted URL (a string in blue with an underline) that when clicked, opens the given text.
     *
     * <p><b>This does NOT scale to Minecraft's GUI scale!</b></p>
     *
     * @see RenderManager#drawText(long, String, float, float, int, float, Font)
     * @see InputUtils#isAreaClicked(float, float, float, float)
     */
    public void drawURL(long vg, String url, float x, float y, float size) {
        drawText(vg, url, x, y, PolyUI.getToken().getColor("polyui.core.color.primary.500").getRGB());
        float length = getTextWidth(vg, url);
        RenderManager.drawRect(vg, x, y + size / 2, length, 1, PolyUI.getToken().getColor("polyui.core.color.primary.500").getRGB());
        if (InputUtils.isAreaClicked((int) (x - 2), (int) (y - 1), (int) (length + 4), (int) (size / 2 + 3))) {
            NetworkUtils.browseLink(url);
        }
    }

    /**
     * Get the width of the provided String.
     *
     * @param vg            The NanoVG context.
     * @param text          The text.
     * @return The width of the text.
     */
    public float getTextWidth(long vg, String text) {
        float[] bounds = new float[4];
        nvgFontSize(vg, typography.getFontSize().getPx());
        nvgTextLetterSpacing(vg, typography.getLetterSpacing().getPx());
        nvgFontFace(vg, font.getName());
        return nvgTextBounds(vg, 0, 0, text, bounds);
    }

    /**
     * Wraps a string into an array of lines.
     *
     * @param vg            The NanoVG context.
     * @param text          The text to wrap.
     * @param maxWidth      The maximum width of each line.
     * @return The array of lines.
     */
    @Beta
    public ArrayList<String> wrapText(long vg, String text, float maxWidth) {
        ArrayList<String> wrappedText = new ArrayList<>();
        text += " ";
        int prevIndex = 0;
        for (int i = text.indexOf(" "); i >= 0; i = text.indexOf(" ", i + 1)) {
            String textPart = text.substring(0, i);
            float textWidth = getTextWidth(vg, textPart);
            if (textWidth < maxWidth) {
                prevIndex = i;
                continue;
            }
            wrappedText.add(text.substring(0, prevIndex) + " ");
            wrappedText.addAll(wrapText(vg, text.substring(prevIndex + 1), maxWidth));
            break;
        }
        if (wrappedText.size() == 0) wrappedText.add(text);
        String temp = wrappedText.get(wrappedText.size() - 1);
        if (temp.length() != 0) {
            wrappedText.remove(wrappedText.size() - 1);
            wrappedText.add(temp.substring(0, temp.length() - 1));
        }
        return wrappedText;
    }
}
