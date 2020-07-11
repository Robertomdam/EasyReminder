package com.rmm.easyreminder;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RemindersRecyclerViewAdapter
        extends RecyclerView.Adapter<RemindersRecyclerViewAdapter.RemindersRecyclerViewViewHolder>
{
    private ArrayList<Reminder> mReminders;
    private ReminderAdapterEventListener mEventListener;

    private int mSelectedItem;

    public RemindersRecyclerViewAdapter (ArrayList<Reminder> reminders, ReminderAdapterEventListener eventListener)
    {
        mReminders = reminders;
        mEventListener = eventListener;
    }

    public int getSelectedItem () {
        return mSelectedItem;
    }

    public void clearSelectedItem () {
        this.mSelectedItem = -1;
    }

    public class RemindersRecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        TextView tv_note;
        ImageView iv_notification;

        public RemindersRecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_note = itemView.findViewById(R.id.item_tv_note);
            iv_notification = itemView.findViewById(R.id.item_iv_notification);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu (ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            mEventListener.onCreateContextMenuCustom (contextMenu, view, contextMenuInfo);
        }
    }

    @Override
    public void onViewRecycled (RemindersRecyclerViewViewHolder holder)
    {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @NonNull
    @Override
    public RemindersRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate(R.layout.item_reminder, viewGroup, false);
        mEventListener.onItemCreated (v);
        return new RemindersRecyclerViewViewHolder (v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RemindersRecyclerViewViewHolder remindersRecyclerViewViewHolder, int i) {
        // Fill the views with the data
        remindersRecyclerViewViewHolder.tv_note.setText( mReminders.get(i).getNote() );
        mEventListener.onItemDataFilled (i);

        final int position = i;

        remindersRecyclerViewViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mSelectedItem = position;
                return false;
            }
        });
    }

    @Override
    public int getItemCount() { return mReminders.size(); }
}

interface ReminderAdapterEventListener
{
    public void onCreateContextMenuCustom (ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo);
    public void onItemCreated (View v);
    public void onItemDataFilled (int i);
}
