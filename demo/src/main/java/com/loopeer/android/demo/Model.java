package com.loopeer.android.demo;

import java.util.List;

public class Model {
    String name;
    List<Model> data;

    public Model(String name) {
        this.name = name;
    }

    public Model(List<Model> data, String name) {
        this.data = data;
        this.name = name;
    }

    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
