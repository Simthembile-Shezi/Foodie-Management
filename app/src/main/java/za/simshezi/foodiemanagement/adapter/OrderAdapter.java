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
import za.simshezi.foodiemanagement.model.OrderModel;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<OrderModel> list;
    private AdapterClickListener onClickListener;

    public OrderAdapter(List<OrderModel> list, AdapterClickListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_recycler_view, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel model = list.get(position);
        holder.setOrder(model);
        holder.itemView.setOnClickListener((view -> onClickListener.onClick(list.get(position))));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(OrderModel model) {
        list.add(model);
        notifyItemInserted(list.size() - 1);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void move(int fromPosition, int toPosition) {
        OrderModel model = list.get(fromPosition);
        list.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void edit(int position, OrderModel model) {
        list.add(position, model);
        notifyItemChanged(position);
    }

    public void filterList(ArrayList<OrderModel> filtered) {
        list = filtered;
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{
        private OrderModel model;
        private TextView tvName, tvPayment, tvPrice, tvTime, tvDate, tvStatus, tvItems;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCustomerName);
            tvPayment = itemView.findViewById(R.id.tvOrderPayment);
            tvPrice = itemView.findViewById(R.id.tvOrderPrice);
            tvTime = itemView.findViewById(R.id.tvOrderTime);
            tvItems = itemView.findViewById(R.id.tvOrderItems);
            tvStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvDate = itemView.findViewById(R.id.tvOrderDate);
        }

        public void setOrder(OrderModel model) {
            this.model = model;
            tvName.setText(model.getCustomer());
            tvTime.setText(String.format("%s", JavaAPI.getTime(model.getTime())));
            tvDate.setText(String.format("%s", JavaAPI.getDate(model.getTime())));
            tvPayment.setText(model.getPayment());
            tvStatus.setText(model.getStatus());
            tvItems.setText(String.format("%s items", model.getItems()));
            tvPrice.setText(String.format("R %s", JavaAPI.formatDouble(model.getPrice())));
        }
    }
}
