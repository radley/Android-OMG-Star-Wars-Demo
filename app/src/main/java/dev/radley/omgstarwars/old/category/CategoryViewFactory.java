package dev.radley.omgstarwars.old.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CategoryViewFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;
    private final String id;

    public CategoryViewFactory(@NonNull Application application, String id) {
        this.application = application;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == CategoryViewModel.class) {
            return (T) new CategoryViewModel(application, id);
        }
        return null;
    }
}