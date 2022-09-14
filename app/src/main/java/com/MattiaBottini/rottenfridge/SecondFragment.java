package com.MattiaBottini.rottenfridge;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SecondFragment extends Fragment {

    TextView next, back;
    ViewPager viewPager;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        viewPager=getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.slideTwoNext);
        back = view.findViewById(R.id.slideTwoBack);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
                getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.lightYellow));
            }
        });
        return view;
    }
}