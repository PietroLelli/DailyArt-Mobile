// Generated by view binder compiler. Do not edit!
package com.example.dailyart.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.dailyart.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAllArtworkBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout linearLayout;

  @NonNull
  public final RecyclerView recyclerViewAllArtwork;

  @NonNull
  public final ScrollView scrollView2;

  @NonNull
  public final SearchView searchView;

  @NonNull
  public final TextView textView8;

  private FragmentAllArtworkBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout linearLayout, @NonNull RecyclerView recyclerViewAllArtwork,
      @NonNull ScrollView scrollView2, @NonNull SearchView searchView,
      @NonNull TextView textView8) {
    this.rootView = rootView;
    this.linearLayout = linearLayout;
    this.recyclerViewAllArtwork = recyclerViewAllArtwork;
    this.scrollView2 = scrollView2;
    this.searchView = searchView;
    this.textView8 = textView8;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAllArtworkBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAllArtworkBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_all_artwork, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAllArtworkBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout linearLayout = (ConstraintLayout) rootView;

      id = R.id.recyclerViewAllArtwork;
      RecyclerView recyclerViewAllArtwork = ViewBindings.findChildViewById(rootView, id);
      if (recyclerViewAllArtwork == null) {
        break missingId;
      }

      id = R.id.scrollView2;
      ScrollView scrollView2 = ViewBindings.findChildViewById(rootView, id);
      if (scrollView2 == null) {
        break missingId;
      }

      id = R.id.searchView;
      SearchView searchView = ViewBindings.findChildViewById(rootView, id);
      if (searchView == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = ViewBindings.findChildViewById(rootView, id);
      if (textView8 == null) {
        break missingId;
      }

      return new FragmentAllArtworkBinding((ConstraintLayout) rootView, linearLayout,
          recyclerViewAllArtwork, scrollView2, searchView, textView8);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
