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
import za.simshezi.foodiemanagement.model.IngredientModel;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<IngredientModel> list;

    public RecipeAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_recycler_view, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        holder.setRecipeIngredients(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<IngredientModel> updated){
        list = updated;
        notifyDataSetChanged();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvMeasure;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvIngredientName);
            tvMeasure = itemView.findViewById(R.id.tvIngredientCount);
        }

        public void setRecipeIngredients(IngredientModel model) {
            tvName.setText(model.getName());
            tvMeasure.setText(model.getMeasure());
        }
    }
}
