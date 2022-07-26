package cc.polyfrost.oneconfig.polyUI.objects;


import cc.polyfrost.jtokens.objects.Dimension;

public class FontDimension {
    private final Unit unit;
    private final float value;

    public FontDimension(Unit unit, float value) {
        this.unit = unit;
        this.value = value;
    }

    public Unit getUnit() {
        return unit;
    }

    public float getPx(Dimension fontSize) {
        if (unit.equals(Unit.PX)) return value;
        if (unit.equals(Unit.REM)) return value * 16;
        return value * fontSize.getPx();
    }

    public float getRem(Dimension fontSize) {
        if (unit.equals(Unit.REM)) return value;
        if (unit.equals(Unit.PX)) return value / 16;
        return value * fontSize.getRem();
    }

    public float getEm(Dimension fontSize) {
        if (unit.equals(Unit.EM)) return value;
        if (unit.equals(Unit.PX)) return value / fontSize.getPx();
        return value / fontSize.getRem();
    }

    public enum Unit {
        PX,
        REM,
        EM
    }
}
