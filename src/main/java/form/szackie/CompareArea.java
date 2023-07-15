package form.szackie;

public class CompareArea {

    private int sumArea;
    private int formIndex1;


    private int formIndex2;

    public CompareArea(int sumArea, int formIndex1,int formIndex2) {
        this.sumArea = sumArea;
        this.formIndex1 = formIndex1;
        this.formIndex2 = formIndex2;
    }

    public int getSumArea() {
        return sumArea;
    }

    public void setSumArea(int sumArea) {
        this.sumArea = sumArea;
    }

    public int getFormIndex1() {
        return formIndex1;
    }

    public void setFormIndex1(int formIndex1) {
        this.formIndex1 = formIndex1;
    }

    public int getFormIndex2() {
        return formIndex2;
    }

    public void setFormIndex2(int formIndex2) {
        this.formIndex2 = formIndex2;
    }
}
