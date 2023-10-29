package za.simshezi.foodiemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.simshezi.foodiemanagement.R;
import za.simshezi.foodiemanagement.api.JavaAPI;
import za.simshezi.foodiemanagement.model.ProductModel;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ProductViewHolder>{
    private List<ProductModel> list;
    private static Context context;

    public OrderProductAdapter(Context context, List<ProductModel> list) {
        this.list = list;
        OrderProductAdapter.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cart_recycler_view, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.setProduct(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvProductPrice;
        RecyclerView listView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvCartProductName);
            tvProductPrice = itemView.findViewById(R.id.tvCartProductPrice);
            listView = itemView.findViewById(R.id.lstCartProductIngredients);
        }

        public void setProduct(ProductModel model) {
            ProductIngredientAdapter adapter = new ProductIngredientAdapter(context ,model.getIngredients());
            tvProductName.setText(model.getName());
            tvProductPrice.setText(String.format("R %.2f", model.getPrice()));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            listView.setAdapter(adapter);
            listView.setLayoutManager(layoutManager);
        }
    }
}
