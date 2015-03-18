package com.example.samramez.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class List extends ActionBarActivity {



    private Button addButton;
    private Button saveButton;
    public EditText addItemEditText;
    private ViewGroup mContainerView;

    // Name of the this list
    static String listName;

    // ArrayList for storing items and then adding to database later
    static HashMap<String,String> queryItemMap = new HashMap<String, String>();

    DBTools dbtools = new DBTools(this);

    // if true, Activity will make a new list
    boolean isNewList;

    private static final String TAG = "ListActivity";


    // For Test purposes
    TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Receiving the intent from MainActivity
        Intent listIntent = this.getIntent();
        listName = listIntent.getStringExtra(Intent.EXTRA_TEXT);
        isNewList = listIntent.getExtras().getBoolean(Intent.ACTION_ANSWER);
        Log.v(TAG, "Is this a new list? : " + isNewList);

        // If this is an old list
        if( isNewList == false){
            loadList(listName);
        }

        // For Test purposes
        testTextView = (TextView) findViewById(R.id.testTextView);
        testTextView.setText(listName);

        //initiating page elements
        addButton = (Button) findViewById(R.id.addButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        addItemEditText = (EditText) findViewById(R.id.addItemEditText);
        mContainerView = (ViewGroup) findViewById(R.id.container);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(android.R.id.empty).setVisibility(View.GONE);
                addItem();
            }
        });
    }

    private void loadList(String listName) {

        ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
        listItems = dbtools.getAllItems(listName);


    }

    // Inflate item cell to the List activity.
    private void addItem() {

        // Show the Save Button
        saveButton.setVisibility(View.VISIBLE);

        // Instantiate a new "row" view.
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.list_item_inflate, mContainerView, false);

        String item;
        item = addItemEditText.getText().toString();
        ((TextView) newView.findViewById(android.R.id.text1)).setText(item);

        // Add to ItemList HashMap and later add the whole list
        queryItemMap.put("item",item);

        // Set a click listener for the "X" button in the row that will remove the row.
        newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the row from its parent (the container view).
                // Because mContainerView has android:animateLayoutChanges set to true,
                // this removal is automatically animated.
                mContainerView.removeView(newView);

                // Remove item from HashMap
                String deletedItem = ((TextView) newView.findViewById(android.R.id.text1))
                        .getText().toString();
                queryItemMap.remove(deletedItem);

                // If there are no rows remaining, show the empty view.
                if (mContainerView.getChildCount() == 0) {
                    findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Because mContainerView has android:animateLayoutChanges set to true,
        // adding this view is automatically animated.
        mContainerView.addView(newView, 0);

        //clear the EditText for the next thing
        addItemEditText.setText("");
    }


    /**
     * A static list of item names.
     */
    private static final String[] ITEMS = new String[]{
            "Apple", "Orange", "Juice", "Meat", "Water",
            "Cheese", "Eggs", "Ice Cream", "Butter", "Bread",
            "Nutella",
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Clear EditText when it is clicked
    public void clearEditText(View view) {
        addItemEditText.setText("");
    }

    // Saves the queryHashMap in the SQL table and go back to main page
    public void saveList(View view) {

        dbtools.insertItem(queryItemMap, listName);

        // Go to main page
        Intent intent = new Intent(getApplication(), MainActivity.class );
        startActivity(intent);

    }
}
