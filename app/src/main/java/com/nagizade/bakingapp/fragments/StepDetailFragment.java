package com.nagizade.bakingapp.fragments;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nagizade.bakingapp.R;
import com.nagizade.bakingapp.activity.MainActivity;
import com.nagizade.bakingapp.activity.StepDetailActivity;
import com.nagizade.bakingapp.activity.StepListActivity;
import com.nagizade.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.Objects;

import static android.view.View.GONE;


public class StepDetailFragment extends Fragment {

    public  static final String ARG_STEP_OBJ            = "step_object";
    public  static final String ARG_STEP_POS            = "step_position";
    public  static final String ARG_STEP_ARRAY          = "step_array";

    private int                 stepPosition;
    private Step                step;
    private View                rootView;
    private String              videoURL;
    private Toolbar             detailToolbar;
    private PlayerView          playerView;
    private FrameLayout         mainMediaFrame;
    private SimpleExoPlayer     simpleExoPlayer;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_STEP_OBJ)) {

            step            = getArguments().getParcelable(ARG_STEP_OBJ);
            stepPosition    = getArguments().getInt(ARG_STEP_POS);

            Activity activity = this.getActivity();
            assert activity != null;
            detailToolbar = activity.findViewById(R.id.tb_details);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView            = inflater.inflate(R.layout.step_detail, container, false);
        playerView          = rootView.findViewById(R.id.exoplayer);
        mainMediaFrame      = rootView.findViewById(R.id.main_media_frame);

       if(savedInstanceState != null) {
           stepPosition     = savedInstanceState.getInt(ARG_STEP_POS);
       }
        setupData(rootView,step);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
           initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || simpleExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_STEP_POS,stepPosition);
    }

    /**
     * Method for setting up step data. We need two parameters. First parameter is rootView
     * and second parameter is position of step.
     * @param rootView - the view which has all the elements needed to fill with step data
     */

    private void setupData(View rootView,Step step) {
        if (step != null) {

            // Setting Toolbar title
            if (detailToolbar != null) {
                detailToolbar.setTitle(step.getShortDescription());
            }

            if(!step.getVideoURL().equals("")) {
                videoURL = step.getVideoURL();
            } else videoURL = step.getThumbnailURL();

            if(videoURL.equals("")) {    // if no video is presented we will hide player
                mainMediaFrame.setVisibility(GONE);
                playerView.setVisibility(GONE);
            } else {
                mainMediaFrame.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.VISIBLE);
            }

            //Setting step text instruction
            ((TextView) rootView.findViewById(R.id.tv_step_detail)).setText(step.getDescription());
        }
    }

    /***
     * Method to initialize ExoPlayer
     */
    private void initializePlayer() {
        if(simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory,trackSelector,loadControl);
            simpleExoPlayer.setPlayWhenReady(true);
            playerView.setPlayer(simpleExoPlayer);
        }

        String userAgent = Util.getUserAgent(getContext(),getContext().getString(R.string.app_name));
        MediaSource mediaSource = new ExtractorMediaSource
                .Factory(new DefaultDataSourceFactory(getContext(),userAgent))
                .setExtractorsFactory(new DefaultExtractorsFactory())
                .createMediaSource(Uri.parse(videoURL));
        simpleExoPlayer.prepare(mediaSource);
    }

    /**
     * Method to release ExoPlayer
     */

    private void releasePlayer() {
        if(simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
