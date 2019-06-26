package dev.radley.omgstarwars.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Factory for adding ViewModel to CategoryFragment
 */
public class CategoryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    public CategoryViewModelFactory(@NonNull Application application, String id) {

    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == CategoryViewModel.class) {
            return (T) new CategoryViewModel();
        }
        return null;
    }
}