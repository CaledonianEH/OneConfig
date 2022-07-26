package cc.polyfrost.oneconfig.polyUI;

import cc.polyfrost.jtokens.DesignToken;
import cc.polyfrost.oneconfig.renderer.FontRenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PolyUI {
    private static DesignToken token;
    private static FontRenderer TWO_XL_REGULAR;
    private static FontRenderer TWO_XL_MEDIUM;
    private static FontRenderer TWO_XL_SEMIBOLD;
    private static FontRenderer TWO_XL_BOLD;
    private static FontRenderer XL_REGULAR;
    private static FontRenderer XL_MEDIUM;
    private static FontRenderer XL_SEMIBOLD;
    private static FontRenderer XL_BOLD;

    public static void load() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(PolyUI.class.getResourceAsStream("/assets/oneconfig/polyui.tokens.json"), StandardCharsets.UTF_8))) {
            token = new DesignToken(new OneConfigTypeResolutionStrategy(), reader);
            TWO_XL_REGULAR = new FontRenderer(token.getTypography("polyui.core.typography.display.2xl.regular"));
            TWO_XL_MEDIUM = new FontRenderer(token.getTypography("polyui.core.typography.display.2xl.medium"));
            TWO_XL_SEMIBOLD = new FontRenderer(token.getTypography("polyui.core.typography.display.2xl.semibold"));
            TWO_XL_BOLD = new FontRenderer(token.getTypography("polyui.core.typography.display.2xl.bold"));
            XL_REGULAR = new FontRenderer(token.getTypography("polyui.core.typography.display.xl.regular"));
            XL_MEDIUM = new FontRenderer(token.getTypography("polyui.core.typography.display.xl.medium"));
            XL_SEMIBOLD = new FontRenderer(token.getTypography("polyui.core.typography.display.xl.semibold"));
            XL_BOLD = new FontRenderer(token.getTypography("polyui.core.typography.display.xl.bold"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DesignToken getToken() {
        return token;
    }

    public static FontRenderer getTwoXlBold() {
        return TWO_XL_BOLD;
    }

    public static FontRenderer getTwoXlMedium() {
        return TWO_XL_MEDIUM;
    }

    public static FontRenderer getTwoXlRegular() {
        return TWO_XL_REGULAR;
    }

    public static FontRenderer getTwoXlSemibold() {
        return TWO_XL_SEMIBOLD;
    }

    public static FontRenderer getXlBold() {
        return XL_BOLD;
    }

    public static FontRenderer getXlMedium() {
        return XL_MEDIUM;
    }

    public static FontRenderer getXlRegular() {
        return XL_REGULAR;
    }

    public static FontRenderer getXlSemibold() {
        return XL_SEMIBOLD;
    }
}
