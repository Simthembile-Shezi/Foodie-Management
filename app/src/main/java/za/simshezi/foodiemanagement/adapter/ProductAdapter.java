package za.simshezi.foodiemanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import za.simshezi.foodiemanagement.R;
import za.simshezi.foodiemanagement.api.ImagesAPI;
import za.simshezi.foodiemanagement.api.JavaAPI;
import za.simshezi.foodiemanagement.model.ProductModel;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductModel> products;
    private AdapterClickListener listener;


    public ProductAdapter(AdapterClickListener listener) {
        this.products = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_recycler_view, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductModel product = products.get(position);
        holder.setProduct(product);
        holder.itemView.setOnClickListener(view -> listener.onClick(products.get(position)));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void add(ProductModel product) {
        products.add(product);
        notifyItemInserted(products.size() - 1);
    }

    public void edit(ProductModel product) {
        int position = products.indexOf(product);
        if (position > -1) {
            products.remove(product);
            products.add(position, product);
            notifyItemChanged(position);
        }
    }

    public ProductModel get(int position) {
        return products.get(position);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public ProductModel product;
        public TextView tvName, tvDescription, tvPrice;
        public ImageView imgProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRecyclerProductName);
            tvDescription = itemView.findViewById(R.id.tvRecyclerProductDescription);
            tvPrice = itemView.findViewById(R.id.tvRecyclerProductPrice);
            imgProduct = itemView.findViewById(R.id.imgRecyclerProduct);
        }

        public void setProduct(ProductModel product) {
            this.product = product;
            byte[] data = product.getImage();
            if (data != null) {
                imgProduct.setImageBitmap(ImagesAPI.convertToBitmap(data));
            } else
                imgProduct.setImageResource(R.drawable.baseline_restaurant_menu_24);
            tvName.setText(product.getName());
            tvDescription.setText(product.getDescription());
            tvPrice.setText(String.format("R %.2f", product.getPrice()));
        }
    }
}
