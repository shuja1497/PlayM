package com.shuja1497.olaplay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by shuja1497 on 12/20/17.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>

    implements Filterable{

    private List<Song> songList;
    private Context context;
    private List<Song> mFilteredList;


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
        this.mFilteredList = songList;

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
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = songList;
                } else {
                    List<Song> filteredList = new ArrayList<>();
                    for (Song row : songList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSong().toLowerCase().contains(charString.toLowerCase()) || row.getArtists().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Song>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}
