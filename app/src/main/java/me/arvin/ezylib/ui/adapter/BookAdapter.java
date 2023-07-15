package me.arvin.ezylib.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.arvin.ezylib.R;
import me.arvin.ezylib.ui.model.Returned;
import me.arvin.ezylib.ui.utli.SharedPreferenceManager;
import me.arvin.ezylib.databinding.ItemBookBinding;
import me.arvin.ezylib.ui.model.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final Context context;
    private final List<Book> bookList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Book book);
        void onDeleteClick(Book book);
    }

    public BookAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.bookList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setBookList(List<Book> books) {
        bookList.clear();
        if (books != null) {
            bookList.addAll(books);
        }
        notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookBinding binding;

        public BookViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Book book) {
            binding.tvBookTitle.setText("Name : " + book.getBookTitle());
            binding.tvBookId.setText("Id : " + book.getId());
            binding.tvAuthor.setText("Author : " + book.getAuthor());
            binding.tvPublishYear.setText("Publish Date : " + String.valueOf(book.getPublishYear()));

            SharedPreferenceManager manager = new SharedPreferenceManager(context);
            if (manager.getUserType().equals("admin")) {
                binding.btnDelete.setVisibility(View.VISIBLE);
                binding.btnDelete.setOnClickListener(v -> listener.onDeleteClick(book));
            } else {
                binding.btnDelete.setVisibility(View.GONE);
            }

            Log.d("dfsdfgdfsg", "bind: " + book.isBorrowed());

            if (!book.isBorrowed()) {
                binding.tvAvailability.setText("Available");
                binding.tvAvailability.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                binding.tvAvailability.setText("Not Available");
                binding.tvAvailability.setTextColor(context.getResources().getColor(R.color.rectangle_11_color));
            }
        }
    }
}
