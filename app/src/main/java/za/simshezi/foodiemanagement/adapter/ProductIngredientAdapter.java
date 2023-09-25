package za.simshezi.foodiemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.simshezi.foodiemanagement.R;
import za.simshezi.foodiemanagement.model.IngredientModel;

public class ProductIngredientAdapter extends RecyclerView.Adapter<ProductIngredientAdapter.ProductIngredientViewHolder> {
    List<IngredientModel> list;
    private LayoutInflater inflater;
    public ProductIngredientAdapter(Context context, List<IngredientModel> ingredients) {
        list = ingredients;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ingredients_recycler_view, parent, false);
        return new ProductIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductIngredientViewHolder holder, int position) {
        IngredientModel model = list.get(position);
        holder.setIngredient(model);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductIngredientViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvItemCount;
        public ProductIngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvIngredientName);
            tvItemCount = itemView.findViewById(R.id.tvIngredientCount);
        }

        public void setIngredient(IngredientModel model) {
            if(model.getCount() > 0){
                tvName.setText(model.getName());
                tvItemCount.setText(String.format("%d", model.getCount()));
            }
        }
    }
}
