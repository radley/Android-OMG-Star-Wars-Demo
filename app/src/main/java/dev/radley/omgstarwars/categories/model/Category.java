package dev.radley.omgstarwars.categories.model;

import dev.radley.omgstarwars.categories.fragments.BaseCategoryFragment;

public class Category {

    protected String mId;
    protected String mName;
    protected BaseCategoryFragment mCategoryFragment;

    public Category(String id, String name, BaseCategoryFragment fragment) {
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

    public BaseCategoryFragment getCategoryFragment() {
        return mCategoryFragment;
    }

}
