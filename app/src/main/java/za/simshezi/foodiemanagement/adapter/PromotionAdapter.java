package za.simshezi.foodiemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import za.simshezi.foodiemanagement.R;
import za.simshezi.foodiemanagement.api.JavaAPI;
import za.simshezi.foodiemanagement.model.PromotionModel;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {
    private List<PromotionModel> list;

    public PromotionAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public PromotionAdapter.PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discount_recycler_view, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.PromotionViewHolder holder, int position) {
        holder.setPromo(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(PromotionModel promotion) {
        list.add(promotion);
        notifyItemInserted(list.size() - 1);
    }

    public static class PromotionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPromoCode, tvPromAmount, tvDate;

        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPromoCode = itemView.findViewById(R.id.tvDiscountRecyclerCode);
            tvPromAmount = itemView.findViewById(R.id.tvDiscountRecyclerAmount);
            tvDate = itemView.findViewById(R.id.tvDiscountRecyclerDate);
        }

        public void setPromo(PromotionModel model) {
            double discount = model.getDiscount();
            String value = discount < 1 ? (discount * 100) + "%" : String.format("R %.2f", discount);
            tvPromoCode.setText(model.getPromoCode());
            tvPromAmount.setText(String.format("Spend R %.2f or more then get %s discount", model.getMinimum(), value));
            tvDate.setText(JavaAPI.getDate(model.getEnd()).concat(" to ")
                    .concat(JavaAPI.getDate(model.getStart())));
        }
    }
}
