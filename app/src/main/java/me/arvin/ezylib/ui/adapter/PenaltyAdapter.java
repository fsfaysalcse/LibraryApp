package me.arvin.ezylib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import me.arvin.ezylib.R;
import me.arvin.ezylib.ui.model.BorrowItem;

public class PenaltyAdapter extends ListAdapter<BorrowItem, PenaltyAdapter.PenaltyViewHolder> {

    private Context context;

    public interface OnItemClickListener {
        void onItemClick(BorrowItem borrowItem);
    }

    private OnItemClickListener listener;
    public PenaltyAdapter(Context context, OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
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
        BorrowItem borrowItem = getItem(position);


        if (!borrowItem.getBookTitle().isEmpty()) {
            holder.tvBookName.setText(borrowItem.getBookTitle());
        }

        if (borrowItem.getReturnDate().isEmpty()) {
            holder.tvReturnDate.setText(borrowItem.getReturnDate());
        }

        holder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(borrowItem);
            }
        });
    }

    public class PenaltyViewHolder extends RecyclerView.ViewHolder {
        TextView  tvBookName, tvReturnDate;
        TextView btnPay;

        public PenaltyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvReturnDate = itemView.findViewById(R.id.tvReturnDate);
            btnPay = itemView.findViewById(R.id.btnPayAndReturnBook);
        }
    }

    private static final DiffUtil.ItemCallback<BorrowItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<BorrowItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull BorrowItem oldItem, @NonNull BorrowItem newItem) {
            return oldItem.getBookId().equals(newItem.getBookId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BorrowItem oldItem, @NonNull BorrowItem newItem) {
            return oldItem.getBookId().equals(newItem.getBookId());
        }
    };
}
