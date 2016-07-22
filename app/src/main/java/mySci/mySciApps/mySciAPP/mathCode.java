package mySci.mySciApps.mySciAPP;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidhive.R;

import java.text.NumberFormat;

/**
 * Created by Twins on 2015-04-09.
 */
public class mathCode extends Activity {

    Button calculate;
    EditText a;
    EditText b;
    EditText c;
    TextView xIntercepts;
    double aNum;
    double bNum;
    double cNum;
    double x1;
    double x2;
    boolean valid;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.quadratic_screen);
        a = (EditText) findViewById(R.id.aAnswer);
        b = (EditText) findViewById(R.id.bAnswer);
        c = (EditText) findViewById(R.id.cAnswer);
        xIntercepts = (TextView) findViewById(R.id.finalAnswer);
        calculate = (Button) findViewById(R.id.buttonC);
        double aNum;
        double bNum;
        double cNum;
        valid = true;
        clickMath();
    }

    public void clickMath() {

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    aNum = Double.parseDouble(a.getText().toString());
                }
                //if(initialVelocity== && fVelocity==null)
                catch (NumberFormatException e) {

                    xIntercepts.setText("Enter valid values.", TextView.BufferType.EDITABLE);
                    valid = false;

                }

                try {
                    bNum = Double.parseDouble(b.getText().toString());
                }
                //if(initialVelocity== && fVelocity==null)
                catch (NumberFormatException e) {
                    xIntercepts.setText("Enter valid values.", TextView.BufferType.EDITABLE);
                    valid = false;
                }
                try {
                    cNum = Double.parseDouble(c.getText().toString());
                }
                //if(initialVelocity== && fVelocity==null)
                catch (NumberFormatException e) {
                    xIntercepts.setText("Enter valid values.", TextView.BufferType.EDITABLE);
                    valid = false;
                }
                if (valid == false)
                {
                    if (xIntercepts.getText().equals("Enter valid values.")==false)
                    {
                        xIntercepts.setText("Not enough information");
                    }

                }
                else {
                    //checking if there are even valid roots after given a valid equation
                    if(aNum==0)
                    {
                        if (bNum!=0) {
                            xIntercepts.setText((Double.toString((cNum * -1) / bNum)+" is the only root."), TextView.BufferType.EDITABLE);
                        }
                        else if (bNum==0){
                            xIntercepts.setText("There are no roots");
                        }
                    }
                    else{



                    if ((Math.pow(bNum, 2) - (4 * aNum * cNum)) >= 0) {
                        x1 =  (((bNum * -1) + (Math.sqrt(Math.pow(bNum, 2) - (4 * aNum * cNum)))) / (2 * aNum));
                        x2 =  (((bNum * -1) - (Math.sqrt(Math.pow(bNum, 2) - (4 * aNum * cNum)))) / (2 * aNum));
                        NumberFormat fmt = NumberFormat.getNumberInstance();
                        fmt.setMaximumFractionDigits(5);
                        fmt.setMinimumFractionDigits(0);
                        String xOne= fmt.format(x1);
                        String xTwo= fmt.format(x2);
                        x1= Double.parseDouble(xOne);
                        x2= Double.parseDouble(xTwo);
                        if(x1!=x2)
                        {
                            xIntercepts.setText(String.valueOf(x1) + " and " + String.valueOf(x2) + " are roots.");
                        }
                        else
                        {
                            xIntercepts.setText(String.valueOf(x1)+" is the only root.");
                        }
                    } else {
                        xIntercepts.setText("There are no real roots.");
                    }
                }}
                valid = true;

            }
        });
    }


}
