package gov.unsc.sketchio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

public class CanvasView extends View {

    private Paint paint;
    private Paint bitmapPaint = new Paint();
    private int color = Color.argb(255, 164, 198, 57);
    private int STROKE_WIDTH = 10;
    private Bitmap bitmap;
    private Canvas canvas;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(color);
    }

    public void init(DisplayMetrics dm) {
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    public void update() {

    }
}
