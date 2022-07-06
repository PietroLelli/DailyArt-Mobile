package com.example.dailyart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailyart.ArtWorkRecyclerView.ArtWork;
import com.example.dailyart.ArtWorkRecyclerView.ArtworkCardAdapter;
import com.example.dailyart.ArtWorkRecyclerView.OnItemListenerArtWork;
import com.example.dailyart.ArtistRecyclerView.Artist;
import com.example.dailyart.MuseumRecyclerView.Museum;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllArtworkFragment extends Fragment implements OnItemListenerArtWork {

    private Museum museum;
    private Artist artist;
    private SearchView searchView;
    private DatabaseReference dbRef;

    private ArtworkCardAdapter adapterArtwork;
    private RecyclerView recyclerArtwork;
    private List<ArtWork> artworkListAll = new ArrayList<>();
    private List<ArtWork> artworkList = new ArrayList<>();
    private List<ArtWork> artworkFiltered;

    public AllArtworkFragment() {}

    public AllArtworkFragment(Museum museum) {
        this.museum = museum;
    }

    public AllArtworkFragment(Artist artist) {
        this.artist = artist;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyart-mobile-default-rtdb.firebaseio.com/");
        return inflater.inflate(R.layout.fragment_all_artwork, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (artworkList != null) {
            artworkList.clear();
        }
        recyclerArtwork = view.findViewById(R.id.recyclerViewAllArtwork);
        recyclerArtwork.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerArtwork.setHasFixedSize(true);
        final OnItemListenerArtWork listenerArtWork = this;
        adapterArtwork = new ArtworkCardAdapter(listenerArtWork, artworkList, getActivity());
        recyclerArtwork.setAdapter(adapterArtwork);

        searchView = getActivity().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                artworkFiltered = new ArrayList<>();
                artworkList .clear();
                artworkList.addAll(artworkListAll);
                for (ArtWork item : artworkList){
                    if(item.getName().toLowerCase().contains(query.toLowerCase())){
                        artworkFiltered.add(item);
                    }
                }
                artworkList .clear();
                artworkList.addAll(artworkFiltered);
                adapterArtwork.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                artworkFiltered = new ArrayList<>();
                artworkList.clear();
                artworkList.addAll(artworkListAll);
                for (ArtWork item : artworkList){
                    if(item.getName().toLowerCase().contains(newText.toLowerCase())){
                        artworkFiltered.add(item);
                    }
                }
                artworkList.clear();
                artworkList.addAll(artworkFiltered);
                adapterArtwork.notifyDataSetChanged();

                if(newText == ""){
                    artworkList.clear();
                    artworkList.addAll(artworkListAll);
                    adapterArtwork.notifyDataSetChanged();
                }
                return false;
            }

        });
        searchView.setOnCloseListener(() -> {
            artworkList.clear();
            artworkList.addAll(artworkListAll);
            adapterArtwork.notifyDataSetChanged();
            return false;
        });

        dbRef.child("ArtWork").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                artworkList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (museum != null) {
                        String getArtworkMuseum = dataSnapshot.child("museum").getValue(String.class);
                        if (getArtworkMuseum.equals(museum.getName())) {
                            ArtWork artWork = dataSnapshot.getValue(ArtWork.class);
                            artworkList.add(artWork);
                        }
                    } else if (artist != null){
                        String getArtworkArtist = dataSnapshot.child("artist").getValue(String.class);
                        if (getArtworkArtist.equals(artist.getName())) {
                            ArtWork artWork = dataSnapshot.getValue(ArtWork.class);
                            artworkList.add(artWork);
                        }
                    } else {
                        ArtWork artWork = dataSnapshot.getValue(ArtWork.class);
                        artworkList.add(artWork);
                    }
                }
                artworkListAll.clear();
                artworkListAll.addAll(artworkList);
                adapterArtwork.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onItemClickArtWork(int position) {
        Utilities.insertFragment((AppCompatActivity) getActivity(), new ArtworkDetailsFragment(adapterArtwork.getArtWorkSelected(position)), ArtworkDetailsFragment.class.getSimpleName());
        searchView.setQuery("", false);

    }
}