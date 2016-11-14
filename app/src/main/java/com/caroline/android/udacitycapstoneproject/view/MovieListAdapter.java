package com.caroline.android.udacitycapstoneproject.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caroline.android.udacitycapstoneproject.R;
import com.caroline.android.udacitycapstoneproject.model.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinestewart on 9/12/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    public final OnMovieClickListener movieClickListener;
    private List<MovieItem> movieViewData;
    private Context context;


    public MovieListAdapter(Context context, OnMovieClickListener movieClickListener) {
        this.context = context;
        this.movieClickListener = movieClickListener;
        this.movieViewData = new ArrayList<>();
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardlayout, viewGroup, false);
        final MovieListAdapter.ViewHolder viewHolder = new MovieListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MovieItem item = movieViewData.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieClickListener.onMovieClicked(movieViewData.get(position));
            }
        });
        holder.setTitleText(item.getTitle());
        holder.setYearView(item.getYear());
        holder.setImdbRatingView(item.getImdbRating());
        holder.setRankView(item.getRank());
        holder.setImdbVotesView(item.getImdbVotes());
        holder.setImdbLinkView(item.getImdbLink());

        Picasso.with(context)
               .load(item.getPoster())
               .into(holder.posterView);
    }

    @Override
    public int getItemCount() {
        return (null != movieViewData ? movieViewData.size() : 0);
    }

    public void setData(List<MovieItem> list) {
        movieViewData = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterView;
        private TextView titleView;
        private TextView yearView;
        private TextView imdbRatingView;
        private TextView imdbVotesView;
        private TextView imdbLinkView;
        private TextView rankView;

        public void setTitleText(String titleText) {
            titleView.setText(titleText);
        }

        public void setYearView(String yearText) {
            yearView.setText(yearText);
        }

        public void setRankView(String rankText) {
            rankView.setText(rankText);
        }

        public void setImdbRatingView(String imdbRatingText) {
            imdbRatingView.setText(imdbRatingText);
        }

        public void setImdbVotesView(String imdbText) {
            imdbVotesView.setText(imdbText);
        }

        public void setImdbLinkView(String imdbLinkText) {
            imdbLinkView.setText(imdbLinkText);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.title);
            yearView = (TextView) itemView.findViewById(R.id.year);
            posterView = (ImageView) itemView.findViewById(R.id.poster);
            imdbVotesView = (TextView) itemView.findViewById(R.id.imdbVotes);
            rankView = (TextView) itemView.findViewById(R.id.rank);
            imdbRatingView = (TextView) itemView.findViewById(R.id.imdbRating);
            imdbLinkView = (TextView) itemView.findViewById(R.id.imdbLink);
        }
    }

    public interface OnMovieClickListener {
        void onMovieClicked(MovieItem movieItem);
    }
}
