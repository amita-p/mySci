package mySci.mySciApps.mySciAPP;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidhive.R;

/*Talha do your magic. :D*/
public class Kinematics extends Activity  {

	Button button;
    Button button1;
    EditText iVelocity;
    EditText fVelocity;
    EditText timeValue;
    EditText displacementValue;
    EditText accelerationValue;
    TextView answer;
    private double initialVelocity;
    private double finalVelocity;
    private double time;
    private double displacement;
    private double acceleration;
    private int blankField=0;
    private boolean validIVelocity=true;
    private boolean validFVelocity=true;
    private boolean validTime=true;
    private boolean validDisplacement=true;
    private boolean validAcceleration=true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.kinematics_screen);
		click();
	}
	public void click() {
		//Select a specific button to bundle it with the action you want
        setContentView(R.layout.kinematics_screen);
		button = (Button) findViewById(R.id.CalculateButton);
        button1 = (Button) findViewById(R.id.ClearButton);
        answer = (TextView) findViewById(R.id.autoCompleteTextView);
        iVelocity=(EditText) findViewById(R.id.v1Input);
        fVelocity=(EditText) findViewById(R.id.v2Input);
        timeValue=(EditText) findViewById(R.id.tInput);
        displacementValue=(EditText) findViewById(R.id.dInput);
        accelerationValue=(EditText) findViewById(R.id.aInput);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
                validAcceleration=true;
                validDisplacement=true;
                validFVelocity=true;
                validIVelocity=true;
                validTime=true;
                blankField=0;
                //this checks the input from fileds, making sure they are valid.
                try {
                    initialVelocity = Double.parseDouble(iVelocity.getText().toString());
                    }
				//if(initialVelocity== && fVelocity==null)
                catch (NumberFormatException e){
                    iVelocity.setText("",TextView.BufferType.EDITABLE);
                    blankField++;
                    validIVelocity=false;
                }
                try{
                    finalVelocity = Double.parseDouble(fVelocity.getText().toString());
                }
                catch (NumberFormatException e)
                {
                   fVelocity.setText("",TextView.BufferType.EDITABLE);
                    blankField++;
                    validFVelocity=false;
                }
                try{
                    time = Double.parseDouble(timeValue.getText().toString());
                }
                catch (NumberFormatException e)
                {
                    timeValue.setText("",TextView.BufferType.EDITABLE);
                    blankField++;
                    validTime=false;
                }
                try{
                    displacement = Double.parseDouble(displacementValue.getText().toString());
                }
                catch (NumberFormatException e)
                {
                    displacementValue.setText("",TextView.BufferType.EDITABLE);
                    blankField++;
                    validDisplacement=false;
                }
                try{
                    acceleration = Double.parseDouble(accelerationValue.getText().toString());
                }
                catch (NumberFormatException e)
                {
                    accelerationValue.setText("",TextView.BufferType.EDITABLE);
                    blankField++;
                    validAcceleration=false;
                }
                //initialVelocity, finalVelocity, time, displacement, acceleration
                if(blankField>2)
                {
                answer.setText("Not enough information is given.");
                }
                else
                {answer.setText("");
                    blankField=0;
                }
                boolean zeroAcceleration=false;
                if (validAcceleration==true&&acceleration==0)
                {
                    zeroAcceleration=true;
                    if (initialVelocity!=finalVelocity)
                    {
                        answer.setText("This scenario is not possible.");
                    }
                    else if((initialVelocity==0 && finalVelocity==0))
                    {
                        answer.setText("");
                        timeValue.setText(">=0");
                        displacementValue.setText("0");
                    }
                    else
                    {
                        if (validTime==false && validDisplacement==false)
                        {
                            answer.setText("This scenario is not possible.");
                        }
                        else
                        {
                            if (validTime==true)
                            {

                                displacementValue.setText(Double.toString(initialVelocity*time));
                                answer.setText("");
                            }
                            else
                            {
                                if (displacement!=0) {
                                    timeValue.setText(Double.toString(initialVelocity / displacement));
                                }
                                else
                                {
                                    timeValue.setText("0");
                                }
                                answer.setText("");
                            }
                        }
                    }
                }
                //first valid answer output
                //1
              if( validIVelocity==true && validFVelocity==true && validAcceleration== true && zeroAcceleration==false)
              {

                  time=(finalVelocity-initialVelocity)/acceleration;
                  if(time>=0)
                  {
                      timeValue.setText(Double.toString((double)Math.round(time * 100000) / 100000),TextView.BufferType.EDITABLE);
                      displacement=0.5*acceleration*(Math.pow(time,2))+time*initialVelocity;
                      displacementValue.setText(Double.toString((double)Math.round(displacement * 100000) / 100000),TextView.BufferType.EDITABLE);
                  }
                  else
                  {
                      //timeValue.setText("This scenario is not possible",TextView.BufferType.EDITABLE);
                      answer.setText("This scenario is not possible",TextView.BufferType.EDITABLE);
                  }
              }
                //2
              else if(validFVelocity==true && validIVelocity==true && validTime==true && zeroAcceleration==false)
               {
                    acceleration=(finalVelocity-initialVelocity)/time;
                    accelerationValue.setText(Double.toString(acceleration),TextView.BufferType.EDITABLE);
                    displacement=0.5*acceleration*(Math.pow(time,2))+time*initialVelocity;
                    displacementValue.setText(Double.toString(displacement),TextView.BufferType.EDITABLE);
               }
                //3
               else if(validFVelocity==true && validIVelocity==true && validDisplacement==true&&zeroAcceleration==false)
               {
                   acceleration=(Math.pow(finalVelocity,2)-Math.pow(initialVelocity,2))/(2*displacement);
                   time=(finalVelocity-initialVelocity)/acceleration;
                   if(time>0)
                   {
                       timeValue.setText(Double.toString((double)Math.round(time * 100000) / 100000),TextView.BufferType.EDITABLE);
                       accelerationValue.setText(Double.toString((double)Math.round(acceleration * 100000) / 100000),TextView.BufferType.EDITABLE);
                   }
                   else
                   {
                       //timeValue.setText("This scenario is not possible",TextView.BufferType.EDITABLE);
                       answer.setText("This scenario is not possible",TextView.BufferType.EDITABLE);
                   }

               }
               //4
              else if(validAcceleration==true && validIVelocity==true && validTime==true && zeroAcceleration==false)
               {
                    finalVelocity=(acceleration*time) +initialVelocity;
                    fVelocity.setText(Double.toString((double)Math.round(finalVelocity * 100000) / 100000),TextView.BufferType.EDITABLE);
                    displacement=0.5*acceleration*(Math.pow(time,2))+time*initialVelocity;
                    displacementValue.setText(Double.toString((double)Math.round(displacement * 100000) / 100000),TextView.BufferType.EDITABLE);
               }
               //5
              else if(validAcceleration==true && validIVelocity==true && validDisplacement==true && zeroAcceleration==false)
               {
                   finalVelocity=(2*acceleration*displacement)+Math.pow(initialVelocity,2);
                   if(finalVelocity>=0 )
                   {
                       time= (initialVelocity*-1 + Math.sqrt(Math.pow(initialVelocity,2)-(4*0.5*acceleration*displacement*-1)))/(acceleration) ;
                       double time2 = (initialVelocity*-1 - Math.sqrt(Math.pow(initialVelocity,2)-(4*0.5*acceleration*displacement*-1)))/(acceleration) ;

                       if( time>0 && time2>0)
                       {
                           timeValue.setText(Double.toString((double)Math.round(time * 100000) / 100000)+" and "+Double.toString((double)Math.round(time2 * 100000) / 100000),TextView.BufferType.EDITABLE);
                           finalVelocity=(acceleration*time) +initialVelocity;
                           double finalVelocity2= (acceleration*time2) +initialVelocity;
                           fVelocity.setText(Double.toString((double)Math.round(finalVelocity * 100000) / 100000)+" and "+Double.toString((double)Math.round(finalVelocity2 * 100000) / 100000), TextView.BufferType.EDITABLE);
                       }
                       else if(time>0 && time2<0)
                       {
                           timeValue.setText(Double.toString((double)Math.round(time * 100000) / 100000),TextView.BufferType.EDITABLE);
                           finalVelocity=(acceleration*time) +initialVelocity;
                           fVelocity.setText(Double.toString((double)Math.round(finalVelocity * 100000) / 100000),TextView.BufferType.EDITABLE);
                       }
                       else if(time<0 && time2>0)
                       {
                           timeValue.setText(Double.toString((double)Math.round(time2 * 100000) / 100000),TextView.BufferType.EDITABLE);
                           double finalVelocity2= (acceleration*time2) +initialVelocity;
                           fVelocity.setText(Double.toString((double)Math.round(finalVelocity2 * 100000) / 100000),TextView.BufferType.EDITABLE);

                       }
                       else if(time<0 && time2<0)
                       {
                           answer.setText("This is not possible", TextView.BufferType.EDITABLE);
                       }
                       //time=(finalVelocity-initialVelocity)/acceleration;
                       //timeValue.setText(Double.toString((double)Math.round(time * 100000) / 100000),TextView.BufferType.EDITABLE);
                   }
                   else
                   {
                       //fVelocity.setText("This scenario is not possible", TextView.BufferType.EDITABLE);
                       answer.setText("This scenario is not possible", TextView.BufferType.EDITABLE);
                   }


               }
               //6
                else if(validTime==true && validIVelocity==true && validDisplacement==true && zeroAcceleration==false)
               {
                   finalVelocity=(acceleration*time) +initialVelocity;
                   fVelocity.setText(Double.toString((double)Math.round(finalVelocity * 100000) / 100000),TextView.BufferType.EDITABLE);
                   acceleration=(finalVelocity-initialVelocity)/time;
                   accelerationValue.setText(Double.toString((double)Math.round(acceleration * 100000) / 100000),TextView.BufferType.EDITABLE);
               }
               //7
                else if(validTime==true && validAcceleration==true && validDisplacement==true && zeroAcceleration==false)
                {
                initialVelocity=(0.5*acceleration*Math.pow(time,2))/time;
                iVelocity.setText(Double.toString((double)Math.round(initialVelocity * 100000) / 100000),TextView.BufferType.EDITABLE);
                finalVelocity=(acceleration*time) +initialVelocity;
                fVelocity.setText(Double.toString((double)Math.round(finalVelocity * 100000) / 100000),TextView.BufferType.EDITABLE);
                }
               //8
                else if(validTime==true && validFVelocity==true && validDisplacement==true&&zeroAcceleration==false)
                {
                    initialVelocity=(0.5*acceleration*Math.pow(time,2))/time;
                    iVelocity.setText(Double.toString((double)Math.round(initialVelocity * 100000) / 100000),TextView.BufferType.EDITABLE);
                    acceleration=(Math.pow(finalVelocity,2)-Math.pow(initialVelocity,2))/(2*displacement);
                    accelerationValue.setText(Double.toString((double)Math.round(acceleration * 100000) / 100000),TextView.BufferType.EDITABLE);
                }
                //9
                else if(validTime==true && validFVelocity==true && validAcceleration==true && zeroAcceleration==false)
                {
                    initialVelocity= (acceleration*time)-finalVelocity;
                    iVelocity.setText(Double.toString((double)Math.round(initialVelocity * 100000) / 100000),TextView.BufferType.EDITABLE);
                    displacement=0.5*acceleration*(Math.pow(time,2))+time*initialVelocity;
                    displacementValue.setText(Double.toString((double)Math.round(displacement * 100000) / 100000),TextView.BufferType.EDITABLE);
                }
                //10
                else if(validDisplacement==true && validFVelocity==true && validAcceleration==true && zeroAcceleration==false)
                {
                    initialVelocity=(2*acceleration*displacement)-Math.pow(finalVelocity,2);
                    if(initialVelocity>0)
                    {
                        initialVelocity=Math.sqrt((2*acceleration*displacement)-Math.pow(finalVelocity,2));
                        iVelocity.setText(Double.toString((double) Math.round(initialVelocity * 100000) / 100000), TextView.BufferType.EDITABLE);
                        time=(finalVelocity-initialVelocity)/acceleration;
                        timeValue.setText(Double.toString((double)Math.round(time * 100000) / 100000),TextView.BufferType.EDITABLE);
                    }
                    else
                    {
                        //iVelocity.setText("This scenario is not possible", TextView.BufferType.EDITABLE);
                        answer.setText("This scenario is not possible", TextView.BufferType.EDITABLE);
                    }
                }

			}
		}
        );
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                validAcceleration=true;
                validDisplacement=true;
                validFVelocity=true;
                validIVelocity=true;
                validTime=true;
                iVelocity.setText("",TextView.BufferType.EDITABLE);
                fVelocity.setText("",TextView.BufferType.EDITABLE);
                timeValue.setText("",TextView.BufferType.EDITABLE);
                displacementValue.setText("",TextView.BufferType.EDITABLE);
                accelerationValue.setText("",TextView.BufferType.EDITABLE);
                blankField=0;
            }
        }
        );
	}

}
