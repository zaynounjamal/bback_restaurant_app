package zaynoun.ul.bbackapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.adapters.FeaturedAdapter;
import zaynoun.ul.bbackapplication.adapters.FeaturedVerAdapter;
import zaynoun.ul.bbackapplication.models.FeaturedModel;
import zaynoun.ul.bbackapplication.models.FeaturedVerModel;


public class FirstFragment extends Fragment {
    ///////Featured Hor RecyclerView
    List<FeaturedModel> featuredModelList;
    RecyclerView recyclerView;
    FeaturedAdapter featuredAdapter;
    ///////Featured Ver RecyclerView
    List<FeaturedVerModel> featuredVerModelslist;
    RecyclerView recyclerViewVer;
    FeaturedVerAdapter featuredVerAdapter;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_first, container, false);
        //HOR
        recyclerView= view.findViewById(R.id.featured_hor_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        //Ver
        recyclerViewVer= view.findViewById(R.id.featured_ver_rec);
        recyclerViewVer.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        featuredVerModelslist = new  ArrayList<>();
        //Hor
        featuredModelList = new ArrayList<>();
        featuredModelList.add(new FeaturedModel(R.drawable.fav1,"Featured 1","Description 1"));
        featuredModelList.add(new FeaturedModel(R.drawable.fav2,"Featured 2","Description 2"));
        featuredModelList.add(new FeaturedModel(R.drawable.fav3,"Featured 3","Description 3"));
        featuredAdapter = new FeaturedAdapter(requireContext(),featuredModelList);
        recyclerView.setAdapter(featuredAdapter);
        //Ver
        featuredVerModelslist.add(new FeaturedVerModel(R.drawable.ver1,"Featured 1","4.0","Description 1","10:00 - 23:00"));
        featuredVerModelslist.add(new FeaturedVerModel(R.drawable.ver2,"Featured 2","4.3","Description 2","11:00 - 23:00"));
        featuredVerModelslist.add(new FeaturedVerModel(R.drawable.ver3,"Featured 3","4.5","Description 3","12:00 - 23:00"));
        featuredVerAdapter = new FeaturedVerAdapter(getContext(),featuredVerModelslist);
        recyclerViewVer.setAdapter(featuredVerAdapter);
        return view;
    }
}