package com.example.basicnotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecycleViewAdapter.OnNoteListener{
    public static final int ADD_ACTIVITY_RESULT_CODE =1;
    private NoteViewModel noteViewModel;
    private ArrayList<Note> dataset = new ArrayList<>();

    private RecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addButton = findViewById(R.id.AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NoteActionActivity.class);
                intent.putExtra("actionGet", "AddNew");
                startActivityForResult(intent, ADD_ACTIVITY_RESULT_CODE);
            }
        });
        noteViewModel = new ViewModelProvider(MainActivity.this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);

        //dataset = (ArrayList<Note>) noteViewModel.retrieve();
        noteViewModel.retrieve().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if(dataset.size() > 0){
                    dataset.clear();
                }
                if(notes != null){
                    dataset.addAll(notes);
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecycleViewAdapter(dataset, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, NoteActionActivity.class);
        intent.putExtra("actionGet", "Edit");
        intent.putExtra("edit_id", dataset.get(position).id);
        intent.putExtra("edit_title", dataset.get(position).title);
        intent.putExtra("edit_text", dataset.get(position).text);
        startActivityForResult(intent, ADD_ACTIVITY_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_ACTIVITY_RESULT_CODE && resultCode == Activity.RESULT_OK && data.getStringExtra("actionSend").equals("add")){
            String noteID = data.getStringExtra("note_id");
            String noteTitle = data.getStringExtra("note_title");
            String noteText = data.getStringExtra("note_text");

            Note note = new Note(noteID, noteTitle, noteText);
            noteViewModel.insert(note);
            dataset.add(note);

            Toast.makeText(getApplicationContext(),"Note created", Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }
        else if(requestCode == ADD_ACTIVITY_RESULT_CODE && resultCode == Activity.RESULT_OK && data.getStringExtra("actionSend").equals("update")){
            String noteID = data.getStringExtra("note_id");
            String noteTitle = data.getStringExtra("note_title");
            String noteText = data.getStringExtra("note_text");

            Note note = new Note();
            for(int i = 0; i < dataset.size(); i++) {
                if(dataset.get(i).id.equals(noteID)) {
                    note.id = noteID;
                    note.title = noteTitle;
                    note.text = noteText;
                    dataset.get(i).title = noteTitle;
                    dataset.get(i).text = noteText;
                    break;
                }
            }

            if(note.id.equals(noteID)) {
                noteViewModel.update(note);
            }
            Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }
        else if(requestCode == ADD_ACTIVITY_RESULT_CODE && resultCode == Activity.RESULT_OK && data.getStringExtra("actionSend").equals("delete")){
            String noteID = data.getStringExtra("note_id");

            Note note = new Note();
            for(int i = 0; i < dataset.size(); i++) {
                if(dataset.get(i).id.equals(noteID)) {
                    note = dataset.get(i);
                    dataset.remove(i);
                    break;
                }
            }

            if(note.id.equals(noteID)) {
                noteViewModel.delete(note);
            }

            Toast.makeText(getApplicationContext(),"Deleted", Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }
        else if(requestCode == ADD_ACTIVITY_RESULT_CODE && resultCode == Activity.RESULT_OK && data.getStringExtra("actionSend").equals("cancel")){
            Toast.makeText(getApplicationContext(),"Note creation cancelled", Toast.LENGTH_LONG).show();
        }
    }
}