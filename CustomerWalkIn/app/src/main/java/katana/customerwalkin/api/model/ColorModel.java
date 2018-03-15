package katana.customerwalkin.api.model;

/**
 * ka
 * 14/10/2017
 */

public class ColorModel {

    private int textColor;
    private int buttonColor;

    public ColorModel(int textColor, int buttonColor) {
        this.textColor = textColor;
        this.buttonColor = buttonColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
    }
}
