package se.mickelus.tetra.gui.stats.getter;

public class LabelGetterBasic implements ILabelGetter {
    public static final ILabelGetter integerLabel = new LabelGetterBasic();
    public static final ILabelGetter decimalLabel = new LabelGetterBasic();
    public static final ILabelGetter percentageLabel = new LabelGetterBasic();
    public static final ILabelGetter percentageLabelDecimal = new LabelGetterBasic();

    @Override
    public String getLabel(double value, double diffValue, boolean flipped) {
        return "";
    }
}
