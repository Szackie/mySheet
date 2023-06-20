package form.szackie;

public class Form {
    private double depth;
    private double width;
    private int quantity;

    public Form(double depth, double width) {
        this.depth = depth;
        this.width = width;
        this.quantity=1;
    }

    public Form(double depth, double width, int quantity) {
        this.depth = depth;
        this.width = width;
        this.quantity = quantity;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
