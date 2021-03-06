// Generated by view binder compiler. Do not edit!
package com.example.dailyart.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.example.dailyart.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAccountBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final FloatingActionButton fabAchievement;

  @NonNull
  public final ImageView imageSettings;

  @NonNull
  public final ImageView imgProfilePhoto;

  @NonNull
  public final ConstraintLayout linearLayout4;

  @NonNull
  public final TabItem tabFavourites;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final TabItem tabVisited;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView tvUserSettings;

  @NonNull
  public final TextView tvUsernameAccount;

  @NonNull
  public final ViewPager2 viewPager;

  private FragmentAccountBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppBarLayout appBarLayout, @NonNull FloatingActionButton fabAchievement,
      @NonNull ImageView imageSettings, @NonNull ImageView imgProfilePhoto,
      @NonNull ConstraintLayout linearLayout4, @NonNull TabItem tabFavourites,
      @NonNull TabLayout tabLayout, @NonNull TabItem tabVisited, @NonNull Toolbar toolbar,
      @NonNull TextView tvUserSettings, @NonNull TextView tvUsernameAccount,
      @NonNull ViewPager2 viewPager) {
    this.rootView = rootView;
    this.appBarLayout = appBarLayout;
    this.fabAchievement = fabAchievement;
    this.imageSettings = imageSettings;
    this.imgProfilePhoto = imgProfilePhoto;
    this.linearLayout4 = linearLayout4;
    this.tabFavourites = tabFavourites;
    this.tabLayout = tabLayout;
    this.tabVisited = tabVisited;
    this.toolbar = toolbar;
    this.tvUserSettings = tvUserSettings;
    this.tvUsernameAccount = tvUsernameAccount;
    this.viewPager = viewPager;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAccountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_account, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAccountBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appBarLayout;
      AppBarLayout appBarLayout = ViewBindings.findChildViewById(rootView, id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.fab_achievement;
      FloatingActionButton fabAchievement = ViewBindings.findChildViewById(rootView, id);
      if (fabAchievement == null) {
        break missingId;
      }

      id = R.id.imageSettings;
      ImageView imageSettings = ViewBindings.findChildViewById(rootView, id);
      if (imageSettings == null) {
        break missingId;
      }

      id = R.id.imgProfilePhoto;
      ImageView imgProfilePhoto = ViewBindings.findChildViewById(rootView, id);
      if (imgProfilePhoto == null) {
        break missingId;
      }

      ConstraintLayout linearLayout4 = (ConstraintLayout) rootView;

      id = R.id.tab_favourites;
      TabItem tabFavourites = ViewBindings.findChildViewById(rootView, id);
      if (tabFavourites == null) {
        break missingId;
      }

      id = R.id.tabLayout;
      TabLayout tabLayout = ViewBindings.findChildViewById(rootView, id);
      if (tabLayout == null) {
        break missingId;
      }

      id = R.id.tab_visited;
      TabItem tabVisited = ViewBindings.findChildViewById(rootView, id);
      if (tabVisited == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.tvUserSettings;
      TextView tvUserSettings = ViewBindings.findChildViewById(rootView, id);
      if (tvUserSettings == null) {
        break missingId;
      }

      id = R.id.tv_username_account;
      TextView tvUsernameAccount = ViewBindings.findChildViewById(rootView, id);
      if (tvUsernameAccount == null) {
        break missingId;
      }

      id = R.id.viewPager;
      ViewPager2 viewPager = ViewBindings.findChildViewById(rootView, id);
      if (viewPager == null) {
        break missingId;
      }

      return new FragmentAccountBinding((ConstraintLayout) rootView, appBarLayout, fabAchievement,
          imageSettings, imgProfilePhoto, linearLayout4, tabFavourites, tabLayout, tabVisited,
          toolbar, tvUserSettings, tvUsernameAccount, viewPager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
