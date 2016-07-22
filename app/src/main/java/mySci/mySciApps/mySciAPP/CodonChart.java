package mySci.mySciApps.mySciAPP;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.example.androidhive.R;

/**
 * Created by Amita on 2015-06-28.
 */
public class CodonChart extends Activity {
    View mainView;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codonchart_layout);
    }
}
