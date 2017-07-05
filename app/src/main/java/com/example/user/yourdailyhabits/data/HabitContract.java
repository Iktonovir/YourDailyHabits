package com.example.user.yourdailyhabits.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Habits app.
 */
public final class HabitContract {

        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
    private HabitContract() {}

        /**
         * Inner class that defines constant values for the Habits database table.
         * Each entry in the table represents a single habit.
         */
        public static final class HabitEntry implements BaseColumns {

            /**
             * Name of database table for Habits
             */
            public final static String TABLE_NAME = "habits";

            /**
             * Unique ID number for the habit (only for use in the database table).
             * <p>
             * Type: INTEGER
             */
            public final static String _ID = BaseColumns._ID;

            /**
             * Name of the habit.
             * <p>
             * Type: TEXT
             */
            public final static String COLUMN_ACTIVITY_NAME = "activity";

            /**
             * When the activity takes place.
             * <p>
             * The only possible values are {@link #HAPPENING_MORNING}, {@link #HAPPENING_AFTERNOON},
             * or {@link #HAPPENING_EVENING}.
             * <p>
             * Type: INTEGER
             */
            public final static String COLUMN_HAPPENING = "happening";

            /**
             * Duration of activity.
             * <p>
             * Type: INTEGER
             */
            public final static String COLUMN_DURATION = "duration";

            /**
             * Possible values for the WHEN of the activity.
             */
            public static final int HAPPENING_MORNING = 0;
            public static final int HAPPENING_AFTERNOON = 1;
            public static final int HAPPENING_EVENING = 2;
        }
    }
