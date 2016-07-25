package com.vicloud.vn.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vicloud.vn.R;
import com.vicloud.vn.adapters.ServerAdapter;
import com.vicloud.vn.adapters.SheetAdapter;
import com.vicloud.vn.databases.CacheHelper;
import com.vicloud.vn.models.Server;
import com.vicloud.vn.models.SheetItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ServerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Server> mDataSet;
    private ServerAdapter serverAdapter;
    RecyclerView mRecyclerView;
    private CacheHelper cacheHelper;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ServerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServerFragment newInstance(String param1, String param2) {
        ServerFragment fragment = new ServerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_server, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cacheHelper = new CacheHelper(getActivity().getBaseContext());
        mDataSet = new ArrayList<Server>();
        loadData();
        return view;
    }
    public void insertData() {
        for (int i =0; i<= 20; i++){
            cacheHelper.addServer(new Server(i +"instance "+ i, String.valueOf(i),"running","192.168.1.1","ubuntu","as:asda:asda",
                    "2 core", "running"));
        }
    }
    private void loadData(){
        ArrayList<Server> servers = cacheHelper.getAllServer();
        if (servers.size() ==0)
            insertData();
        serverAdapter = new ServerAdapter(getContext(), servers, getActivity());
        mRecyclerView.setAdapter(serverAdapter);
    }

}
