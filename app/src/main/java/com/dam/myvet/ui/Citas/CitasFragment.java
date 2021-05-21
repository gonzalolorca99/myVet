package com.dam.myvet.ui.Citas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dam.myvet.R;

public class CitasFragment extends Fragment {

    private CitasViewModel CitasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CitasViewModel =
                new ViewModelProvider(this).get(CitasViewModel.class);

        View root = inflater.inflate(R.layout.fragment_citas, container, false);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}