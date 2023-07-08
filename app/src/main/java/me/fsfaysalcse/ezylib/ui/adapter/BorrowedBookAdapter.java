package me.fsfaysalcse.ezylib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import me.fsfaysalcse.ezylib.R;
import me.fsfaysalcse.ezylib.ui.model.BorrowItem;

public class BorrowedBookAdapter extends ListAdapter<BorrowItem, BorrowedBookAdapter.BorrowedBookViewHolder> {

    private Context context;

    public BorrowedBookAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public BorrowedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_borrowed_book, parent, false);
        return new BorrowedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowedBookViewHolder holder, int position) {
        BorrowItem borrowItem = getItem(position);

        holder.tvStudentId.setText(borrowItem.getStudentId());
        holder.tvBookName.setText(borrowItem.getBookTitle());
        holder.tvBorrowedDate.setText(borrowItem.getBorrowDate());
        holder.tvReturnDate.setText(borrowItem.getReturnDate());
    }

    public class BorrowedBookViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentId, tvBookName, tvBorrowedDate, tvReturnDate;

        public BorrowedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentId = itemView.findViewById(R.id.tvStudentId);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvBorrowedDate = itemView.findViewById(R.id.tvBorrowedDate);
            tvReturnDate = itemView.findViewById(R.id.tvReturnDate);
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
