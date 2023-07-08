package me.fsfaysalcse.ezylib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import me.fsfaysalcse.ezylib.databinding.ItemBookBinding;
import me.fsfaysalcse.ezylib.ui.model.Book;
import me.fsfaysalcse.ezylib.ui.utli.SharedPreferenceManager;

public class BookAdapter extends ListAdapter<Book, BookAdapter.BookViewHolder> {

    private final Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Book book);

        void onDeleteClick(Book book);
    }

    public BookAdapter(Context context, OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
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

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = getItem(position);
        holder.bind(book);
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
                binding.btnDelete.setVisibility(android.view.View.VISIBLE);
                binding.btnDelete.setOnClickListener(v -> listener.onDeleteClick(book));
            } else {
                binding.btnDelete.setVisibility(android.view.View.GONE);
            }
        }
    }
}
