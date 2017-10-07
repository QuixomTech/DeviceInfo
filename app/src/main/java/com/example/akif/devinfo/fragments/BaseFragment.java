package com.example.akif.devinfo.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.akif.devinfo.MainActivity;
import com.example.akif.devinfo.utilities.FragmentUtil;
import com.example.akif.devinfo.utilities.Methods;

public class BaseFragment extends Fragment {

    public MainActivity mActivity;
    public FragmentUtil fragmentUtil;
    Resources mResources;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (MainActivity) getActivity();
        fragmentUtil = new FragmentUtil(mActivity);
        mResources = mActivity.getResources();

        new Methods(mActivity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) getActivity();
    }


}
