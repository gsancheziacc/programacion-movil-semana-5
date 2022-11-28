package com.example.gabriel_sanchez_s5.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gabriel_sanchez_s5.databinding.FragmentListBinding;
import com.example.gabriel_sanchez_s5.db.DBHandler;
import com.example.gabriel_sanchez_s5.model.PersonalModel;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private ListViewAdapter adapter;
    private DBHandler dbHandler;
    private ArrayList<PersonalModel> personalList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListViewModel ListViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHandler = new DBHandler(root.getContext());
        personalList = dbHandler.getAllPersonal();
        ListView listView = binding.lvPersonalList;

        adapter = new ListViewAdapter(root.getContext(), personalList);
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}