package gonzaga.cpsc331.highfidelity.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PieChartView extends View {

    private final Paint slicePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint separatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF chartBounds = new RectF();

    private float[] values = new float[0];
    private int[] colors = new int[0];

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        slicePaint.setStyle(Paint.Style.FILL);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(2f);
        outlinePaint.setColor(0xFFFFFFFF);
        separatorPaint.setStyle(Paint.Style.STROKE);
        separatorPaint.setStrokeWidth(2f);
        separatorPaint.setStrokeCap(Paint.Cap.ROUND);
        separatorPaint.setColor(0xFFFFFFFF);
    }

    public void setChartData(float[] values, int[] colors) {
        this.values = values != null ? values : new float[0];
        this.colors = colors != null ? colors : new int[0];
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float total = 0f;
        for (float value : values) {
            total += value;
        }

        float size = Math.min(getWidth(), getHeight());
        float padding = size * 0.06f;
        float left = (getWidth() - size) / 2f + padding;
        float top = (getHeight() - size) / 2f + padding;
        float right = (getWidth() + size) / 2f - padding;
        float bottom = (getHeight() + size) / 2f - padding;
        chartBounds.set(left, top, right, bottom);

        if (total <= 0f) {
            slicePaint.setColor(0xFFE8E1EE);
            canvas.drawArc(chartBounds, 0, 360, true, slicePaint);
        } else {
            float startAngle = -90f;
            for (int i = 0; i < values.length; i++) {
                float sweep = (values[i] / total) * 360f;
                slicePaint.setColor(colors[i % colors.length]);
                canvas.drawArc(chartBounds, startAngle, sweep, true, slicePaint);
                startAngle += sweep;
            }

            drawSeparators(canvas, total);
        }

        canvas.drawOval(chartBounds, outlinePaint);
    }

    private void drawSeparators(Canvas canvas, float total) {
        float centerX = chartBounds.centerX();
        float centerY = chartBounds.centerY();
        float radius = chartBounds.width() / 2f;
        float startAngle = -90f;

        for (int i = 0; i < values.length - 1; i++) {
            startAngle += (values[i] / total) * 360f;

            double radians = Math.toRadians(startAngle);
            float endX = centerX + (float) (Math.cos(radians) * radius);
            float endY = centerY + (float) (Math.sin(radians) * radius);

            Path separator = new Path();
            separator.moveTo(centerX, centerY);
            separator.lineTo(endX, endY);
            canvas.drawPath(separator, separatorPaint);
        }
    }
}
