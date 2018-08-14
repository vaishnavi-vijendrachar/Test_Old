package com.example.vish.test.service.model;

import com.example.vish.test.service.model.DataModel;

import java.util.List;

/**
 * Created by admin on 25/7/2018.
 */

public class Response {
    public List<DataModel> rows;
    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DataModel> getRows() {
        return rows;
    }

    public void setRows(List<DataModel> rows) {
        this.rows = rows;
    }
}
