package gov.unsc.sketchio;

public class DrawObject {

    private CanvasView.Shapes type;
    private int color;
    private Object o;

    public DrawObject(CanvasView.Shapes type, int color, Object o) {
        this.type = type;
        this.color = color;
        this.o = o;
    }

    public CanvasView.Shapes getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Object getO() {
        return o;
    }
}
