package com.example.basicnotes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{
    private ArrayList<Note> mDataset;
    private OnNoteListener mOnNoteListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnNoteListener mOnNoteListener;
        public TextView noteTitle;
        public TextView noteText;

        public ViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            mOnNoteListener = onNoteListener;
            noteTitle = itemView.findViewById(R.id.TitleRecyclerView);
            noteText = itemView.findViewById(R.id.NoteRecyclerView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    public RecycleViewAdapter(ArrayList<Note> myDataset, OnNoteListener onNoteListener){
        this.mDataset = myDataset;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewCell = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_card,parent,false);

        ViewHolder viewHolder = new ViewHolder(viewCell, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.noteTitle.setText(mDataset.get(position).title);
        holder.noteText.setText(mDataset.get(position).text);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

