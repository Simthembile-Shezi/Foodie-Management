package za.simshezi.foodiemanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.simshezi.foodiemanagement.R;
import za.simshezi.foodiemanagement.api.JavaAPI;
import za.simshezi.foodiemanagement.model.IngredientModel;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<IngredientModel> ingredients;
    private AdapterClickListener onClickListener;

    public IngredientAdapter(List<IngredientModel> ingredients, AdapterClickListener onClickListener) {
        this.ingredients = ingredients;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_recycler_view, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, @SuppressLint("RecyclerView") int position) {
        IngredientModel ingredient = ingredients.get(position);
        holder.setIngredient(ingredient);
        holder.itemView.setOnClickListener(view -> onClickListener.onClick(ingredients.get(position)));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void add(IngredientModel ingredient) {
        int pos = ingredients.indexOf(ingredient);
        if(pos >= 0){
            ingredients.remove(pos);
            notifyItemRemoved(pos);
            ingredients.add(pos,ingredient);
            notifyItemInserted(pos);
        }else {
            ingredients.add(ingredient);
            notifyItemInserted(ingredients.size() - 1);
        }
    }

    public void remove(IngredientModel ingredient) {
        int pos = ingredients.indexOf(ingredient);
        ingredients.remove(pos);
        notifyItemRemoved(pos);
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private IngredientModel ingredient;
        private TextView tvName, tvPrice;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvIngredientName);
            tvPrice = itemView.findViewById(R.id.tvIngredientCount);
        }

        public void setIngredient(IngredientModel ingredient) {
            this.ingredient = ingredient;
            tvName.setText(ingredient.getName());
            tvPrice.setText(String.format("R %.2f", ingredient.getPrice()));
        }
    }
}
