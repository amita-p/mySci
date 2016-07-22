package mySci.mySciApps.mySciAPP;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.androidhive.R;

/*Whenever you click an element, it displays the name, molar mass and a button
 * to go to the next screen to calculate stuff. We're working on the last part :D*/
public class MolarMass extends Activity {

	Button button;
	EditText editText;
	EditText editTextQuant;
	TextView textView;
	private ArrayList <Element> myList;
	Element [] pTable; //array of all the elements
	private final String TAG = "** subActivity **";
	boolean mass=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent cal = getIntent();
		setContentView(R.layout.molar_mass_calculate);
		editText = (EditText) findViewById(R.id.compound);
		editTextQuant = (EditText) findViewById(R.id.quantity);
		textView=(TextView) findViewById(R.id.text_view); //textview that displays answer
		textView.setVisibility(View.INVISIBLE); //initially it is invisible
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		myList = getIntent().getParcelableArrayListExtra ("mylist"); 
		pTable= new Element[118];
		for (int i = 0; i < myList.size (); i++) //filling in the array of all elements
		{
			Element a = myList.get (i);
			pTable[i]=a;
		}
		addListenerOnButton();

	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch(view.getId()) {
		case R.id.radio_moles:
			if (checked)
				mass=true; //we need to find the mass
			    editTextQuant.setHint("Enter in number of moles");
			break;
		case R.id.radio_mass:
			if (checked)
				mass=false; //we don't need to find the mass, we need to find the number of moles
			editTextQuant.setHint("Enter in mass (g)");
			break;
		}
	}
	public void addListenerOnButton() {
		//Select a specific button to bundle it with the action you want
		button = (Button) findViewById(R.id.button1); //enter button
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

               // this converts it using molar mass
				String formula= editText.getText().toString() ;//takes the formula
				String val= editTextQuant.getText().toString();//takes the amount
				boolean valid=true;//is the input valid?
				formula=formula.replace(" ", "");
				char[] formulaChars=formula.toCharArray();
				int[] formulaCharsInt=new int[formulaChars.length];
				double molarMass=0;
				if (isInteger(val)==false)
				{
					valid=false;
				}
				for (int counter=0;counter<formulaChars.length;counter++)
				{
					formulaCharsInt[counter]=formulaChars[counter];
				}
				try
				{
					molarMass=getMolarMass(formula,pTable);
					if (molarMass!=0)
						System.out.println(mass);
					else
					{
						valid=false;
					}
				}
				catch (Exception ex)
				{
					valid=false;
				}
				catch (StackOverflowError ex)
				{
					valid=false;
				}
				if (valid==true)
				{
					textView.setVisibility(View.VISIBLE);
					if (mass==true) {
						textView.setText(Double.toString(molarMass * Integer.parseInt(val)));
					}
					else
					{
						textView.setText(Double.toString((Integer.parseInt(val)/molarMass)));
					}
				}
				else if (valid==false)
				{
					textView.setVisibility(View.VISIBLE);
					textView.setText("Invalid input");
				}

			}
		});
	}
	public static boolean isBracket(int charInt)
	{
		if (charInt>=40 && charInt<=41)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean isUpper(int charInt)
	{
		if (charInt>=65 && charInt<=90)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean isLower(int charInt)
	{
		if (charInt>=97 && charInt<=122)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean isInteger(char character)
	{
		String str=Character.toString(character);
		try{
			int num = Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	public static boolean isInteger(String str)
	{

		try{
			int num = Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	public static double getMolarMass(String formula, Element[] pTable)
	{
		double returnDouble=0;
		formula=formula.replace(" ", "");
		for(int s=0;s<pTable.length;s++){
			if (formula.equals(pTable[s].getSymbol()))
			{
				returnDouble= pTable[s].getMass();
			}
		}
		if (returnDouble!=0.0)
		{
			return returnDouble;
		}
		else
		{
			char[] formulaChars=formula.toCharArray();
			int[] formulaCharsInt=new int[formulaChars.length];
			for (int counter=0;counter<formulaChars.length;counter++)
			{
				formulaCharsInt[counter]=formulaChars[counter];
			}
			ArrayList <String> groupings = new ArrayList<String>();
			ArrayList <Integer> subscripts = new ArrayList<Integer>();
			int counter=1;
			String grouping="";
			String subscript="";
			while (counter<=formula.length())
			{
				grouping="";
				subscript="";
				if (isBracket(formulaCharsInt[counter-1]))
				{
					while ((formula.length()==counter)==false&&isBracket(formulaCharsInt[counter])==false)
					{
						grouping=grouping+formulaChars[counter];
						counter++;
					}
					groupings.add(grouping);
					counter++;
					if((formula.length()==counter)==false&&isInteger(formulaChars[counter])==true)
					{
						while ((formula.length()==counter)==false&&isInteger(formulaChars[counter])==true)
						{
							subscript+=formulaChars[counter];
							counter++;
						}
						subscripts.add(Integer.parseInt(subscript));
					}
					else
					{
						subscripts.add(1);
					}
				}
				else if (isUpper(formulaCharsInt[counter-1]))
				{

					grouping=Character.toString(formulaChars[counter-1]);
					while ( (formula.length()==counter)==false&&isLower(formulaCharsInt[counter])==true )
					{
						grouping=grouping+formulaChars[counter];
						counter++;
					}
					groupings.add(grouping);
					if((formula.length()==counter)==false&&isInteger(formulaChars[counter])==true)
					{
						while ((formula.length()==counter)==false&&isInteger(formulaChars[counter])==true)
						{
							subscript+=formulaChars[counter];
							counter++;
						}
						subscripts.add(Integer.parseInt(subscript));
					}
					else
					{
						subscripts.add(1);
					}
				}
				counter++;
			}
			for (int i=0;i<groupings.size();i++)
			{
				returnDouble=returnDouble+getMolarMass(groupings.get(i),pTable)*subscripts.get(i);
			}
			return returnDouble;
		}
	}




}
