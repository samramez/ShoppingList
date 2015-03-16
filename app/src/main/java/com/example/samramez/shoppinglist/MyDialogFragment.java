package com.example.samramez.shoppinglist;

/**
 * Created by samramez on 3/14/15.
 */

// This will be used to create a dialog window

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// If you get an error that the minimum must be 11, change the minimum in the manifest
// and also change it in build.gradle

// To generate the onCreateDialog() right click on DialogFragment and Generate and
// select onCreateDialog()

public class MyDialogFragment extends DialogFragment{

    private EditText dialogEditText;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_cell, null);

        dialogEditText = (EditText) view.findViewById(R.id.dialogEditText);

        // We build the dialog
        // getActivity() returns the Activity this Fragment is associated with
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

        // Set the title for the Dialog
        theDialog.setTitle("Enter Your List Name");

        // Set the View as EditText
        theDialog.setView(view);

        // Set the message
        //theDialog.setMessage("Hello I'm a Dialog");

        // Add text for a positive button
        theDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                // Get text from EditText
                final String listName = dialogEditText.getText().toString();

                Intent intent = new Intent(getActivity(), List.class)
                        .putExtra(Intent.EXTRA_TEXT, listName);

                startActivity(intent);

            }
        });

        // Add text for a negative button
        theDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getActivity(), "Clicked Cancel", Toast.LENGTH_SHORT).show();

            }
        });

        // Returns the created dialog
        return theDialog.create();

    }

}
