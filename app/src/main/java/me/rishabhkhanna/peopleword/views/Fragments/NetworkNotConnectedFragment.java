package me.rishabhkhanna.peopleword.views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rishabhkhanna.peopleword.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetworkNotConnectedFragment extends Fragment {


    public NetworkNotConnectedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_network_not_connected, container, false);
        return view;
    }

}
