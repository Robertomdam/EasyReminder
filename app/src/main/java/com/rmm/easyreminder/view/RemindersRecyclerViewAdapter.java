package com.rmm.easyreminder.view;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmm.easyreminder.R;
import com.rmm.easyreminder.data.Reminder;

import java.util.ArrayList;

/**
 * @author Roberto
 * Class that inherits from RecyclerViewAdapter, that will help managing the reminders visualization
 * in the activity.
 */
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

    /**
     * Retrieves the current selected item.
     * @return The current selected item.
     */
    public int getSelectedItem () {
        return mSelectedItem;
    }

    /**
     * Sets no item as currently selected.
     */
    public void clearSelectedItem () {
        this.mSelectedItem = -1;
    }

    /**
     * @author Roberto
     * Class that inherits from RecyclerViewViewHolder that helps the Adapter
     * to handle the data, views and layouts of its items.
     */
    public class RemindersRecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        TextView tv_note;
        ImageView iv_notification;

        /**
         * Sets some basic data and establishes listener to a context menu creation event.
         * @param itemView
         */
        public RemindersRecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_note = itemView.findViewById(R.id.item_tv_note);
            iv_notification = itemView.findViewById(R.id.item_iv_notification);

            itemView.setOnCreateContextMenuListener(this);
        }

        /**
         * Callback method that notifies to the interface that listen to events that the context menu has been created.
         * @param contextMenu
         * @param view
         * @param contextMenuInfo
         */
        @Override
        public void onCreateContextMenu (ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            mEventListener.onCreateContextMenuCustom (contextMenu, view, contextMenuInfo);
        }
    }

    /**
     * Setter for the list of reminders.
     * @param reminders The new reminders list.
     */
    void setReminders (ArrayList<Reminder> reminders) { mReminders = reminders; }

    /**
     * Removes the onLongClickListener from the itemView of the holder.
     * @param holder
     */
    @Override
    public void onViewRecycled (RemindersRecyclerViewViewHolder holder)
    {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    /**
     * Inflates the view holder that is being processes and notifies to the interface that listen to events.
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public RemindersRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate(R.layout.item_reminder, viewGroup, false);
        mEventListener.onItemCreated (v);
        return new RemindersRecyclerViewViewHolder (v);
    }

    /**
     * Fills the views of the view holder with the corresponding data and notifies to the interface that listen to events.
     * Also configures an onLongClickEvent to the view holder.
     * @param remindersRecyclerViewViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull final RemindersRecyclerViewViewHolder remindersRecyclerViewViewHolder, int i) {
        // Fill the views with the data
        remindersRecyclerViewViewHolder.tv_note.setText( mReminders.get(i).getNote() );
        mEventListener.onItemDataFilled (remindersRecyclerViewViewHolder.itemView, i);

        final int position = i;

        remindersRecyclerViewViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mSelectedItem = position;
                return false;
            }
        });
    }

    /**
     * Retrieves the size of the list of reminders.
     * @return The size of the list of reminders.
     */
    @Override
    public int getItemCount() { return mReminders.size(); }
}

/**
 * @author Roberto
 * Interface that lets external clases to receive events from the RemindersRecyclerViewAdapter.
 */
interface ReminderAdapterEventListener
{
    public void onCreateContextMenuCustom (ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo);
    public void onItemCreated (View v);
    public void onItemDataFilled (View v, int i);
}
