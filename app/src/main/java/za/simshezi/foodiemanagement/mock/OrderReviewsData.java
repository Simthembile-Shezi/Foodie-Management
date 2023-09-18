package za.simshezi.foodiemanagement.mock;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

import za.simshezi.foodiemanagement.model.OrderModel;
import za.simshezi.foodiemanagement.model.OrderReviewModel;

public class OrderReviewsData {
    public List<OrderReviewModel> getData() {
        List<OrderReviewModel> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(new OrderReviewModel("shop" + i, "Customer " + i, "Card", Timestamp.now(),"Happy", 5 ,2 * i, 10.0 * i));
        }
        return list;
    }
}
