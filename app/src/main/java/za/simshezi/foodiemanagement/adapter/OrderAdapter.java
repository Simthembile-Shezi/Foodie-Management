package za.simshezi.foodiemanagement.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.simshezi.foodiemanagement.R;
import za.simshezi.foodiemanagement.model.OrderModel;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    public OrderModel model;
    private List<OrderModel> list;
    private View.OnClickListener onClickListener;

    public OrderAdapter(List<OrderModel> list, View.OnClickListener onClickListener) {
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
        holder.itemView.setOnClickListener((view -> {
            this.model = list.get(position);
            onClickListener.onClick(view);
        }));
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

    public static class OrderViewHolder extends RecyclerView.ViewHolder{
        private OrderModel model;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setOrder(OrderModel model) {
            this.model = model;
        }
    }
}
