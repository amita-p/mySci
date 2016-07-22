package mySci.mySciApps.mySciAPP;

import com.example.androidhive.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class PeriodicTable extends Activity {

    Button button;
    GridView gridView;
    private ArrayList <Element> myList;
    Element [] pTable;
	@Override

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.periodic_table_layout);



        Intent cal = getIntent();
        myList = getIntent().getParcelableArrayListExtra ("mylist");
        pTable= new Element[118];
        for (int i = 0; i < myList.size (); i++)
        {
            Element a = myList.get (i);
            pTable[i]=a;
            //Log.d (TAG, "state:" + a.getName ());
        }





		 gridView = (GridView) findViewById(R.id.grid_view);
       		// Instance of ImageAdapter Class
		gridView.setAdapter(new ImageAdapter(this));
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
						
				if(position==0||position>=17&&position<=19||position>=30&&position<=37||
						position>=48&&position<=125||position>=146&&position<=159||position>=164&&position<=177){
				// Sending image id to FullScreenActivity
				Intent i = new Intent(getApplicationContext(), EleInfomation.class);
                    ArrayList<Element> myList2 = new ArrayList<Element>(0);
                    for(int s=0;s<pTable.length;s++){
                        myList2.add(pTable[s]);
                    }
				// passing array index
				i.putExtra("id", position);
                i.putParcelableArrayListExtra ("mylist", myList2);
				startActivity(i);
				}
			}
					
		});
	}






}