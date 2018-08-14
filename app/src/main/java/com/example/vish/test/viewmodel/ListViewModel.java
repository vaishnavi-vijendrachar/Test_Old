package com.example.vish.test.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.vish.test.service.Database;
import com.example.vish.test.service.model.DataModel;
import com.example.vish.test.service.repository.service.RetrofitRepository;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by admin on 25/7/2018.
 */
@SuppressWarnings("unchecked")
public class ListViewModel extends AndroidViewModel {
  private MutableLiveData<List<DataModel>> listDataObservable = new MutableLiveData<>();
  static Database db ;
    public ListViewModel(@NonNull Application application) {
        super(application);
        //TODO - check the casting here
        this.listDataObservable = (MutableLiveData<List<DataModel>>) new RetrofitRepository().getDataList();
        db = Database.getDatabase(application.getApplicationContext());

    }

    public LiveData<List<DataModel>> getListDataObservable(){
      return listDataObservable;
  }

  public void addDataToDb(DataModel dataModel){
     db.listDao().addDataModel(dataModel);
    }

    public List<DataModel> checkDataExistance() {
        List<DataModel> dm = (List<DataModel>) db.listDao().getDataList();
        return dm;
    }
}
