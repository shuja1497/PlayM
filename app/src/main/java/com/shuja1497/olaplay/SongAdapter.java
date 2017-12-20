package com.shuja1497.olaplay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by shuja1497 on 12/20/17.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;
    private Context context;


    class SongViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSongName,  textViewArtistName;
        ImageView imageView;

        public SongViewHolder(View itemView) {
            super(itemView);

            textViewSongName = itemView.findViewById(R.id.song_name);
            textViewArtistName = itemView.findViewById(R.id.artist_name);
            imageView = itemView.findViewById(R.id.cover_image);
        }
    }

    public SongAdapter(List<Song> songList, Context context){
        this.songList = songList;
        this.context = context;

    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        SongViewHolder vh = new SongViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SongAdapter.SongViewHolder holder, int position) {

        Song song = songList.get(position);

        holder.textViewSongName.setText(song.getSong());
        holder.textViewArtistName.setText(song.getArtists());
        Glide.with(context).load(song.getCover_image()).into(holder.imageView);

        Log.d(TAG, "onBindViewHolder: +++++****"+ song.getUrl());

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

}
