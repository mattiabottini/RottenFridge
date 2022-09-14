package com.MattiaBottini.rottenfridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class FifthFragment extends Fragment {

    TextView done, back;
    ViewPager viewPager;

    public FifthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);

        viewPager=getActivity().findViewById(R.id.viewPager);
        done = view.findViewById(R.id.slideFiveDone);
        back = view.findViewById(R.id.slideFiveBack);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.navBar));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
                getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.navBar));
            }
        });
        return view;
    }
}