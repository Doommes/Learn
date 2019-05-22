package com.doommes.learn.ArchitectureComponents_14;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doommes.learn.R;

public class TestFrament extends Fragment implements View.OnClickListener {
    private View view;
    /**
     * start
     */
    private TextView mTest;

    private ShareDataViewModel mShareDataViewModel;
    private int mCount = 0;

    public static TestFrament newInstance() {

        Bundle args = new Bundle();

        TestFrament fragment = new TestFrament();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewmodel, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mShareDataViewModel = ViewModelProviders.of(getActivity()).get(ShareDataViewModel.class);
        mTest = (TextView) view.findViewById(R.id.test);
        mTest.setOnClickListener(this);

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                mTest.setText(String.valueOf(integer));
                mCount = integer;
            }
        };
        mShareDataViewModel.getMutableLiveData().observe(getActivity(), observer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.test:
                mShareDataViewModel.getMutableLiveData().postValue(++mCount);
                break;
        }
    }
}
