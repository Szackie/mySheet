package form.szackie;



public class Form {
    private int type;
    private static int nextId=1;
    private int parentID=-1;  // only for wastes
    private int wasteID=-1; // only for forms
    private int id;
    /**
     * priority- determines the order of creation
     */
    private int priority=0;
    private int depth;
    private int width;
    private int quantity;
    private int area;
    private int comparator;


    public static int getNextId() {
        return nextId++;
    }


    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = getNextId();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }



    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getWasteID() {
        return wasteID;
    }

    public void setWasteID(int wasteID) {
        this.wasteID = wasteID;
    }



    public int getComparator() {
        return comparator;
    }

    public void setComparator(int comparator) {
        this.comparator = comparator;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public boolean isCutted() {
        return cutted;
    }

    public void setCutted(boolean cutted) {
        this.cutted = cutted;
    }

    private boolean cutted=false;

    public Form(int width, int depth) {
        this.id=getNextId();
        this.depth = depth;
        this.width = width;
        this.quantity = 1;
        this.area=width*depth;
        this.comparator=0;
    }

    public Form(int width, int depth, int quantity) {
        this.id=getNextId();
        this.depth = depth;
        this.width = width;
        this.quantity = quantity;
        this.area=width*depth;
        this.comparator=0;
    }

    @Override
    public String toString() {
        return id+
                ". " + depth +
                " x " + width +
                ", ilość: " + quantity +
                '}';
    }



    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
