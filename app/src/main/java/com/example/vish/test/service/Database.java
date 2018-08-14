package com.example.vish.test.service;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.vish.test.service.model.DataModel;


@android.arch.persistence.room.Database(
        entities = {DataModel.class},
        version = 1,
        exportSchema = false
)

public abstract class Database extends RoomDatabase{
    private static Database INSTANCE;

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), Database.class, "test_db")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public abstract ListDao listDao();
}
