package cc.polyfrost.oneconfig.polyUI;

import cc.polyfrost.jtokens.parsers.Parser;
import cc.polyfrost.jtokens.strategy.DefaultTypeResolutionStrategy;
import cc.polyfrost.oneconfig.polyUI.parsers.FontDimensionParser;
import cc.polyfrost.oneconfig.polyUI.parsers.TypographyParser;

public class OneConfigTypeResolutionStrategy extends DefaultTypeResolutionStrategy {
    @Override
    public Parser<?> getParser(String type) {
        switch (type) {
            case "fontDimension":
                return FontDimensionParser.INSTANCE;
            case "typography":
                return TypographyParser.INSTANCE;
            default:
                return super.getParser(type);
        }
    }
}
