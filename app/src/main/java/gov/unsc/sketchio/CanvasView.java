package gov.unsc.sketchio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class CanvasView extends View {

    private Paint paint;
    private int color = Color.argb(255, 164, 198, 57);
    private int STROKE_WIDTH = 10;
    private float dx, dy;
    private Path path;
    private Rect rect;
    private RectF circle;

    private static final int bkgrdColor = 0;

    private ArrayList<DrawObject> objs = new ArrayList<>();

    enum Shapes {
        FREE("Free Form"), RECT("Rectangle"), CIRCLE("Circle");

        private String title;

        Shapes(String title) {
            this.title = title;
        }

        public String toString() {
            return title;
        }
    };
    private Shapes currentShape = Shapes.FREE;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        for (DrawObject o : objs) {
            paint.setColor(o.getColor());
            switch (o.getType()) {
                case FREE:
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawPath((Path) o.getO(), paint);
                    break;
                case RECT:
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect((Rect) o.getO(), paint);
                    break;
                case CIRCLE:
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawOval((RectF) o.getO(), paint);
            }
        }

    }

    public void update() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (currentShape == Shapes.FREE) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path = new Path();
                    objs.add(new DrawObject(Shapes.FREE, color, path));
                    path.reset();
                    path.moveTo(x, y);
                    dx = x;
                    dy = y;
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(dx - x) >= 4 || Math.abs(dy - y) >= 4) {
                        path.quadTo(dx, dy, (x + dx) / 2, (y + dy) / 2);
                        dx = x;
                        dy = y;
                    }

                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    path.lineTo(x, y);
                    invalidate();
                    break;
            }
        } else if (currentShape == Shapes.RECT){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    rect = new Rect((int) x, (int) y, (int) x, (int) y);
                    objs.add(new DrawObject(Shapes.RECT, color, rect));
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    rect.bottom = (int) y;
                    rect.right = (int) x;
                    invalidate();
                    break;
            }
        } else if (currentShape == Shapes.CIRCLE) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    circle = new RectF(x, y, x, y);
                    objs.add(new DrawObject(Shapes.CIRCLE, color, circle));
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    circle.bottom = (int) y;
                    circle.right = (int) x;
                    invalidate();
                    break;
            }
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(Math.max(width * 4 / 5, 300), Math.max(height * 4 / 5, 500));
    }
}
