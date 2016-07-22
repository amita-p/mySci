package mySci.mySciApps.mySciAPP;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidhive.R;

/**
 * Created by Amita on 2015-06-28.
 */
public class BioOptionScreen extends Activity {
    Button codonButton;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.bio_option_screen);
        click1();


    }
    public void click1() {
        //Select a specific button to bundle it with the action you want
        codonButton = (Button) findViewById(R.id.codon);
        codonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cal = new Intent(getApplicationContext(), CodonChart.class);
                // passing array index
                startActivity(cal);
            }
        });
    }
}
