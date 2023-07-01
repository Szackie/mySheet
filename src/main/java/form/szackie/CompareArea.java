package form.szackie;

public class CompareArea {

    private int sumArea;
    private int formIndex;

    public CompareArea(int sumArea, int formIndex) {
        this.sumArea = sumArea;
        this.formIndex = formIndex;
    }

    public int getSumArea() {
        return sumArea;
    }

    public void setSumArea(int sumArea) {
        this.sumArea = sumArea;
    }

    public int getFormIndex() {
        return formIndex;
    }

    public void setFormIndex(int formIndex) {
        this.formIndex = formIndex;
    }
}
