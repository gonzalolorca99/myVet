package com.dam.myvet.ui.MascotasC;

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

public class MascotasCFragment extends Fragment {

    private MascotasCViewModel MascotasCViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MascotasCViewModel =
                new ViewModelProvider(this).get(MascotasCViewModel.class);

        View root = inflater.inflate(R.layout.fragment_inicioc, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        MascotasCViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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