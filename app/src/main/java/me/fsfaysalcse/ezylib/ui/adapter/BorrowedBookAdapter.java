package me.fsfaysalcse.ezylib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import me.fsfaysalcse.ezylib.R;
import me.fsfaysalcse.ezylib.ui.model.BorrowedBookItem;

public class BorrowedBookAdapter extends RecyclerView.Adapter<BorrowedBookAdapter.BorrowedBookViewHolder> {

    private Context context;
    private List<BorrowedBookItem> borrowedBookList;

    public BorrowedBookAdapter(Context context, List<BorrowedBookItem> borrowedBookList) {
        this.context = context;
        this.borrowedBookList = borrowedBookList;
    }

    @NonNull
    @Override
    public BorrowedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_borrowed_book, parent, false);
        return new BorrowedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowedBookViewHolder holder, int position) {
        BorrowedBookItem borrowedBookItem = borrowedBookList.get(position);

        holder.tvStudentId.setText(borrowedBookItem.getStudentId());
        holder.tvBookName.setText(borrowedBookItem.getBookName());
        holder.tvBorrowedDate.setText(borrowedBookItem.getBorrowedDate());
        holder.tvReturnDate.setText(borrowedBookItem.getReturnDate());
    }

    @Override
    public int getItemCount() {
        return borrowedBookList.size();
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
}

