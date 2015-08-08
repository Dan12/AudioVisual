package dantech.com.audiovisual;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.view.View;

/**
 * Created by Danweb on 8/8/15.
 */
public class DrawView extends View {

    private Paint paint = new Paint();
    private int[] spectrumBand;
    private int offset = 400;
    private int scale = 40;
    private int bandNums = 16;

    public DrawView(Context context) {
        super(context);
    }

    public void updateCanvas(byte[] bytes){
        spectrumBand = new int[bytes.length/2];
        for(int i = 0; i < bytes.length / 2; i++){
            byte rfk = bytes[2 * i];
            byte ifk = bytes[2 * i + 1];
            float magnitude = (float) (rfk * rfk + ifk * ifk);
            spectrumBand[i] = (int) (10 * Math.log10(magnitude));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //System.out.println(Main.map(spectrumBand[2], 0, 5, 0.1f, 0.7f));
        paint.setShader(new RadialGradient(150, 150, 100, new int[]{Color.WHITE, Color.WHITE, Color.BLUE}, new float[]{0.05f, Main.map(spectrumBand[1], 0, 30, 0.1f, 0.4f), 0.8f}, Shader.TileMode.CLAMP));
        canvas.drawRect(100, 100, 200, 200, paint);

        float[] mPoints = new float[spectrumBand.length*4];
        paint.setShader(null);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(6);
        for (int i = 0; i < spectrumBand.length; i++) {
            mPoints[i * 4] = i * 8;
            mPoints[i * 4 + 1] = 0;
            mPoints[i * 4 + 2] = i * 8;
            int dbValue = spectrumBand[i];
            mPoints[i * 4 + 3] = (float) (dbValue * 7);
        }
        canvas.drawLines(mPoints, paint);

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();
    }

    public void releaseAssets(){
    }

    public void setDimensions(int width, int height){

    }
}
