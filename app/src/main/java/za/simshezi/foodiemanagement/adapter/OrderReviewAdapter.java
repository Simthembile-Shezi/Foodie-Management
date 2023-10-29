package za.simshezi.foodiemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.simshezi.foodiemanagement.R;
import za.simshezi.foodiemanagement.api.JavaAPI;
import za.simshezi.foodiemanagement.model.OrderModel;

public class OrderReviewAdapter extends RecyclerView.Adapter<OrderReviewAdapter.OrderReviewViewHolder> {
    private List<OrderModel> list;

    public OrderReviewAdapter(List<OrderModel> list) {
        this.list = list;
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
        OrderModel model = list.get(position);
        holder.setOrderReview(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderCellphoneNo, tvName, tvPayment, tvPrice, tvTime, tvItems, tvRating, tvReview;
        private ImageView imgStar;
        public OrderReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderCellphoneNo = itemView.findViewById(R.id.tvReviewOrderCellphone);
            tvName = itemView.findViewById(R.id.tvReviewCustomerName);
            tvPayment = itemView.findViewById(R.id.tvReviewOrderPayment);
            tvPrice = itemView.findViewById(R.id.tvReviewOrderPrice);
            tvTime = itemView.findViewById(R.id.tvReviewOrderTime);
            tvItems = itemView.findViewById(R.id.tvReviewOrderItems);
            tvRating = itemView.findViewById(R.id.tvReviewOrderRating);
            tvReview = itemView.findViewById(R.id.tvReviewOrderDetails);
            imgStar = itemView.findViewById(R.id.imgStar);
        }
        public void setOrderReview(OrderModel model) {
            tvOrderCellphoneNo.setText(model.getCellphone());
            tvName.setText(model.getCustomer());
            tvTime.setText(JavaAPI.getDate(model.getTime()));
            tvPayment.setText(model.getPayment());
            tvItems.setText(String.format("%s items", model.getItems()));
            tvPrice.setText(String.format("R %.2f", model.getPrice()));
            if(model.getReview() == null){
                tvReview.setVisibility(View.GONE);
                imgStar.setVisibility(View.GONE);
            }else {
                tvReview.setText(model.getReview());
            }
            if(model.getRating() == null){
                tvRating.setVisibility(View.GONE);
            }else {
                tvRating.setText(String.format("%s", model.getRating()));
            }
        }
    }
}
