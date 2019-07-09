package dev.radley.omgstarwars.viewmodels;

import androidx.lifecycle.ViewModel;

import dev.radley.omgstarwars.models.Category;

/**
 * ViewModel for CategoriesActivityOld
 *
 * - provides category ids and titles for tabs and pages
 */
public class CategoriesViewModel extends ViewModel {

    private static String[] categoryIds = Category.categoryIds;
    private static String[] categoryTitles = Category.categoryTitles;
    private String category = categoryIds[0];

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
