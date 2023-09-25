package za.simshezi.foodiemanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import za.simshezi.foodiemanagement.adapter.ProductAdapter;
import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.model.ProductModel;
import za.simshezi.foodiemanagement.model.ShopModel;

public class MenuFragment extends Fragment {
    public static int REQ_MENU = 2;
    private Button btnAddProduct;
    private RecyclerView lstProduct;
    private ShopModel shop;
    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddProduct = view.findViewById(R.id.btnMenuNewItem);
        lstProduct = view.findViewById(R.id.lstMenuItems);
        build();
    }

    private void build() {
        Intent data = requireActivity().getIntent();
        shop = (ShopModel) data.getSerializableExtra("shop");
        if(shop != null) {
            ArrayList<ProductModel> list = new ArrayList<>();
            btnAddProduct.setOnClickListener(view -> {
                Intent intent = new Intent(requireContext(), AddProductActivity.class);
                intent.putExtra("shop", shop);
                startActivity(intent);
            });
            FirebaseAPI api = FirebaseAPI.getInstance();
            api.getProducts(shop.getId(), querySnapshot -> {
                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot) {
                        ProductModel product = document.toObject(ProductModel.class);
                        if (product != null) {
                            api.getProductImage(document.getId(), bytes -> {
                                if(bytes != null){
                                    product.setShopId(shop.getId());
                                    product.setId(document.getId());
                                    product.setImage(bytes);
                                    list.add(product);
                                    if(list.size() == querySnapshot.size()){
                                        update(list);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }
    private void update(ArrayList<ProductModel> list){
        ProductAdapter adapter = new ProductAdapter(list, model -> {
            ProductModel product = (ProductModel) model;
            shop.setProduct(product);
            Intent intent = new Intent(requireContext(), EditProductActivity.class);
            intent.putExtra("shop", shop);
            startActivity(intent);
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        lstProduct.setAdapter(adapter);
        lstProduct.setLayoutManager(layoutManager);
    }
}