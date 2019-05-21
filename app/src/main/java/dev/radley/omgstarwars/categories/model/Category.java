package dev.radley.omgstarwars.categories.model;

import dev.radley.omgstarwars.categories.fragments.CategoryFragment;

public class Category {

    protected String mId;
    protected String mName;
    protected CategoryFragment mCategoryFragment;

    public Category(String id, String name, CategoryFragment fragment) {
        mId = id;
        mName = name;
        mCategoryFragment = fragment;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public CategoryFragment getCategoryFragment() {
        return mCategoryFragment;
    }

}
