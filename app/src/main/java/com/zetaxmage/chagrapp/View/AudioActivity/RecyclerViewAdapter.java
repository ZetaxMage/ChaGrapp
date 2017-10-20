package com.zetaxmage.chagrapp.View.AudioActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.zetaxmage.chagrapp.Model.POJO.Audio;
import com.zetaxmage.chagrapp.R;

import java.util.List;

/**
 * Created by ZetaxMage on 12/10/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    private Context context;
    private List<Audio> audioList;
    private RecyclerSongInterface listener;
    private Boolean audioSort;

    public RecyclerViewAdapter(Context context, List<Audio> audioList, RecyclerSongInterface listener, Boolean audioSort) {
        this.context = context;
        this.audioList = audioList;
        this.listener = listener;
        this.audioSort = audioSort;
    }

    // Metodo para inforar que hubo un cambio en el orden
    public void notifyAudioSortChanged(Boolean order) {
        audioSort = order;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.structure_recycler_v2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Audio audio = getItem(position);
        holder.loadView(audio);
        holder.imageViewPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickPlay(audio);
            }
        });
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public Audio getItem (int position) {
        return audioList.get(position);
    }

    // Metodo para ver que muestra el fastscroll popup
    @NonNull
    @Override
    public String getSectionName(int position) {
        if (audioSort) {
            Character c = getItem(position).getAudioDisplayName().charAt(0);
            return c.toString();
        } else {
            return getItem(position).getAudioDisplayAuthor();
        }
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAudioName;
        TextView textViewAudioAuthor;
        ImageView imageViewPlayButton;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewAudioName = itemView.findViewById(R.id.textView_audioName);
            textViewAudioAuthor = itemView.findViewById(R.id.textView_audioAuthor);
            imageViewPlayButton = itemView.findViewById(R.id.imageView_playButton);
        }

        public void loadView (Audio audio) {
            textViewAudioName.setText(audio.getAudioDisplayName());
            textViewAudioAuthor.setText(audio.getAudioDisplayAuthor());
        }
    }

    public interface RecyclerSongInterface {
        void onClickPlay (Audio audio);
    }
}
