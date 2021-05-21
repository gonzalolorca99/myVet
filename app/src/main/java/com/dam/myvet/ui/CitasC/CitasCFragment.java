package com.dam.myvet.ui.CitasC;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dam.myvet.R;

public class CitasCFragment extends Fragment {

    private CitasCViewModel CitasCViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CitasCViewModel =
                new ViewModelProvider(this).get(CitasCViewModel.class);

        View root = inflater.inflate(R.layout.fragment_citasc, container, false);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}