package me.fsfaysalcse.ezylib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.fsfaysalcse.ezylib.R;
import me.fsfaysalcse.ezylib.ui.model.BorrowItem;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.BorrowViewHolder> {

    private Context context;
    private List<BorrowItem> borrowList;


   public interface OnItemClickListener {
        void onItemClick(BorrowItem borrowItem);
    }

    private OnItemClickListener onItemClickListener;

    public BorrowAdapter(Context context, List<BorrowItem> borrowList, OnItemClickListener listener) {
        this.context = context;
        this.borrowList = borrowList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public BorrowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_borrow, parent, false);
        return new BorrowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowViewHolder holder, int position) {
        BorrowItem borrowItem = borrowList.get(position);

        holder.tvTitle.setText(borrowItem.getTitle());
        holder.tvAuthor.setText(borrowItem.getAuthor());
        holder.tvAvailability.setText(borrowItem.isAvailable() ? "Available" : "Not Available");

        if (borrowItem.isAvailable()) {
            holder.tvAvailability.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tvAvailability.setTextColor(context.getResources().getColor(R.color.rectangle_11_color));
        }

        holder.root.setOnClickListener(v -> {
            if (borrowItem.isAvailable()) {
                onItemClickListener.onItemClick(borrowItem);
            } else {
                Toast.makeText(context, "This book is not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return borrowList.size();
    }

    public class BorrowViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor, tvAvailability;
        View root;

        public BorrowViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvAvailability = itemView.findViewById(R.id.tvAvailability);
        }
    }
}

