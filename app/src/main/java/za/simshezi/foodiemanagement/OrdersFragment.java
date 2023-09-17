package za.simshezi.foodiemanagement;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import za.simshezi.foodiemanagement.adapter.OrderAdapter;
import za.simshezi.foodiemanagement.adapter.OrderReviewAdapter;
import za.simshezi.foodiemanagement.mock.OrderReviewsData;
import za.simshezi.foodiemanagement.mock.OrdersData;
import za.simshezi.foodiemanagement.model.OrderModel;
import za.simshezi.foodiemanagement.model.OrderReviewModel;

public class OrdersFragment extends Fragment {

    private RecyclerView lstPreviousOrders;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstPreviousOrders = view.findViewById(R.id.lstPreviousOrders);
        build();
    }
    private void build() {
        List<OrderReviewModel> list = new OrderReviewsData().getData();
        OrderReviewAdapter adapter = new OrderReviewAdapter(list, (view -> {

        }));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        lstPreviousOrders.setAdapter(adapter);
        lstPreviousOrders.setLayoutManager(layoutManager);
    }
}