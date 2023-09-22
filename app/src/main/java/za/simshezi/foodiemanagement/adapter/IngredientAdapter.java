package za.simshezi.foodiemanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import za.simshezi.foodiemanagement.R;
import za.simshezi.foodiemanagement.api.JavaAPI;
import za.simshezi.foodiemanagement.model.IngredientModel;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    public static List<IngredientModel> ingredients;
    private static View.OnClickListener onClickListener;
    private static int position;

    public IngredientAdapter(List<IngredientModel> ingredients, View.OnClickListener onClickListener) {
        IngredientAdapter.ingredients = ingredients;
        IngredientAdapter.onClickListener = onClickListener;
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
        IngredientAdapter.position = position;
        IngredientModel ingredient = ingredients.get(position);
        holder.setIngredient(ingredient);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void add(IngredientModel ingredient) {
        ingredients.add(ingredient);
        notifyItemInserted(ingredients.size() - 1);
    }

    public void remove(int position) {
        ingredients.remove(position);
        notifyItemRemoved(position);
    }

    public void move(int fromPosition, int toPosition) {
        IngredientModel ingredient = ingredients.remove(fromPosition);
        ingredients.add(toPosition, ingredient);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void edit(int position, IngredientModel ingredient) {
        ingredients.add(position, ingredient);
        notifyItemChanged(position);
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private IngredientModel ingredient;
        private TextView tvName, tvPrice;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvIngredientName);
            tvPrice = itemView.findViewById(R.id.tvIngredientPrice);
        }

        public void setIngredient(IngredientModel ingredient) {
            this.ingredient = ingredient;
            tvName.setText(ingredient.getName());
            tvPrice.setText(String.format("R %s", JavaAPI.formatDouble(ingredient.getPrice())));
        }
    }
}
