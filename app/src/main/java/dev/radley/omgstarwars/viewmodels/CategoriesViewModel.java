package dev.radley.omgstarwars.viewmodels;

import androidx.lifecycle.ViewModel;

/**
 * ViewModel class for CategoriesActivity
 */
public class CategoriesViewModel extends ViewModel {

    private static String[] categoryIds;
    private static String[] categoryTitles;
    private String category;

    /**
     * Instantiate category ids, titles, and default category
     *
     * @param ids    String[]
     * @param titles String[]
     */
    public void init(String[] ids, String[] titles) {

        categoryIds = ids;
        categoryTitles = titles;
        category = categoryIds[0];

    }

    public String getCategory() {
        return category;
    }

    public int getCategoryCount() {
        return categoryIds.length;
    }

    public void setCategory(int position) {
        category = categoryIds[position];
    }

    public String getCategory(int position) {
        return categoryIds[position];
    }

    public String getCategoryTitle(int position) {
        return categoryTitles[position];
    }
}
