package com.example.vish.test.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.vish.test.util.NetworkUtil;
import com.example.vish.test.R;
import com.example.vish.test.service.Database;
import com.example.vish.test.service.model.DataModel;
import com.example.vish.test.view.adapter.ListDetailsAdapter;
import com.example.vish.test.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private List<DataModel> list ;
    CoordinatorLayout coordinatorLayout;
    public static SwipeRefreshLayout mSwipeToRefresh;
    ListView listView;
    ListViewModel viewModel;
    static Boolean dataPresent= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        mSwipeToRefresh = findViewById(R.id.swipe_to_refresh);
        mSwipeToRefresh.setOnRefreshListener(this);
        mSwipeToRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //Initialise View Model in the Layout
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        //get data from server
        getDataFromRepository();

    }

    private void setTitle() {
        //set action bar title
        SharedPreferences pref = getSharedPreferences("TITLE_PREF",Context.MODE_PRIVATE);
        getSupportActionBar().setTitle(pref.getString("TITLE","title"));


    }

    private void getDataFromRepository() {
            if (NetworkUtil.checkNetworkState(this)) {
                //subscribe to LiveData

                viewModel.getListDataObservable().observe(this, new Observer<List<DataModel>>() {
                    @Override
                    public void onChanged(@Nullable List<DataModel> dataModels) {
                        //do something with the data - in this case set the adapter
                        if(dataModels != null) {
                            setTitle();
                            setListData(dataModels);
                            cacheData(dataModels, MainActivity.this);
                            dataPresent = true;
                        }
                    }
                });
            } else {
                //dipslay message
                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.network_unavailable, Snackbar.LENGTH_SHORT);
                snackbar.show();
                checkDataExistance();
            }
    }

    private void checkDataExistance() {
        //get data from room
            if(dataPresent) {
                list = new ArrayList<>();
                list = viewModel.checkDataExistance();
                setListData(list);
            }
    }

    private void cacheData(List<DataModel> dataModels, Context context) {
        //save stuff into db
        for(DataModel i : dataModels) {
            viewModel.addDataToDb(i);
        }

    }

    private void setListData(List<DataModel> listData) {
        list = listData;
        if(list != null){
            //setting data to list view
            ListDetailsAdapter adapter = new ListDetailsAdapter(this, list);
            listView.setAdapter(adapter);
        }

    }

    @Override
    public void onRefresh() {
        //swipe to refresh implementation
        mSwipeToRefresh.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if( mSwipeToRefresh.isRefreshing()) {
                                                mSwipeToRefresh.setRefreshing(false);
                                                getDataFromRepository();
                                            }
                                        }
                                    },2000
        );

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSwipeToRefresh.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle();
    }
}
