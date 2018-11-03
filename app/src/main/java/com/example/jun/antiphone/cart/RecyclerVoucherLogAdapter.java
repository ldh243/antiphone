package com.example.jun.antiphone.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jun.antiphone.R;

import java.util.List;

import entity.PostDTO;
import entity.VoucherDTO;
import entity.VoucherLogDTO;


public class RecyclerVoucherLogAdapter extends RecyclerView.Adapter<RecyclerVoucherLogAdapter.DataViewHolder> {

    private List<VoucherLogDTO> voucherLogList;
    private Context context;

    public RecyclerVoucherLogAdapter(List<VoucherLogDTO> listPosts) {
        this.voucherLogList = listPosts;

    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_log_item, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerVoucherLogAdapter.DataViewHolder holder, int position) {

        VoucherLogDTO dto = voucherLogList.get(position);
        //get VoucherID từ VoucherLog --> lấy được voucher --> post ID --> post Name

        // ĐỔ dữ lịu cứng
        VoucherDTO dto1 = new VoucherDTO();
        dto1.setVoucherNumber("BKJHFKJNKJ");

        PostDTO dto2 = new PostDTO();
        dto2.setTitle("POST TITLE DEMO");
        //

        holder.date.setText(dto.getDate());
        holder.postTitle.setText("" + dto2.getTitle());
        holder.voucherNumber.setText("" + dto1.getVoucherNumber());

        //Khi click -> show detail nếu muốn
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
//                view.getContext().startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return voucherLogList == null ? 0 : voucherLogList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int pos;
        private TextView date;
        private TextView postTitle;
        private TextView voucherNumber;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public DataViewHolder(final View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.voucherDate);
            postTitle = itemView.findViewById(R.id.postTitle);
            voucherNumber = itemView.findViewById(R.id.voucherNumber);

            itemView.setOnClickListener(this);
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getPos(), false);
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }


}
