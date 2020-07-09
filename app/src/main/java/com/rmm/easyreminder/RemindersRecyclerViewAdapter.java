package com.rmm.easyreminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RemindersRecyclerViewAdapter
        extends RecyclerView.Adapter<RemindersRecyclerViewAdapter.RemindersRecyclerViewViewHolder>
{
    private ArrayList<Reminder> mReminders;

    public RemindersRecyclerViewAdapter (ArrayList<Reminder> reminders)
    {
        mReminders = reminders;
    }

    public static class RemindersRecyclerViewViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_note;
        ImageView iv_notification;

        public RemindersRecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_note = itemView.findViewById(R.id.item_tv_note);
            iv_notification = itemView.findViewById(R.id.item_iv_notification);
        }
    }

    @NonNull
    @Override
    public RemindersRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from( viewGroup.getContext() ).inflate(R.layout.item_reminder, viewGroup, false);
        return new RemindersRecyclerViewViewHolder (v);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindersRecyclerViewViewHolder remindersRecyclerViewViewHolder, int i) {
        // Fill the views with the data
        remindersRecyclerViewViewHolder.tv_note.setText( mReminders.get(i).getNote() );;
    }

    @Override
    public int getItemCount() {
        return mReminders.size();
    }
}

