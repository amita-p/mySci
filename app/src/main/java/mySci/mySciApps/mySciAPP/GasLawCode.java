package mySci.mySciApps.mySciAPP;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidhive.R;


public class GasLawCode extends Activity {

    Button buttonCalculate;
    Button buttonClear;
    EditText tempField;
    EditText pressureField;
    EditText volumeField;
    EditText molesField;
    TextView answer;
    //boolean values used to determine which variable to solve for.
    boolean emptyTemp;
    boolean emptyPressure;
    boolean emptyVolume;
    boolean emptyMoles;
    double temp;
    double moles;
    double volume;
    double pressure;
    double rConstant;
    int numOfInfo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.gas_laws);
        click();
    }

    public void click() {
        //Select a specific button to bundle it with the action you want
        setContentView(R.layout.gas_laws);
        //declare all buttons and textfields used
        buttonCalculate = (Button) findViewById(R.id.buttonC);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        tempField = (EditText) findViewById(R.id.tAnswer);
        pressureField = (EditText) findViewById(R.id.pAnswer);
        volumeField = (EditText) findViewById(R.id.vAnswer);
        molesField = (EditText) findViewById(R.id.nAnswer);
        answer = (TextView) findViewById(R.id.ans);
        emptyMoles = false;
        emptyPressure = false;
        emptyTemp = false;
        emptyVolume = false;
        rConstant = 8.3144621;
        numOfInfo= 0;

        //decalre everything
        //when this button is clicked
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   try {
                                                       pressure = Double.parseDouble(pressureField.getText().toString());
                                                   } catch (NumberFormatException e) {
                                                       emptyPressure = true;
                                                       numOfInfo++;
                                                   }
                                                   try {
                                                       volume = Double.parseDouble(volumeField.getText().toString());
                                                   } catch (NumberFormatException e) {
                                                       emptyVolume = true;
                                                       numOfInfo++;
                                                   }
                                                   try {
                                                       moles = Double.parseDouble(molesField.getText().toString());
                                                   } catch (NumberFormatException e) {
                                                       emptyMoles = true;
                                                       numOfInfo++;
                                                   }
                                                   try {
                                                       temp = Double.parseDouble(tempField.getText().toString());
                                                   } catch (NumberFormatException e) {
                                                       emptyTemp = true;
                                                       numOfInfo++;
                                                   }
                                                   if(emptyMoles==false && emptyPressure==false && emptyVolume==false && emptyTemp==false)
                                                   {
                                                       answer.setText("There is too much input given.");
                                                   }
                                                   else if(numOfInfo>1)
                                                   {
                                                       answer.setText("There is not enough information given.");
                                                   }
                                                   else {

                                                       if (emptyTemp == true) {
                                                           temp = (pressure * volume) / (moles * rConstant);
                                                           tempField.setText(Double.toString((double) Math.round(temp * 100000 / 100000)), TextView.BufferType.EDITABLE);
                                                           answer.setText("");
                                                       }
                                                       if (emptyPressure == true) {
                                                           pressure = (moles * rConstant * temp) / volume;
                                                           pressureField.setText(Double.toString((double) Math.round(pressure * 100000 / 100000)), TextView.BufferType.EDITABLE);
                                                           answer.setText("");
                                                       }
                                                       if (emptyVolume == true) {
                                                           volume = (moles * rConstant * temp) / pressure;
                                                           volumeField.setText(Double.toString((double) Math.round(volume * 100000 / 100000)), TextView.BufferType.EDITABLE);
                                                           answer.setText("");
                                                       }
                                                       if (emptyMoles == true) {
                                                           moles = (pressure * volume) / (temp * rConstant);
                                                           molesField.setText(Double.toString((double) Math.round(moles * 100000) / 100000), TextView.BufferType.EDITABLE);
                                                           answer.setText("");
                                                       }
                                                   }

                                               }
                                           }
        );


        buttonClear.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               pressureField.setText("", TextView.BufferType.EDITABLE);
                                               volumeField.setText("", TextView.BufferType.EDITABLE);
                                               molesField.setText("", TextView.BufferType.EDITABLE);
                                               tempField.setText("", TextView.BufferType.EDITABLE);
                                               answer.setText("");

                                           }
                                       }
        );
    }


}
