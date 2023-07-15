package me.arvin.ezylib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.arvin.ezylib.databinding.ItemReturnBinding;
import me.arvin.ezylib.ui.model.Returned;
public class ReturnsAdapter extends RecyclerView.Adapter<ReturnsAdapter.ReturnedViewHolder> {

    private final Context context;
    private final List<Returned> returnedList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Returned returned);
        void onAcceptClick(Returned returned);
    }

    public ReturnsAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.returnedList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReturnedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReturnBinding binding = ItemReturnBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ReturnedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnedViewHolder holder, int position) {
        Returned returned = returnedList.get(position);
        holder.bind(returned);
    }

    @Override
    public int getItemCount() {
        return returnedList.size();
    }

    public void setReturnedList(List<Returned> returnedList) {
        this.returnedList.clear();
        if (returnedList != null) {
            this.returnedList.addAll(returnedList);
        }
        notifyDataSetChanged();
    }


    public class ReturnedViewHolder extends RecyclerView.ViewHolder {
        private final ItemReturnBinding binding;

        public ReturnedViewHolder(ItemReturnBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Returned returned) {
            binding.tvBookTitle.setText("Name : " + returned.getBookTitle());
            binding.tvBookId.setText("Id : " + returned.getBookId());
            binding.tvReturnDate.setText("Return Submitted : " + returned.getReturnTimestamp());

            binding.btnApproved.setOnClickListener(v -> {
                listener.onAcceptClick(returned);
            });
        }
    }
}
