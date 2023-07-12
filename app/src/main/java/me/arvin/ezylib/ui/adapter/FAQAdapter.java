package me.arvin.ezylib.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.arvin.ezylib.R;
import me.arvin.ezylib.ui.model.FAQItem;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQViewHolder> {
    private List<FAQItem> faqList;

    public FAQAdapter(List<FAQItem> faqList) {
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_faq, parent, false);
        return new FAQViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        FAQItem faqItem = faqList.get(position);
        holder.bind(faqItem);
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class FAQViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTextView;
        private TextView answerTextView;
        private ImageView expandImageView;

        public FAQViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerTextView = itemView.findViewById(R.id.answerTextView);
            expandImageView = itemView.findViewById(R.id.expandImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FAQItem faqItem = faqList.get(position);
                    faqItem.setExpanded(!faqItem.isExpanded());
                    notifyItemChanged(position);
                }
            });
        }

        public void bind(FAQItem faqItem) {
            questionTextView.setText(faqItem.getQuestion());
            answerTextView.setText(faqItem.getAnswer());

            if (faqItem.isExpanded()) {
                answerTextView.setVisibility(View.VISIBLE);
                expandImageView.setImageResource(R.drawable.ic_collapse);
            } else {
                answerTextView.setVisibility(View.GONE);
                expandImageView.setImageResource(R.drawable.ic_expand);
            }
        }
    }
}

