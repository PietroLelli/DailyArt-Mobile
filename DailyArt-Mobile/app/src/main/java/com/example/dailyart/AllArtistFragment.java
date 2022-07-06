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

import com.example.dailyart.ArtistRecyclerView.Artist;
import com.example.dailyart.ArtistRecyclerView.ArtistCardAdapter;
import com.example.dailyart.ArtistRecyclerView.OnItemListenerArtist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllArtistFragment extends Fragment implements OnItemListenerArtist {

    private DatabaseReference dbRef;
    private SearchView searchView;
    private ArtistCardAdapter adapterArtist;
    private RecyclerView recyclerArtist;
    private List<Artist> artistListAll = new ArrayList<>();
    private List<Artist> artistList = new ArrayList<>();
    private List<Artist> artistFiltered = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyart-mobile-default-rtdb.firebaseio.com/");
        return inflater.inflate(R.layout.fragment_all_artist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (artistList != null) {
            artistList.clear();
        }
        recyclerArtist = view.findViewById(R.id.recyclerViewAllArtists);
        recyclerArtist.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerArtist.setHasFixedSize(true);
        final OnItemListenerArtist listenerArtist = this;
        adapterArtist = new ArtistCardAdapter(listenerArtist, artistList, getActivity());
        recyclerArtist.setAdapter(adapterArtist);

        searchView = getActivity().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                artistFiltered = new ArrayList<>();
                artistList.clear();
                artistList.addAll(artistListAll);
                for (Artist item : artistList){
                    if(item.getName().toLowerCase().contains(query.toLowerCase())){
                        artistFiltered.add(item);
                    }
                }
                artistList.clear();
                artistList.addAll(artistFiltered);
                adapterArtist.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                artistFiltered = new ArrayList<>();
                artistList.clear();
                artistList.addAll(artistListAll);
                for (Artist item : artistList){
                    if(item.getName().toLowerCase().contains(newText.toLowerCase())){
                        artistFiltered.add(item);
                    }
                }
                artistList.clear();
                artistList.addAll(artistFiltered);
                adapterArtist.notifyDataSetChanged();

                if(newText == ""){
                    artistList.clear();
                    artistList.addAll(artistListAll);
                    adapterArtist.notifyDataSetChanged();
                }
                return false;
            }

        });

        searchView.setOnCloseListener(() -> {
            artistList.clear();
            artistList.addAll(artistListAll);
            adapterArtist.notifyDataSetChanged();
            return false;
        });


        dbRef.child("Artist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                artistList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Artist artist = dataSnapshot.getValue(Artist.class);
                    artistList.add(artist);
                }
                artistListAll.clear();
                artistListAll.addAll(artistList);
                adapterArtist.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onItemClickArtist(int position) {
        Utilities.insertFragment((AppCompatActivity) getActivity(), new ArtistDetailsFragment(adapterArtist.getArtistSelected(position)), ArtistDetailsFragment.class.getSimpleName());
        searchView.setQuery("", false);
    }
}