package com.example.basicnotes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class NoteViewModel extends AndroidViewModel{

    private NoteDao noteDao;

    public NoteViewModel(@NonNull Application application) {
        super(application);


        NoteRoomDatabase db = Room.databaseBuilder(application,
                NoteRoomDatabase.class, "note-database").build();

        noteDao = db.noteDao();
    }

    public void insert(Note note){
        new InsertAsyncTask().execute(note);
    }

    private class InsertAsyncTask extends AsyncTask<Note,Void,Void> {
        @Override
        protected Void doInBackground(Note[] notes) {
            noteDao.insertNotes(notes[0]);
            return null;
        }
    }

    public void delete(Note note){
        new DeleteAsyncTask().execute(note);
    }

    private class DeleteAsyncTask extends AsyncTask<Note,Void,Void> {
        @Override
        protected Void doInBackground(Note[] notes) {
            noteDao.deleteNotes(notes[0]);
            return null;
        }
    }

    public void update(Note note){
        new UpdateAsyncTask().execute(note);
    }

    private class UpdateAsyncTask extends AsyncTask<Note,Void,Void> {
        @Override
        protected Void doInBackground(Note[] notes) {
            noteDao.updateNotes(notes[0]);
            return null;
        }
    }

    public LiveData<List<Note>> retrieve(){
        return noteDao.getNotes();
    }
}
