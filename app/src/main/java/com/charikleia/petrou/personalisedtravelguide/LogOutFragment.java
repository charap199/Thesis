package com.charikleia.petrou.personalisedtravelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class LogOutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_logout, container,false);

//        FirebaseAuth.getInstance().signOut();

//        Intent i = new Intent(getActivity(), DetailActivity.class);
//        startActivity(i);
//        ((Activity) getActivity()).overridePendingTransition(0, 0);

//        Intent intent = new Intent(LogOutFragment.this, MainActivity.class);
//        intent.putExtra("","");
//        startActivity(intent);
    }

}
