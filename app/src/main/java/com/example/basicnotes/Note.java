package com.example.basicnotes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    public String title;

    public String text;

    public Note(String id,String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    @Ignore
    public Note() {}
}
