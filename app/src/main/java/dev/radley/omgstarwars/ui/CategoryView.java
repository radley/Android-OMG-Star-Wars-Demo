package dev.radley.omgstarwars.ui;

import dev.radley.omgstarwars.ui.search.category.fragments.BaseCategoryFragment;

public class CategoryView {

    private String mId;
    private String mName;
    private BaseCategoryFragment mFragment;

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
