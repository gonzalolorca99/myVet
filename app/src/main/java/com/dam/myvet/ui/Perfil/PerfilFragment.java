package com.dam.myvet.ui.Perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dam.myvet.R;

public class PerfilFragment extends Fragment {

    private PerfilViewModel PerfilViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        final TextView textView = root.findViewById(R.id.text_perfil);
        PerfilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}