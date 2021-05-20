package com.dam.myvet.ui.CerrarSesion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.dam.myvet.LoginActivity;
import com.dam.myvet.MenuClienteActivity;
import com.dam.myvet.R;
import com.google.firebase.auth.FirebaseAuth;

public class CerrarSesionFragment extends Fragment {

    private CerrarSesionViewModel CerrarSesionViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CerrarSesionViewModel =
                new ViewModelProvider(this).get(CerrarSesionViewModel.class);
        View root = inflater.inflate(R.layout.activity_login, container, false);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}