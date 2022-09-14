package com.MattiaBottini.rottenfridge;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class FirstFragment extends Fragment {

    TextView next;
    ViewPager viewPager;
    Window window;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        viewPager=getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.slideOneNext);
        getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.lightYellow));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.navBar));
            }
        });
        return view;
    }
}