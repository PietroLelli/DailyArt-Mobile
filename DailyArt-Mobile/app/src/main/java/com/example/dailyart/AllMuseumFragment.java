package com.example.dailyart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailyart.MuseumRecyclerView.AllMuseumCardAdapter;
import com.example.dailyart.MuseumRecyclerView.Museum;
import com.example.dailyart.MuseumRecyclerView.OnItemListenerMuseum;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllMuseumFragment extends Fragment implements OnItemListenerMuseum {

    private DatabaseReference dbRef;
    private SearchView searchView;
    private AllMuseumCardAdapter adapterMuseum;
    private RecyclerView recyclerViewMuseum;
    private List<Museum> museumListAll = new ArrayList<>();
    private List<Museum> museumList = new ArrayList<>();
    private List<Museum> museumFiltered = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyart-mobile-default-rtdb.firebaseio.com/");
        return inflater.inflate(R.layout.fragment_all_museum, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (museumList != null) {
            museumList.clear();
        }
        recyclerViewMuseum = view.findViewById(R.id.recyclerViewAllMuseums);
        recyclerViewMuseum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewMuseum.setHasFixedSize(true);
        final OnItemListenerMuseum listenerMuseum = this;
        adapterMuseum = new AllMuseumCardAdapter(listenerMuseum, museumList, getActivity());
        recyclerViewMuseum.setAdapter(adapterMuseum);

        searchView = getActivity().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                museumFiltered = new ArrayList<>();
                museumList .clear();
                museumList.addAll(museumListAll);
                for (Museum item : museumList){
                    if(item.getName().toLowerCase().contains(query.toLowerCase())){
                        museumFiltered.add(item);
                    }
                }
                museumList.clear();
                museumList.addAll(museumFiltered);
                adapterMuseum.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                museumFiltered = new ArrayList<>();
                museumList .clear();
                museumList.addAll(museumListAll);
                for (Museum item : museumList){
                    if(item.getName().toLowerCase().contains(newText.toLowerCase())){
                        museumFiltered.add(item);
                    }
                }
                museumList.clear();
                museumList.addAll(museumFiltered);
                adapterMuseum.notifyDataSetChanged();

                if(newText == ""){
                    museumList.clear();
                    museumList.addAll(museumListAll);
                    adapterMuseum.notifyDataSetChanged();
                }
                return false;
            }

        });

        searchView.setOnCloseListener(() -> {
            museumList.clear();
            museumList.addAll(museumListAll);
            adapterMuseum.notifyDataSetChanged();
            return false;
        });


        dbRef.child("Museum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                museumList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Museum museum = dataSnapshot.getValue(Museum.class);
                    museumList.add(museum);
                }
                museumListAll.clear();
                museumListAll.addAll(museumList);
                adapterMuseum.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onItemClickMuseum(int position) {
        Utilities.insertFragment((AppCompatActivity) getActivity(), new MuseumDetailsFragment(adapterMuseum.getMuseumSelected(position)), MuseumDetailsFragment.class.getSimpleName());
        searchView.setQuery("", false);
    }
}