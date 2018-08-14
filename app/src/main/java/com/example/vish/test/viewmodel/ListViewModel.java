package com.example.vish.test.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import android.content.Context;
import android.support.annotation.NonNull;


import com.example.vish.test.service.Database;
import com.example.vish.test.service.model.DataModel;
import com.example.vish.test.service.repository.service.RetrofitRepository;

import java.util.List;


/**
 * Created by admin on 25/7/2018.
 */
@SuppressWarnings("unchecked")
public class ListViewModel extends AndroidViewModel {
  private MutableLiveData<List<DataModel>> listDataObservable;
  static Database db ;
  Context context;
    public ListViewModel(@NonNull Application application) {
        super(application);
        this.listDataObservable = new MutableLiveData<>();
        context = application.getApplicationContext();
        db = Database.getDatabase(application.getApplicationContext());
    }

    public LiveData<List<DataModel>> getListDataObservable(){
        listDataObservable = (MutableLiveData<List<DataModel>>) new RetrofitRepository(context).getDataList();
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
