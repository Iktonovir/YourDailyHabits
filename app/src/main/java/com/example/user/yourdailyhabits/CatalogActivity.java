package com.example.user.yourdailyhabits;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.user.yourdailyhabits.data.HabitContract.HabitEntry;
import com.example.user.yourdailyhabits.data.HabitDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new HabitDbHelper(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of the
     * database.
     */
    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        db = mDbHelper.getReadableDatabase();

        /*
         * Define a projection that specifies which columns from the database will actually be used
         * after this query.
         */
        String[] projection = new String[]{
                HabitEntry._ID,
                HabitEntry.COLUMN_ACTIVITY_NAME,
                HabitEntry.COLUMN_HAPPENING,
                HabitEntry.COLUMN_DURATION,
        };

        Cursor cursor = readDataBase(projection);

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The habits table contains <number of rows in Cursor> habits.
            // _id - activity - happening - duration
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");

            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_ACTIVITY_NAME + " - " +
                    HabitEntry.COLUMN_HAPPENING + " - " +
                    HabitEntry.COLUMN_DURATION + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int activityColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ACTIVITY_NAME);
            int whenColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HAPPENING);
            int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DURATION);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {

                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentActivity = cursor.getString(activityColumnIndex);
                String currentWhen = cursor.getString(whenColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentActivity + " - " +
                        currentWhen + " - " +
                        currentDuration));
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private Cursor readDataBase(String[] projection) {

        // query for cursor
        // Perform a query on the habits table
        return db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
    }

    private void insertHabit(){
        // Gets the database in write mode
        db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's habits attributes are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_ACTIVITY_NAME, "Tennis");
        values.put(HabitEntry.COLUMN_HAPPENING, HabitEntry.HAPPENING_MORNING);
        values.put(HabitEntry.COLUMN_DURATION, 60);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the habits table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;

            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
