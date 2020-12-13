package com.example.basicnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.UUID;

public class NoteActionActivity extends AppCompatActivity{

    private String noteID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteaction);

        String actionCheck = getIntent().getStringExtra("actionGet");


        EditText etTitle = findViewById(R.id.TitleCreateNote);
        EditText etNote = findViewById(R.id.NoteCreateNote);

        if(actionCheck.equals("Edit")) {
            etTitle.setText(getIntent().getStringExtra("edit_title"));
            etNote.setText(getIntent().getStringExtra("edit_text"));
        }

        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                if (TextUtils.isEmpty(etTitle.getText())){
                    Toast.makeText(getApplicationContext(),"Title can't be empty", Toast.LENGTH_LONG). show();
                }
                else{

                    String noteTitle = etTitle.getText().toString();
                    String noteText = etNote.getText().toString();

                    if (actionCheck.equals("Edit")) {
                        noteID = getIntent().getStringExtra("edit_id");
                        intent.putExtra("actionSend", "update");
                    }
                    else if (actionCheck.equals("AddNew")) {
                        noteID = UUID.randomUUID().toString();
                        intent.putExtra("actionSend", "add");
                    }

                    intent.putExtra("note_id", noteID);
                    intent.putExtra("note_title", noteTitle);
                    intent.putExtra("note_text", noteText);
                    setResult(Activity.RESULT_OK, intent);

                    finish();
                }
            }
        });

        FloatingActionButton deleteButton = (FloatingActionButton)findViewById(R.id.DeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoteActionActivity.this);
                    builder.setMessage("Do you want to DELETE this note?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent();

                                    if (actionCheck.equals("Edit")) {
                                        noteID = getIntent().getStringExtra("edit_id");
                                        intent.putExtra("note_id", noteID);
                                        intent.putExtra("actionSend", "delete");
                                        setResult(Activity.RESULT_OK, intent);
                                    }
                                    else {
                                        intent.putExtra("actionSend", "cancel");
                                        setResult(Activity.RESULT_OK, intent);
                                    }
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) { }
                            });
                    builder.show();
            }
        });
    }
}
