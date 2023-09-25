package za.simshezi.foodiemanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import za.simshezi.foodiemanagement.adapter.OrderAdapter;
import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.model.OrderModel;
import za.simshezi.foodiemanagement.model.ShopModel;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{
    public static final int HOME_REQ = 0;
    private RecyclerView lstCurrentOrders;
    private ConstraintLayout layoutOrders, layoutNoOrders;
    private SearchView searchView;
    private FloatingActionButton btnFilter;
    private OrderAdapter adapter;
    private List<OrderModel> list;
    private FirebaseAPI api;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstCurrentOrders = view.findViewById(R.id.lstMainOrders);
        searchView = view.findViewById(R.id.searchViewHome);
        btnFilter = view.findViewById(R.id.btnHomeFilter);
        layoutOrders = view.findViewById(R.id.layoutHomeOrders);
        layoutNoOrders = view.findViewById(R.id.layoutNoOrders);
        build();
    }

    private void build() {
        Intent intent = requireActivity().getIntent();
        ShopModel shop = (ShopModel) intent.getSerializableExtra("shop");
        if(shop != null){
            list = new ArrayList<>();
            api = FirebaseAPI.getInstance();
            api.getOrders(shop.getId(), (DocumentSnapshot)->{
                if(DocumentSnapshot != null){
                    for (QueryDocumentSnapshot document : DocumentSnapshot){
                        OrderModel model = document.toObject(OrderModel.class);
                        model.setId(document.getId());
                        list.add(model);
                    }
                    if(DocumentSnapshot.size() == list.size()){
                        adapter = new OrderAdapter(list, (model -> {
                            OrderModel order = (OrderModel) model;
                            Intent send = new Intent(requireActivity(), ManageOrderActivity.class);
                            shop.setOrder(order);
                            send.putExtra("shop", shop);
                            startActivity(send);
                        }));
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        lstCurrentOrders.setAdapter(adapter);
                        lstCurrentOrders.setLayoutManager(layoutManager);

                        searchView.setOnQueryTextListener(HomeFragment.this);

                        layoutNoOrders.setVisibility(View.GONE);
                        layoutOrders.setVisibility(View.VISIBLE);
                    }
                }else {
                    layoutOrders.setVisibility(View.GONE);
                    layoutNoOrders.setVisibility(View.VISIBLE);
                }

            });
        }
    }

    private void filter(String text) {
        // creating a new array list to filter shops.
        ArrayList<OrderModel> filtered = new ArrayList<>();

        for (OrderModel item : list) {
            if (item.getCustomer().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(item);
            }
        }
        if (filtered.isEmpty()) {
            Toast.makeText(getContext(), "No order Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filtered);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        return false;
    }
}