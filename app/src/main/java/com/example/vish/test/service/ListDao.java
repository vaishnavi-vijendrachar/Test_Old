package com.example.vish.test.service;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.vish.test.service.model.DataModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ListDao {

    //Insert datamodel
    @Insert(onConflict = REPLACE)
    void addDataModel(DataModel dataModel);

    //get all the data
    @Query("SELECT * FROM DataModel")
    List<DataModel> getDataList();

    @Query("SELECT count(*) FROM DataModel")
    int getDataCount();
}
