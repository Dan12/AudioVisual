package dantech.com.audiovisual;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import java.util.ArrayList;

/**
 * Created by Danweb on 8/8/15.
 */

public class Main extends Activity {
    private DrawView drawView;
    private int width;
    private int height;
    private MediaPlayer music;
    private Visualizer visualizer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.WHITE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        System.out.println(width + " , " + height);
        drawView.setDimensions(width, height);

        setContentView(drawView);

        music = MediaPlayer.create(this, R.raw.secrets);

        setupVisualizer();

        music.start();
    }


    private void setupVisualizer(){
        System.out.println(Visualizer.getCaptureSizeRange()[0]+","+Visualizer.getCaptureSizeRange()[1]);
        System.out.println(Visualizer.getMaxCaptureRate());
        visualizer = new Visualizer(music.getAudioSessionId());
        visualizer.setCaptureSize(128);
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                //drawView.updateCanvas(bytes);
            }

            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                drawView.updateCanvas(bytes);
                //System.out.println(bytes.length);
            }
        }, 20000, false, true);
        visualizer.setEnabled(true);

        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                visualizer.setEnabled(false);
            }
        });
    }

    protected void onRestart(){
        System.out.println("Restarted");
        super.onRestart();
    }

    protected void onResume(){
        System.out.println("Resumed");
        super.onResume();
    }

    protected void onPause() {
        System.out.println("Paused");
        super.onPause();
        if(music.isPlaying())
            music.pause();
    }

    protected void onStop(){
        System.out.println("Stopped");
        super.onStop();
    }

    protected void onDestroy(){
        System.out.println("Destroyed");
        super.onDestroy();
        music.release();
        visualizer.release();
        finish();
    }

    /*Map fuction: scales x, which has in_min and in_max range, to out_min and out_max
    **If scaled value is outside range of out_min and out_max, make it in range*/
    public static float map(float x, float in_min, float in_max, float out_min, float out_max){
        float ret = (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
        if(ret > out_max)
            ret = out_max;
        if(ret < out_min)
            ret = out_min;
        return ret;
    }
}

