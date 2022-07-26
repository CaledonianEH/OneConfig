package cc.polyfrost.oneconfig.polyUI.parsers;

import cc.polyfrost.jtokens.objects.Dimension;
import cc.polyfrost.jtokens.objects.Typography;
import cc.polyfrost.jtokens.parsers.Parser;
import cc.polyfrost.jtokens.parsers.type.DimensionParser;
import cc.polyfrost.jtokens.parsers.type.FontFamilyParser;
import cc.polyfrost.jtokens.parsers.type.FontWeightParser;
import cc.polyfrost.oneconfig.polyUI.objects.FontDimension;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class TypographyParser extends Parser<Typography> {
    public static final TypographyParser INSTANCE = new TypographyParser();

    @Override
    protected Typography parseValue(JsonElement element, HashMap<String, Object> values) {
        JsonObject object = element.getAsJsonObject();
        String[] fontFamily = FontFamilyParser.INSTANCE.parse(object.get("fontFamily"), values);
        Dimension fontSize = DimensionParser.INSTANCE.parse(object.get("fontSize"), values);
        int fontWeight = FontWeightParser.INSTANCE.parse(object.get("fontWeight"), values);
        FontDimension letterSpacingDimension = FontDimensionParser.INSTANCE.parse(object.get("letterSpacing"), values);
        Dimension letterSpacing = new Dimension(Dimension.Unit.PX, letterSpacingDimension.getPx(fontSize));
        FontDimension lineHeightDimension = FontDimensionParser.INSTANCE.parse(object.get("lineHeight"), values);
        float lineHeight = lineHeightDimension.getEm(fontSize);
        return new Typography(fontFamily, fontSize, fontWeight, letterSpacing, lineHeight);
    }
}
