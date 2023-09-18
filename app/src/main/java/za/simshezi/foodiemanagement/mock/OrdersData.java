package za.simshezi.foodiemanagement.mock;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

import za.simshezi.foodiemanagement.model.OrderModel;

public class OrdersData {
    public List<OrderModel> getData() {
        List<OrderModel> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(new OrderModel("ShopID", "Customer " + i, "Card", Timestamp.now(), 2 * i, 10.0 * i));
        }
        return list;
    }
}
