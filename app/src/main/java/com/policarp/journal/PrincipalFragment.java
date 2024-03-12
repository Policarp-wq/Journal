package com.policarp.journal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.policarp.journal.databinding.FragmentPrincipalBinding;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PrincipalFragment extends FragmentDataSender {
    FragmentPrincipalBinding binding;
    FragmentManager fm;
    School school;
    private static final String SCHOOL = "school";
    OnDataSendListener listener;
    public static final Dictionary<String, Supplier> Actions = new Hashtable<String, Supplier>();

    private Object addClass() {

       // getParentFragmentManager().beginTransaction().add();
        return null;
    }
    private void fillDict(){
        Actions.put("Add class", () -> addClass());
    }

    public PrincipalFragment() {
    }
    public static PrincipalFragment newInstance(String school, OnDataSendListener listener) {
        PrincipalFragment fragment = new PrincipalFragment();
        Bundle args = new Bundle();
        args.putString(SCHOOL, school);
        fragment.setArguments(args);
        fragment.listener = listener;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            school = School.fromJson(getArguments().getString(SCHOOL));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPrincipalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}