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
import zaynoun.ul.bbackapplication.adapters.PopularAdapter;
import zaynoun.ul.bbackapplication.models.PopularVerModal;


public class SecondFragment extends Fragment {
    List<PopularVerModal> popularVerModalslist;
    RecyclerView recyclerView;
    PopularAdapter popularAdapter;


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_second, container, false);
        recyclerView=view.findViewById(R.id.popular_ver_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        popularVerModalslist = new ArrayList<>();
        popularVerModalslist.add(new PopularVerModal(R.drawable.ver1,"Popular 1","Description 1","4.0","10:00 - 23:00"));
        popularVerModalslist.add(new PopularVerModal(R.drawable.ver2,"Popular 2","Description 2","4.3","10:00 - 23:00"));
        popularVerModalslist.add(new PopularVerModal(R.drawable.ver3,"Popular 3","Description 3","4.5","10:00 - 23:00"));
        popularAdapter = new PopularAdapter(requireContext(),popularVerModalslist);
        recyclerView.setAdapter(popularAdapter);
        return view;
    }
}