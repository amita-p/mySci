package mySci.mySciApps.mySciAPP;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.GridLayout.LayoutParams;

import com.example.androidhive.R;

import java.util.ArrayList;

/**
 * Created by Amita on 2015-06-29.
 */
public class StoichCalculations extends Activity  {

    Button newButton;
    EditText editReactants;
    EditText editProducts;
    Button enterAmountsButton;
    Button calculateButton;
    ScrollView amountsScroll;
    RelativeLayout amountsScrollLayout;
    Context coolContext;
    TextView[] textViews;
    EditText[] editTexts;
    Switch[] switches;
    Drawable drawButton;
    double[] amounts;
    String[] reactants;
    double[] coefficients;
    double[] products;
    String[] productsString;
    double[] productsCoefficients;
    TextView limitingReactantsTextView;
    TextView invalidTextView;
    private ArrayList <Element> myList;
    Element [] pTable; //array of all the elements;
    boolean valid=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.stoich_layout);
        editReactants=(EditText) findViewById(R.id.editReactants);
        editProducts=(EditText) findViewById(R.id.editProducts);
        invalidTextView=(TextView)findViewById(R.id.invalidTextView);
        invalidTextView.setVisibility(View.INVISIBLE);
        enterAmountsButton=(Button)findViewById(R.id.enterAmountsButton);
        amountsScroll=(ScrollView)findViewById(R.id.scrollView);
        amountsScrollLayout=(RelativeLayout)findViewById(R.id.relativeLayoutStoich);
        calculateButton=(Button)findViewById(R.id.goButton);
        coolContext=this;
        amountsScrollLayout.removeView(calculateButton);
        myList = getIntent().getParcelableArrayListExtra ("mylist");
        pTable= new Element[118];
        for (int i = 0; i < myList.size (); i++) //filling in the array of all elements
        {
            Element a = myList.get (i);
            pTable[i]=a;
        }
        click();
        click2();
    }
    public void click2(){
        calculateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                valid=true;
                invalidTextView.setVisibility(View.INVISIBLE);
                amounts=new double[reactants.length];
                if (limitingReactantsTextView!=null)
                {
                    amountsScrollLayout.removeView(limitingReactantsTextView);
                }
                ArrayList<String> limitingReactants=new ArrayList<String>();
                products=new double[reactants.length];
                for (int counter=0;counter<reactants.length;counter++)
                {
                    if(isNumeric(editTexts[counter].getText().toString()))
                        if (switches[counter].isChecked()==false) {
                            amounts[counter] = Double.parseDouble(editTexts[counter].getText().toString());
                        }
                        else{
                            double mass=Double.parseDouble(editTexts[counter].getText().toString());
                            amounts[counter]=mass/MolarMass.getMolarMass(reactants[counter],pTable);
                        }
                    else
                    {
                        valid=false;
                    }
                }
                if (valid==true) {
                    for (int counter = 0; counter < reactants.length; counter++) {
                        double[] theoreticalCoefficients = new double[reactants.length];
                        theoreticalCoefficients[counter] = amounts[counter];
                        for (int counter2 = 0; counter2 < reactants.length; counter2++) {
                            if (counter != counter2) {
                                theoreticalCoefficients[counter2] = (coefficients[counter2] / coefficients[counter]) * amounts[counter];
                            }
                        }
                        double product = 0;
                        for (int counter2 = 0; counter2 < theoreticalCoefficients.length; counter2++) {
                            product = product + theoreticalCoefficients[counter2];
                        }
                        products[counter] = product;
                    }

                    double limitingAmount = amounts[indexWithMin(products)];
                    double[] theoreticalAmounts = new double[reactants.length];
                    theoreticalAmounts[indexWithMin(products)] = limitingAmount;
                    for (int counter2 = 0; counter2 < reactants.length; counter2++) {
                        if (indexWithMin(products) != counter2) {
                            theoreticalAmounts[counter2] = (coefficients[counter2] / coefficients[indexWithMin(products)]) * amounts[indexWithMin(products)];
                        }
                    }
                    double amountOfLastLimitingReactant=0;
                    double coefficientOfLastLimitingReactant=0;
                    for (int counter = 0; counter < amounts.length; counter++) {
                        if (theoreticalAmounts[counter] == amounts[counter]) {
                            limitingReactants.add(reactants[counter]);
                            amountOfLastLimitingReactant=amounts[counter];
                            coefficientOfLastLimitingReactant=coefficients[counter];
                        }
                    }
                    limitingReactantsTextView = new TextView(coolContext);
                    limitingReactantsTextView.setText("Limiting Reactant(s):");
                    for (int counter = 0; counter < limitingReactants.size(); counter++) {
                        limitingReactantsTextView.setText(limitingReactantsTextView.getText() + "\n" + limitingReactants.get(counter));
                    }
                    limitingReactantsTextView.setText(limitingReactantsTextView.getText()+"\n"+"Amount of Product Formed:");
                    for (int counter=0;counter<productsString.length;counter++)
                    {
                        double productMol=(productsCoefficients[counter]/coefficientOfLastLimitingReactant)*amountOfLastLimitingReactant;
                        double productMass=productMol*MolarMass.getMolarMass(productsString[counter], pTable);
                        limitingReactantsTextView.setText(limitingReactantsTextView.getText() + "\n"+productsString[counter]+": "+Double.toString(productMol)+"mol/"+Double.toString(productMass)+"g");
                    }
                    RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params5.setMargins(0, 90 * (reactants.length - 1) + 250, 0, 0);
                    limitingReactantsTextView.setHorizontallyScrolling(true);
                    limitingReactantsTextView.setMovementMethod(new ScrollingMovementMethod());
                    limitingReactantsTextView.setTextSize(20);
                    amountsScrollLayout.addView(limitingReactantsTextView, params5);
                }
            }
        });
    }public static double eval(final String str) {
        class Parser {
            int pos = -1, c;

            void eatChar() {
                c = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            void eatSpace() {
                while (Character.isWhitespace(c)) eatChar();
            }

            double parse() {
                eatChar();
                double v = parseExpression();
                if (c != -1) throw new RuntimeException("Unexpected: " + (char)c);
                return v;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor | term brackets
            // factor = brackets | number | factor `^` factor
            // brackets = `(` expression `)`

            double parseExpression() {
                double v = parseTerm();
                for (;;) {
                    eatSpace();
                    if (c == '+') { // addition
                        eatChar();
                        v += parseTerm();
                    } else if (c == '-') { // subtraction
                        eatChar();
                        v -= parseTerm();
                    } else {
                        return v;
                    }
                }
            }

            double parseTerm() {
                double v = parseFactor();
                for (;;) {
                    eatSpace();
                    if (c == '/') { // division
                        eatChar();
                        v /= parseFactor();
                    } else if (c == '*' || c == '(') { // multiplication
                        if (c == '*') eatChar();
                        v *= parseFactor();
                    } else {
                        return v;
                    }
                }
            }

            double parseFactor() {
                double v;
                boolean negate = false;
                eatSpace();
                if (c == '+' || c == '-') { // unary plus & minus
                    negate = c == '-';
                    eatChar();
                    eatSpace();
                }
                if (c == '(') { // brackets
                    eatChar();
                    v = parseExpression();
                    if (c == ')') eatChar();
                } else { // numbers
                    StringBuilder sb = new StringBuilder();
                    while ((c >= '0' && c <= '9') || c == '.') {
                        sb.append((char)c);
                        eatChar();
                    }
                    if (sb.length() == 0) throw new RuntimeException("Unexpected: " + (char)c);
                    v = Double.parseDouble(sb.toString());
                }
                eatSpace();
                if (c == '^') { // exponentiation
                    eatChar();
                    v = Math.pow(v, parseFactor());
                }
                if (negate) v = -v; // unary minus is applied after exponentiation; e.g. -3^2=-9
                return v;
            }
        }
        return new Parser().parse();
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
    public void click(){
        enterAmountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valid=true;
                invalidTextView.setVisibility(View.INVISIBLE);
                String balancedEquation = editReactants.getText().toString() + "-->" + editProducts.getText().toString();
                balancedEquation=balancedEquation.replace(" ", "");
                reactants = (balancedEquation.substring(0, balancedEquation.indexOf("-"))).split("\\+");
                coefficients = new double[reactants.length];
                String productss=editProducts.getText().toString().replace(" ","");
                productsString=productss.split("\\+");
                productsCoefficients=new double[productsString.length];
                if (editReactants.getText().toString().equals("")||editProducts.getText().toString().equals(""))
                {
                    valid=false;
                    invalidTextView.setVisibility(View.VISIBLE);
                }
                if (valid==true) {
                    for (int counter = 0; counter < coefficients.length; counter++) {
                        String coefficient = "";
                        int counter2 = 0;
                        while (Character.isLetter((reactants[counter].charAt(counter2)))==false && isBracket((reactants[counter].charAt(counter2)))==false) {
                            coefficient += reactants[counter].charAt(counter2);
                            counter2++;
                        }
                        if (coefficient.equals("")) {
                            coefficients[counter] = 1;
                        } else {
                            if (isNumeric(coefficient)==false)
                            {
                                valid=false;
                                invalidTextView.setVisibility(View.VISIBLE);
                            }
                            else{
                                coefficients[counter] = (eval(coefficient));
                            }

                        }
                        reactants[counter] = reactants[counter].substring(counter2, reactants[counter].length());
                    }
                    for (int counter = 0; counter < productsCoefficients.length; counter++) {
                        String coefficient = "";
                        int counter2 = 0;
                        while (Character.isLetter((productsString[counter].charAt(counter2)))==false && isBracket((productsString[counter].charAt(counter2)))==false) {
                            coefficient += productsString[counter].charAt(counter2);
                            counter2++;
                        }
                        if (coefficient.equals("")) {
                            productsCoefficients[counter] = 1;
                        } else {
                            if (isNumeric(coefficient)==false)
                            {
                                valid=false;
                                invalidTextView.setVisibility(View.VISIBLE);
                            }
                            else{
                                productsCoefficients[counter] = (eval(coefficient));
                            }

                        }
                        productsString[counter] = productsString[counter].substring(counter2, productsString[counter].length());
                    }
                }
                for(int counter=0;counter<reactants.length;counter++)
                {

                    try
                    {
                        double molarMass=MolarMass.getMolarMass(reactants[counter],pTable);
                        if (molarMass==0)
                        {
                            valid=false;
                            invalidTextView.setVisibility(View.VISIBLE);
                        }

                    }
                    catch (Exception ex)
                    {
                        valid=false;
                        invalidTextView.setVisibility(View.VISIBLE);
                    }
                    catch (StackOverflowError ex)
                    {
                        valid=false;
                        invalidTextView.setVisibility(View.VISIBLE);
                    }

                }
                for(int counter=0;counter<productsString.length;counter++)
                {

                    try
                    {
                        double molarMass=MolarMass.getMolarMass(productsString[counter],pTable);
                        if (molarMass==0)
                        {
                            valid=false;
                            invalidTextView.setVisibility(View.VISIBLE);
                        }

                    }
                    catch (Exception ex)
                    {
                        valid=false;
                        invalidTextView.setVisibility(View.VISIBLE);
                    }
                    catch (StackOverflowError ex)
                    {
                        valid=false;
                        invalidTextView.setVisibility(View.VISIBLE);
                    }

                }
                if (textViews!=null) {
                    for (int counter = 0; counter < textViews.length; counter++) {
                        amountsScrollLayout.removeView(textViews[counter]);
                        amountsScrollLayout.removeView(editTexts[counter]);
                        amountsScrollLayout.removeView(switches[counter]);
                    }
                }
                if (calculateButton!=null)
                {
                    amountsScrollLayout.removeView(calculateButton);
                }
                if (limitingReactantsTextView!=null)
                {
                    amountsScrollLayout.removeView(limitingReactantsTextView);
                }
                textViews=new TextView[reactants.length];
                editTexts=new EditText[reactants.length];
                switches=new Switch[reactants.length];

                if (valid==true) {
                    for (int counter = 0; counter < reactants.length; counter++) {
                        TextView rowTextView = new TextView(coolContext);
                        EditText newEditText = new EditText(coolContext);
                        Switch newSwitch = new Switch(coolContext);
                        rowTextView.setTextSize(25);
                        newEditText.setTextSize(25);
                        newSwitch.setTextSize(20);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(200, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(200, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 90 * counter + 30, 0, 0);
                        params2.setMargins(250, 90 * counter, 0, 0);
                        params3.setMargins(500, 90 * counter + 30, 0, 0);
                        rowTextView.setSingleLine(true);
                        rowTextView.setHorizontallyScrolling(true);
                        rowTextView.setMovementMethod(new ScrollingMovementMethod());
                        rowTextView.setText(reactants[counter]);
                        newSwitch.setTextOff("mol");
                        newSwitch.setTextOn("g");
                        newEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


                        //substitute parameters for left, top, right, bottom


                        textViews[counter] = (rowTextView);
                        editTexts[counter] = newEditText;
                        switches[counter] = newSwitch;
                        amountsScrollLayout.addView(rowTextView, params);
                        amountsScrollLayout.addView(newEditText, params2);
                        amountsScrollLayout.addView(newSwitch, params3);
                        if (counter == reactants.length - 1)

                        {
                            //calculateButton=new Button(coolContext);
                            RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(200, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params4.setMargins(250, 90 * counter + 120, 0, 0);
                            calculateButton.setText("Go!");
                            amountsScrollLayout.addView(calculateButton, params4);
                        }
                    }
                }
            }
        });


}
    public static boolean isNumeric(char character)
    {
        String str=Character.toString(character);
        str=Double.toString(eval(str));
        try{
            double num = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isNumeric(String str)
    {

        try{
            str = Double.toString(eval(str));
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException ex){
            return false;
        }
        catch(RuntimeException ex){
            return false;
        }
    }
    public static int indexWithMin(double[] products)
    {
        double min=products[0];
        int index=0;
        for (int counter=0;counter<products.length;counter++)
        {
            if (min>products[counter])
            {
                index=counter;
            }
            min=Math.min(min, products[counter]);
        }
        return index;
    }


}
