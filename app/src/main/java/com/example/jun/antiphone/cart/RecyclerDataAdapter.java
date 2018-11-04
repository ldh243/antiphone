package com.example.jun.antiphone.cart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jun.antiphone.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import entity.PostDTO;


public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.DataViewHolder> {

    private List<PostDTO> listPost;
    private Context context;

    public RecyclerDataAdapter(Context context, List<PostDTO> listPosts) {
        this.listPost = listPosts;
        this.context = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_view, viewGroup, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerDataAdapter.DataViewHolder holder, int position) {
        PostDTO dto = listPost.get(position);
        holder.postTitle.setText(dto.getStoreTitle());
        Picasso.with(context).load(dto.getBanner()).error(R.drawable.image_not_available).into(holder.post_image);
        Picasso.with(context).load(dto.getLogoStore()).error(R.drawable.image_not_available).into(holder.logoPostCartScreen);
        holder.discountRateCartScreen.setText("-" + dto.getDiscountRate() + "%");
        String date = dto.getStartDate() + " - " + dto.getToDate();
        holder.tvDateCartScreen.setText(date);
        holder.tvDescriptionCartScreen.setText(dto.getPostID() + " " + dto.getDescription());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                PostDTO dto = listPost.get(position);
                intent.putExtra("POSTID", dto.getPostID());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPost == null ? 0 : listPost.size();
    }


    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView postTitle;
        private ImageView post_image;
        private ImageView logoPostCartScreen;
        private ItemClickListener itemClickListener;
        private TextView discountRateCartScreen;
        private TextView tvDateCartScreen;
        private TextView tvDescriptionCartScreen;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public DataViewHolder(final View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.tvPostTitle);
            post_image = itemView.findViewById(R.id.imgvPostImageCartScreen);
            logoPostCartScreen = itemView.findViewById(R.id.logoPostCartScreen);
            discountRateCartScreen = itemView.findViewById(R.id.discountRateCartScreen);
            tvDateCartScreen = itemView.findViewById(R.id.tvDateCartScreen);
            tvDescriptionCartScreen = itemView.findViewById(R.id.tvDescriptionCartScreen);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

}

