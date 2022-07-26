package cc.polyfrost.oneconfig.polyUI.parsers;

import cc.polyfrost.jtokens.parsers.Parser;
import cc.polyfrost.oneconfig.polyUI.objects.FontDimension;
import com.google.gson.JsonElement;

import java.util.HashMap;

public class FontDimensionParser extends Parser<FontDimension> {
    public static final FontDimensionParser INSTANCE = new FontDimensionParser();

    @Override
    protected FontDimension parseValue(JsonElement element, HashMap<String, Object> values) {
        String value = element.getAsString();
        if (value.endsWith("px"))
            return new FontDimension(FontDimension.Unit.PX, Float.parseFloat(value.replace("px", "")));
        else if (value.endsWith("rem"))
            return new FontDimension(FontDimension.Unit.REM, Float.parseFloat(value.replace("rem", "")));
        return new FontDimension(FontDimension.Unit.EM, Float.parseFloat(value.replace("em", "")));
    }
}
