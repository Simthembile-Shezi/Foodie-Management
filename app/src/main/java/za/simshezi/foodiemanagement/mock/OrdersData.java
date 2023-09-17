package za.simshezi.foodiemanagement.mock;

import java.util.ArrayList;
import java.util.List;

import za.simshezi.foodiemanagement.model.OrderModel;

public class OrdersData {
    public List<OrderModel> getData() {
        List<OrderModel> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(new OrderModel("OrderNo" + i, "Customer " + i, "Card", "10:1" + i, 2 * i, 10 * i));
        }
        return list;
    }
}
