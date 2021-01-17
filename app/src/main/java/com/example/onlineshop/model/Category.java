package com.example.onlineshop.model;

public class Category implements Comparable {
    private int mId;
    private String mName;

    public Category(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int compareTo(Object o) {
        Category category = (Category) o;
        if (category.getId() == this.mId && category.getName().equals(this.mName)) {
            return 0;
        }
        return 1;
    }
}
