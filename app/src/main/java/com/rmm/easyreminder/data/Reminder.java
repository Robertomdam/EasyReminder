package com.rmm.easyreminder.data;

/**
 * @author Roberto
 * This class represents a reminder. It stores an id to identify it and a note.
 */
public class Reminder
{
    private int mId;
    private String mNote;

    /**
     * Creates a reminder with no assigned id. Id will be initialized as -1.
     * @param note
     */
    public Reminder (String note) {
        mId =     -1;
        mNote = note;
    }

    /**
     * Creates a reminder.
     * @param id
     * @param note
     */
    public Reminder (int id, String note) {
          mId =   id;
        mNote = note;
    }

    /**
     * Getter for the id attribute.
     * @return Attribute id.
     */
    public int getId() {
        return mId;
    }

    /**
     * Getter for the note attribute.
     * @return Attribute note.
     */
    public String getNote() {
        return mNote;
    }
}
