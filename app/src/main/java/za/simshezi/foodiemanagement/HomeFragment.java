package za.simshezi.foodiemanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import za.simshezi.foodiemanagement.adapter.OrderAdapter;
import za.simshezi.foodiemanagement.mock.OrdersData;
import za.simshezi.foodiemanagement.model.OrderModel;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView lstCurrentOrders;
    private SearchView searchView;
    private FloatingActionButton btnFilter;
    private OrderAdapter adapter;
    private List<OrderModel> list;
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
        build();
    }

    private void build() {
        list = new OrdersData().getData();
        adapter = new OrderAdapter(list, (view -> {

        }));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        lstCurrentOrders.setAdapter(adapter);
        lstCurrentOrders.setLayoutManager(layoutManager);

        searchView.setOnQueryTextListener(this);
    }

    private void filter(String text) {
        // creating a new array list to filter shops.
        ArrayList<OrderModel> filtered = new ArrayList<>();

        for (OrderModel item : list) {
            if (item.getCustomerName().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(item);
            }else if (item.getOrderId().toLowerCase().contains(text.toLowerCase())) {
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