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
import zaynoun.ul.bbackapplication.adapters.NewAdapter;
import zaynoun.ul.bbackapplication.models.NewModel;


public class ThirdFragment extends Fragment {
    List<NewModel> newModelList;
    RecyclerView recyclerView;
    NewAdapter newAdapter;

    public ThirdFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_third, container, false);
        recyclerView=view.findViewById(R.id.new_ver_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        newModelList=new ArrayList<>();
        newModelList.add(new NewModel(R.drawable.ver1,"New 1","Description 1","3.0","10:00 - 23:00"));
        newModelList.add(new NewModel(R.drawable.ver2,"New 2","Description 2","3.5","10:00 - 23:00"));
        newModelList.add(new NewModel(R.drawable.ver3,"New 3","Description 4","3.9","10:00 - 23:00"));
        newAdapter=new NewAdapter(requireContext(),newModelList);
        recyclerView.setAdapter(newAdapter);
        return view;
    }
}