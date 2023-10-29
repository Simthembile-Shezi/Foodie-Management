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

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder>{
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

    public static class PromotionViewHolder extends RecyclerView.ViewHolder{
        private TextView tvPromoCode, tvStartDate, tvEndDate;
        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPromoCode = itemView.findViewById(R.id.tvDiscountRecyclerCode);
            tvStartDate = itemView.findViewById(R.id.tvDiscountRecyclerStart);
            tvEndDate = itemView.findViewById(R.id.tvDiscountRecyclerEnd);
        }

        public void setPromo(PromotionModel model) {
            tvPromoCode.setText(model.getPromoCode());
            tvStartDate.setText(JavaAPI.getDate(model.getStart()));
            tvEndDate.setText(JavaAPI.getDate(model.getEnd()));
        }
    }
}
