package com.MattiaBottini.rottenfridge;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FourthFragment extends Fragment {

    TextView next, back;
    ViewPager viewPager;

    public FourthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.slideFourthNext);
        back = view.findViewById(R.id.slideFourthBack);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(4);
                getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.navBar));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
                getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.navBar));
            }
        });
        return view;
    }
}