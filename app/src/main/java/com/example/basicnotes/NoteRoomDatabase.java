package com.example.basicnotes;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
abstract class NoteRoomDatabase extends RoomDatabase{
    public abstract NoteDao noteDao();
}
