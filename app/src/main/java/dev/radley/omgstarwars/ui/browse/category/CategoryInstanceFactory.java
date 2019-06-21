package dev.radley.omgstarwars.ui.browse.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CategoryInstanceFactory extends ViewModelProvider.NewInstanceFactory {

    public CategoryInstanceFactory(@NonNull Application application, String id) {

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == CategoryViewModel.class) {
            return (T) new CategoryViewModel();
        }
        return null;
    }
}