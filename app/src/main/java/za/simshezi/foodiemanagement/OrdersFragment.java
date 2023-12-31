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
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import za.simshezi.foodiemanagement.adapter.OrderReviewAdapter;
import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.model.OrderModel;
import za.simshezi.foodiemanagement.model.ShopModel;

public class OrdersFragment extends Fragment {
    public static final int ORDER_REQ = 3;
    private RecyclerView lstPreviousOrders;
    private TextView tvNoOrderReview;
    private List<OrderModel> list;
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
        tvNoOrderReview = view.findViewById(R.id.tvNoOrderReview);
        build();
    }
    private void build() {
        Intent data = requireActivity().getIntent();
        ShopModel model = (ShopModel) data.getSerializableExtra("shop");
        if(model != null){
            list = new ArrayList<>();
            FirebaseAPI.getInstance().getOrdersReviews(model.getId(), querySnapshot ->{
                if(querySnapshot != null){
                    for(DocumentSnapshot document : querySnapshot){
                        OrderModel order = document.toObject(OrderModel.class);
                        if(order != null && order.getStatus().equals("Collected")) {
                            list.add(order);
                        }
                    }
                    list = list.stream().sorted((prev, next) -> next.getTime().compareTo(prev.getTime()))
                            .collect(Collectors.toList());
                    OrderReviewAdapter adapter = new OrderReviewAdapter(list);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    lstPreviousOrders.setAdapter(adapter);
                    lstPreviousOrders.setLayoutManager(layoutManager);

                    lstPreviousOrders.setVisibility(View.VISIBLE);
                    tvNoOrderReview.setVisibility(View.GONE);
                }else {
                    lstPreviousOrders.setVisibility(View.GONE);
                    tvNoOrderReview.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}