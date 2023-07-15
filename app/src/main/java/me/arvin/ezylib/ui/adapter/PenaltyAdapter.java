package me.arvin.ezylib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.arvin.ezylib.R;
import me.arvin.ezylib.ui.model.BorrowItem;

public class PenaltyAdapter extends RecyclerView.Adapter<PenaltyAdapter.PenaltyViewHolder> {

    private Context context;
    private List<BorrowItem> borrowItemList;

    public interface OnItemClickListener {
        void onItemClick(BorrowItem borrowItem);
    }

    private OnItemClickListener listener;

    public PenaltyAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.borrowItemList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public PenaltyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_penalty, parent, false);
        return new PenaltyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PenaltyViewHolder holder, int position) {
        BorrowItem borrowItem = borrowItemList.get(position);

        if (!borrowItem.getBookTitle().isEmpty()) {
            holder.tvBookName.setText(borrowItem.getBookTitle());
        }

        if (!borrowItem.getReturnDate().isEmpty()) {
            holder.tvReturnDate.setText(borrowItem.getReturnDate());
        }

        holder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(borrowItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return borrowItemList.size();
    }

    public void setBorrowItemList(List<BorrowItem> borrowItemList) {
        this.borrowItemList.clear();
        if (borrowItemList != null) {
            this.borrowItemList.addAll(borrowItemList);
        }
        notifyDataSetChanged();
    }

    public class PenaltyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookName, tvReturnDate;
        TextView btnPay;

        public PenaltyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvReturnDate = itemView.findViewById(R.id.tvReturnDate);
            btnPay = itemView.findViewById(R.id.btnPayAndReturnBook);
        }
    }
}
