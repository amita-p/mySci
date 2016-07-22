package mySci.mySciApps.mySciAPP;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidhive.R;

import java.text.NumberFormat;

/**
 * Created by Twins on 02/07/2015.
 */
public class AcidBase extends Activity {

    Button calculate;
    Button clear;
    TextView enter;//the text view on top with the text
    EditText amount;//they enter in the concentration for the strong acid
    TextView answer;
    Spinner kaValues;//has ka Values
    TextView answerWeak;//this is the answer for weak acids
    boolean isWeak = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.acid_base_layout);
        enter = (TextView) findViewById(R.id.Enter);//text on the top =calculate pH
        clear = (Button) findViewById(R.id.clear);//button to clear
        calculate = (Button) findViewById(R.id.button); // button to calculate
        //only if acid is strong does it appear
        amount = (EditText) findViewById(R.id.amount);//user enters amount
        answer = (TextView) findViewById(R.id.answer);//gives answer
        amount.setHint("Enter in the concentration.");
        //only if acid is weak does it appear
        kaValues = (Spinner) findViewById(R.id.spinner);//gives answer
        kaValues.setPrompt("Pick an acid");
        kaValues.setVisibility(View.INVISIBLE);
        answerWeak = (TextView) findViewById(R.id.answer_weak);//gives answer
        answerWeak.setVisibility(View.INVISIBLE);
        //comment out for when starting work on weak stuff
       RadioButton radGr = (RadioButton) findViewById(R.id.weak);//text on the top =calculate pH
        radGr.setVisibility(View.INVISIBLE);

        click();
        click2();
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.weak:
                if (checked)
                    isWeak = false;

                amount.setVisibility(View.INVISIBLE);
                answer.setVisibility(View.INVISIBLE);
                kaValues.setVisibility(View.VISIBLE);
                answerWeak.setVisibility(View.VISIBLE);
                break;
            case R.id.strong:
                if (checked)
                    isWeak = true;
                amount.setVisibility(View.VISIBLE);
                answer.setVisibility(View.VISIBLE);
                kaValues.setVisibility(View.INVISIBLE);
                answerWeak.setVisibility(View.INVISIBLE);
                break;
        }
    }


    public void click() {

        calculate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWeak == true) {
                    boolean calc = true;
                    double acid = 0.00;
                    try {
                        String userAmount = amount.getText().toString();//get user amount
                        acid = Double.parseDouble(userAmount);
                    } catch (NumberFormatException e) {
                        answer.setText("Invalid Input");
                        calc = false;
                    }

                    if (calc == true) {
                        double ans = 0;//this is the answer
                        ans = Math.log10(acid) * -1;
                        //this is for rounding
                        NumberFormat fmt = NumberFormat.getNumberInstance();
                        fmt.setMaximumFractionDigits(3);
                        fmt.setMinimumFractionDigits(3);
                        String s = fmt.format(ans);
                        ans = Double.parseDouble(s);
                        if (ans >= 14.00) {
                            ans = 14.00;
                        }
                        if (ans <= 0.00) {
                            ans = 0.00;
                        }
                        answer.setText(String.valueOf(ans));
                    }
                }
                else{
                    String text = kaValues.getSelectedItem().toString().trim();
                    answerWeak.setText(text);
                }

            }
        });
    }


    public void click2() {

        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                amount.setText("");
            }
        });
    }


}
