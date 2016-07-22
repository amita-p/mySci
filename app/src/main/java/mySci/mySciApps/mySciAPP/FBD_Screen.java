package mySci.mySciApps.mySciAPP;

import com.example.androidhive.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Twins on 07/04/2015.
 */
public class FBD_Screen extends Activity {

    Button calculate;
    EditText mass;
    EditText acceleration;
    EditText force;
    TextView message;
    Button answer;
    double massValue;
    double accelerationValue;
    double forceValue;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.free_body_diagram);
        mass=(EditText) findViewById(R.id.editTextMass);
        acceleration=(EditText) findViewById(R.id.editTextAcceleration);
        force=(EditText) findViewById(R.id.editTextForce);
        answer=(Button) findViewById(R.id.clear);
        calculate = (Button) findViewById(R.id.button);
        message=(TextView)findViewById(R.id.message);
        click();
        click2();
    }

    public void click() {

        calculate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //enter in what needs to be done on button click
                //this checks the input from fields, making sure they are valid.
                int counter=0;
                try {
                    massValue=Double.parseDouble(mass.getText().toString());
                    }
                catch (NumberFormatException e){
                    mass.setText("", TextView.BufferType.EDITABLE);
                    counter++;
                }
                try {
                    accelerationValue=Double.parseDouble(acceleration.getText().toString());
                }
                //if(initialVelocity== && fVelocity==null)
                catch (NumberFormatException e){
                    acceleration.setText("", TextView.BufferType.EDITABLE);
                    counter++;
                }
                try {
                    forceValue=Double.parseDouble(force.getText().toString());
                }
                //if(initialVelocity== && fVelocity==null)
                catch (NumberFormatException e){
                    force.setText("", TextView.BufferType.EDITABLE);
                    counter++;
                }

                if(counter>=2){
                    message.setText("Too many unknown variables");
                }
                else if (counter==0)
                {
                    double ans = massValue*accelerationValue;
                    force.setText(String.valueOf(ans));
                    message.setText("");
                }
                else if (counter==1) {
                    if(mass.getText().toString().equals("")){
                        double ans = forceValue/accelerationValue;
                        mass.setText(String.valueOf(ans));
                    }
                    else if(acceleration.getText().toString().equals("")){
                        double ans = forceValue/massValue;
                        acceleration.setText(String.valueOf(ans));
                    }
                    else if(force.getText().toString().equals("")){
                        double ans = massValue*accelerationValue;
                        force.setText(String.valueOf(ans));
                    }
                    message.setText("");
                    counter=0;
                }
            }
        });
    }
    public void click2() {

        answer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                        mass.setText("");
                        acceleration.setText("");
                        force.setText("");
            }
        });
    }
}

