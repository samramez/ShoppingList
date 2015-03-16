package com.example.samramez.shoppinglist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    ListView listView;
    FloatingActionButton fab;

    MyDialogFragment myFragment;

    ArrayList<String> listNames;

    DBTools dbTools = new DBTools(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(android.R.id.list);

        // Getting List names from the database
        listNames = dbTools.getAllListNames();

        //
        ListAdapter theAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,
                arrayListToString(listNames));

        listView.setAdapter(theAdapter);

        // Initiating the Floating ActionBar
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listView);

        fab.attachToListView(listView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                Log.d("ListViewFragment", "onScrollDown()");
            }

            @Override
            public void onScrollUp() {
                Log.d("ListViewFragment", "onScrollUp()");
            }
        }, new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d("ListViewFragment", "onScrollStateChanged()");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("ListViewFragment", "onScroll()");
            }
        });

        fab.setClickable(true);
        fab.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                // When the button is clicked
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    // Open Alert Dialog box so user enter the list name
                    //openDialog();

                    myFragment = new MyDialogFragment();

                    myFragment.show(getFragmentManager(), "theDialog");



                    return true;
                }

                return true;
            }
        });


    }// onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String[] arrayListToString(ArrayList<String> listNames){

        String[] array = new String[listNames.size()];

        for(int i =0 ; i < listNames.size() ; i++){
           array[i] = listNames.get(i).substring(10, listNames.get(i).length()-1 );
        }

        return array;

    }

}
