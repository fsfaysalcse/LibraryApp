package me.arvin.ezylib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.arvin.ezylib.ui.model.Book;
import me.arvin.ezylib.ui.utli.SharedPreferenceManager;
import me.arvin.ezylib.R;

public class BorrowAdapter extends ListAdapter<Book, BorrowAdapter.BorrowViewHolder> {

    private Context context;
    private List<Book> originalList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Book borrowItem);
    }

    public BorrowAdapter(Context context, OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.originalList = new ArrayList<>();
    }

    @NonNull
    @Override
    public BorrowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_borrow, parent, false);
        return new BorrowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowViewHolder holder, int position) {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context);
        Book borrowItem = getItem(position);

        holder.tvTitle.setText(borrowItem.getBookTitle());
        holder.tvAuthor.setText(borrowItem.getAuthor());
        holder.tvAvailability.setText(borrowItem.isBorrowed() ? "Not Available" : "Available");

        if (!borrowItem.isBorrowed()) {
            holder.tvAvailability.setText("Available");
            holder.tvAvailability.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tvAvailability.setText("Not Available");
            holder.tvAvailability.setTextColor(context.getResources().getColor(R.color.rectangle_11_color));
        }

        holder.root.setOnClickListener(v -> {
            if (sharedPreferenceManager.getUserType().equals("admin")) {
                if (!borrowItem.isBorrowed()) {
                    onItemClickListener.onItemClick(borrowItem);
                } else {
                    Toast.makeText(context, "This book is not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateData(List<Book> bookList) {
        originalList.clear();
        originalList.addAll(bookList);
        submitList(bookList);
    }

    public void search(String query) {
        List<Book> searchResults = new ArrayList<>();
        for (Book book : originalList) {
            if (book.getBookTitle().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(book);
            }
        }
        submitList(searchResults);
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

    private static final DiffUtil.ItemCallback<Book> DIFF_CALLBACK = new DiffUtil.ItemCallback<Book>() {
        @Override
        public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getId().equals(newItem.getId()) && oldItem.getBookTitle().equals(newItem.getBookTitle());
        }
    };

}
