package za.simshezi.foodiemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.simshezi.foodiemanagement.R;
import za.simshezi.foodiemanagement.model.OrderReviewModel;

public class OrderReviewAdapter extends RecyclerView.Adapter<OrderReviewAdapter.OrderReviewViewHolder> {
    public OrderReviewModel model;
    private List<OrderReviewModel> list;
    private View.OnClickListener onClickListener;

    public OrderReviewAdapter(List<OrderReviewModel> list, View.OnClickListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public OrderReviewAdapter.OrderReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_review_recycler_view, parent, false);
        return new OrderReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderReviewAdapter.OrderReviewViewHolder holder, int position) {
        OrderReviewModel model = list.get(position);
        holder.setOrderReview(model);
        holder.itemView.setOnClickListener((view -> {
            this.model = list.get(position);
            onClickListener.onClick(view);
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderReviewViewHolder extends RecyclerView.ViewHolder {
        private OrderReviewModel model;
        private TextView tvOrderNo, tvName, tvPayment, tvPrice, tvTime, tvItems, tvRating, tvReview;
        public OrderReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNo = itemView.findViewById(R.id.tvReviewOrderNumber);
            tvName = itemView.findViewById(R.id.tvReviewCustomerName);
            tvPayment = itemView.findViewById(R.id.tvReviewOrderPayment);
            tvPrice = itemView.findViewById(R.id.tvReviewOrderPrice);
            tvTime = itemView.findViewById(R.id.tvReviewOrderTime);
            tvItems = itemView.findViewById(R.id.tvReviewOrderItems);
            tvRating = itemView.findViewById(R.id.tvReviewOrderRating);
            tvReview = itemView.findViewById(R.id.tvReviewOrderDetails);
        }
        public void setOrderReview(OrderReviewModel model) {
            this.model = model;
            tvName.setText(model.getCustomerName());
            tvOrderNo.setText(model.getOrderId());
            tvTime.setText(model.getDate());
            tvPayment.setText(model.getPaymentType());
            tvReview.setText(model.getReview());
            tvRating.setText(String.format("%d", model.getRating()));
            tvItems.setText(String.format("%d items", model.getItems()));
            tvPrice.setText(String.format("R %s", model.getPrice()));
        }
    }
}
