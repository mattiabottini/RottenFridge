package com.MattiaBottini.rottenfridge;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ThirdFragment extends Fragment {

    TextView next, back;
    ViewPager viewPager;

    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.slideThreeNext);
        back = view.findViewById(R.id.slideThreeBack);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
                getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.navBar));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.navBar));
            }
        });
        return view;
    }
}