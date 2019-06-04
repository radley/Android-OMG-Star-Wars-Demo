package dev.radley.omgstarwars.model;

import dev.radley.omgstarwars.fragment.BaseCategoryFragment;

public class CategoryView {

    protected String mId;
    protected String mName;
    protected BaseCategoryFragment mFragment;
    public CategoryView(String id, String name, BaseCategoryFragment fragment) {

        mId = id;
        mName = name;
        mFragment = fragment;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public BaseCategoryFragment getFragment() {
        return mFragment;
    }
}
