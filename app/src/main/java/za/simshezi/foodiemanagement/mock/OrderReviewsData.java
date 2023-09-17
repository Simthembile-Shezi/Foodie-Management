package za.simshezi.foodiemanagement.mock;

import java.util.ArrayList;
import java.util.List;

import za.simshezi.foodiemanagement.model.OrderModel;
import za.simshezi.foodiemanagement.model.OrderReviewModel;

public class OrderReviewsData {
    public List<OrderReviewModel> getData() {
        List<OrderReviewModel> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(new OrderReviewModel("OrderNo" + i, "Customer " + i, "Card", "20/09/2023","Happy", 5 ,2 * i, 10 * i));
        }
        return list;
    }
}
