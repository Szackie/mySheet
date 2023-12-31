package form.szackie;


public class Form {

    private static int nextId = 1;
    private int parentID = -1;  // only for wastes
    private int wasteID = -1; // only for forms
    private final int id;
    private final int depth;
    private final int width;
    private int quantity;
    private boolean used = false;
    private boolean done = false;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }


    public static int getNextId() {
        return nextId++;
    }


    public int getId() {
        return id;
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


    public boolean isCutted() {
        return cutted;
    }

    public void setCutted(boolean cutted) {
        this.cutted = cutted;
    }

    private boolean cutted = false;

    public Form(int width, int depth) {
        this.id = getNextId();
        if (width > depth) {
            this.width = depth;
            this.depth = width;
        } else {
            this.depth = depth;
            this.width = width;
        }
        this.quantity = 1;

    }

    public Form(int width, int depth, int quantity) {
        this.id = getNextId();
        if (width > depth) {
            this.width = depth;
            this.depth = width;
        } else {
            this.depth = depth;
            this.width = width;
        }
        this.quantity = quantity;

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Form))
            return false;
        return ((Form) o).getId() == this.id;
    }

    @Override
    public String toString() {
        return id +
                ". " + depth +
                " x " + width +
                ", ilość: " + quantity +
                '}';
    }


    public int getDepth() {
        return depth;
    }

    public int getWidth() {
        return width;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
