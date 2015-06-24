/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.deco.amazingpics.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.deco.amazingpics.R;
import org.deco.amazingpics.model.FeedData;
import org.deco.amazingpics.views.RecyclerClickListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PicsListAdapter extends RecyclerView.Adapter<PicsListAdapter.AvengerViewHolder> {

    private final RecyclerClickListener recyclerListener;
    private final List<FeedData> avengers;

    private Context context;

    public PicsListAdapter(List<FeedData> avengers, Context context, RecyclerClickListener recyclerClickListener) {

        this.avengers = avengers;
        this.context = context;
        this.recyclerListener = recyclerClickListener;
    }

    @Override
    public AvengerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(
            R.layout.item_avenger, parent, false);

        return new AvengerViewHolder(rootView, recyclerListener);
    }

    @Override
    public void onBindViewHolder(AvengerViewHolder holder, int position) {

        holder.bindAvenger(avengers.get(position));
    }

    @Override
    public int getItemCount() {

        return avengers.size();
    }

    public class AvengerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.item_avenger_title) TextView titleTextView;
        @InjectView(R.id.item_avenger_thumb) ImageView picImageView;

        public AvengerViewHolder(View itemView, final RecyclerClickListener recyclerClickListener) {

            super(itemView);
            ButterKnife.inject(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindAvenger(FeedData feedData) {

            titleTextView.setText(feedData.link);
            if(feedData.imagesBlock!= null && feedData.imagesBlock.standardResolution.url != null){
                Glide.with(context)
                        .load(feedData.imagesBlock.standardResolution.url)
                        .into(picImageView);
            }
        }

        private void bindListener(View itemView, final RecyclerClickListener recyclerClickListener) {

            itemView.setOnClickListener(v -> recyclerClickListener.onElementClick(getPosition()));
        }
    }

    public void updateList(List<FeedData> data) {
        if(data != avengers){
            if(avengers != null){
                avengers.clear();
            }
            avengers.addAll(data);
        }
        notifyDataSetChanged();
    }
}
