package dantech.com.audiovisual;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import java.util.ArrayList;

/**
 * Created by Danweb on 8/8/15.
 */

public class Main extends Activity {
    DrawView drawView;
    int width;
    int height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.WHITE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        System.out.println(width + " , " + height);
        drawView.setDimensions(width, height);

        setContentView(drawView);
    }
}

