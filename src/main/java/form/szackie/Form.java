package form.szackie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Form {

    private static int nextId=1;

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

    private int id;
    /**
     * priority- determines the order of creation
     */
    private int priority=0;
    private int depth;
    private int width;
    private int quantity;

    public Form(int depth, int width) {
        this.id=getNextId();
        this.depth = depth;
        this.width = width;
        this.quantity = 1;
    }

    public Form(int depth, int width, int quantity) {
        this.id=getNextId();
        this.depth = depth;
        this.width = width;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id+
                ". " + depth +
                " x " + width +
                ", ilość: " + quantity +
                '}';
    }

    public Form(String text) {

        this.id=getNextId();


        Pattern pattern = Pattern.compile("-?\\d+"); // Wzorzec dla liczby całkowitej(MOżE być ujemna!!!)
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            this.depth = Integer.parseInt(matcher.group());
        }
        if (matcher.find()) {
            this.width = Integer.parseInt(matcher.group());
        }
        if (matcher.find()) {
            this.quantity = Integer.parseInt(matcher.group());
        }
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
