package com.example.jun.antiphone.cart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jun.antiphone.R;

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
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerDataAdapter.DataViewHolder holder, int position) {
        PostDTO dto = listPost.get(position);
//        final int discount = dto.getDiscountRate();
        holder.postTitle.setText(dto.getTitle());
        //set background

        //set sale image
//        if(discount > 0) {
//            holder.ic_sale.setBackgroundResource(R.drawable.ic_sale);
//        }
        holder.post_image.setBackgroundResource(dto.getImageDir());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                intent.putExtra("IMAGEDIR", listPost.get(position).getImageDir());
                intent.putExtra("POSTID", listPost.get(position).getPostID());
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
        private ImageView ic_sale;
        private ImageView post_image;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public DataViewHolder(final View itemView) {
            super(itemView);
//            ic_sale = itemView.findViewById(R.id.post_sale);
            postTitle = itemView.findViewById(R.id.post_title);
            post_image = itemView.findViewById(R.id.post_image);
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

