package za.simshezi.foodiemanagement.api;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import za.simshezi.foodiemanagement.model.IngredientModel;
import za.simshezi.foodiemanagement.model.OrderModel;
import za.simshezi.foodiemanagement.model.ProductModel;
import za.simshezi.foodiemanagement.model.PromotionModel;
import za.simshezi.foodiemanagement.model.ShopModel;

public class FirebaseAPI {
    private final StorageReference storageRef;
    private final CollectionReference restaurantsCollection;
    private final CollectionReference ordersCollection;
    private final CollectionReference promotionsCollection;
    private final CollectionReference vouchersCollection;
    private static FirebaseAPI firebase;

    private FirebaseAPI() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        restaurantsCollection = db.collection("restaurants");
        ordersCollection = db.collection("orders");
        promotionsCollection = db.collection("promotions");
        vouchersCollection = db.collection("voucher");
    }

    public static FirebaseAPI getInstance() {
        if (firebase == null) {
            firebase = new FirebaseAPI();
        }
        return firebase;
    }

    public void saveShop(ShopModel model, OnSuccessListener<Boolean> callback) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", model.getName());
        user.put("cellphone", model.getCellphone());
        user.put("email", model.getEmail());
        user.put("address", model.getAddress());
        user.put("status", model.getStatus());
        user.put("days", model.getDays());
        user.put("times", model.getTimes());
        user.put("rating", 0.0);

        restaurantsCollection.add(user)
                .addOnSuccessListener(documentReference -> {
                    storageRef.child("restaurants_images/" + documentReference.getId() + ".jpg")
                            .putBytes(model.getImage())
                            .addOnSuccessListener(taskSnapshot -> callback.onSuccess(true))
                            .addOnFailureListener(e -> {
                                callback.onSuccess(false); // Handle the failure
                            });
                })
                .addOnFailureListener(e -> {
                    callback.onSuccess(false); // Handle the failure
                });
    }

    public void saveProduct(ProductModel model, OnSuccessListener<Boolean> callback) {
        Map<String, Object> product = new HashMap<>();
        product.put("name", model.getName());
        product.put("description", model.getDescription());
        product.put("price", model.getPrice());

        restaurantsCollection.document(model.getShopId()).collection("product").add(product)
                .addOnSuccessListener(documentReference -> {
                    storageRef.child("products_images/" + documentReference.getId() + ".jpg")
                            .putBytes(model.getImage())
                            .addOnSuccessListener(taskSnapshot -> callback.onSuccess(true))
                            .addOnFailureListener(e -> {
                                callback.onSuccess(false); // Handle the failure
                            });
                })
                .addOnFailureListener(e -> callback.onSuccess(false));
    }

    public void saveIngredient(ProductModel product, IngredientModel model, OnSuccessListener<Boolean> callback) {
        Map<String, Object> ingredient = new HashMap<>();
        ingredient.put("name", model.getName());
        ingredient.put("price", model.getPrice());
        restaurantsCollection.document(product.getShopId())
                .collection("product").document(product.getId())
                .collection("ingredient").document(model.getId()).set(ingredient, SetOptions.merge())
                .addOnSuccessListener(bool -> callback.onSuccess(true))
                .addOnFailureListener(e -> callback.onSuccess(false));
    }

    public void addPromotion(PromotionModel model, OnSuccessListener<Boolean> callback) {
        Map<String, Object> promotion = new HashMap<>();
        promotion.put("shopId", model.getShopId());
        promotion.put("shopName", model.getShopName());
        promotion.put("discount", model.getDiscount());
        promotion.put("minimum", model.getMinimum());
        promotion.put("start", model.getStart());
        promotion.put("end", model.getEnd());

        promotionsCollection.document(model.getPromoCode()).set(promotion)
                .addOnSuccessListener(runnable -> callback.onSuccess(true))
                .addOnFailureListener(e -> callback.onSuccess(false));
    }

    public void updateOrder(String orderId, String status, OnSuccessListener<Boolean> callback) {
        ordersCollection.document(orderId).update("status", status)
                .addOnSuccessListener(documentReference -> callback.onSuccess(true))
                .addOnFailureListener(e -> callback.onSuccess(false));
    }

    public void editShop(ShopModel model, OnSuccessListener<Boolean> callback) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", model.getName());
        user.put("cellphone", model.getCellphone());
        user.put("email", model.getEmail());
        user.put("address", model.getAddress());
        user.put("status", model.getStatus());
        user.put("rating", model.getRating());

        restaurantsCollection.document(model.getId()).set(user, SetOptions.merge())
                .addOnSuccessListener(documentReference -> {
                    storageRef.child("restaurants_images/" + model.getId() + ".jpg")
                            .putBytes(model.getImage())
                            .addOnSuccessListener(taskSnapshot -> callback.onSuccess(true))
                            .addOnFailureListener(e -> {
                                callback.onSuccess(false); // Handle the failure
                            });
                })
                .addOnFailureListener(e -> {
                    callback.onSuccess(false); // Handle the failure
                });
    }

    public void editProduct(ProductModel model, OnSuccessListener<Boolean> callback) {
        Map<String, Object> product = new HashMap<>();
        product.put("name", model.getName());
        product.put("description", model.getDescription());
        product.put("price", model.getPrice());

        restaurantsCollection.document(model.getShopId())
                .collection("product").document(model.getId()).set(product, SetOptions.merge())
                .addOnSuccessListener(runnable -> callback.onSuccess(true))
                .addOnFailureListener(e -> callback.onSuccess(false));
    }

    public void deleteIngredient(ProductModel product, IngredientModel model, OnSuccessListener<Boolean> callback) {
        restaurantsCollection.document(product.getShopId())
                .collection("product").document(product.getId())
                .collection("ingredient").document(model.getId()).delete()
                .addOnSuccessListener(bool -> callback.onSuccess(true))
                .addOnFailureListener(e -> callback.onSuccess(false));
    }

    public void getShopLogo(String documentId, OnSuccessListener<byte[]> callback) {
        StorageReference imageRef = storageRef.child("restaurants_images/" + documentId + ".jpg");

        long MAX_DOWNLOAD_SIZE = 1024 * 1024; // 1 MB

        imageRef.getBytes(MAX_DOWNLOAD_SIZE)
                .addOnSuccessListener(bytes -> callback.onSuccess(bytes))
                .addOnFailureListener(exception -> callback.onSuccess(null));
    }

    public void getProductImage(String documentId, OnSuccessListener<byte[]> callback) {
        StorageReference imageRef = storageRef.child("products_images/" + documentId + ".jpg");

        long MAX_DOWNLOAD_SIZE = 1024 * 1024; // 1 MB

        imageRef.getBytes(MAX_DOWNLOAD_SIZE)
                .addOnSuccessListener(bytes -> callback.onSuccess(bytes))
                .addOnFailureListener(exception -> callback.onSuccess(null));
    }

    public void getShop(String email, OnSuccessListener<QuerySnapshot> callback) {
        Query query = restaurantsCollection.whereEqualTo("email", email);
        executeQuery(query, callback);
    }

    public void getProducts(String id, OnSuccessListener<QuerySnapshot> callback) {
        Query query = restaurantsCollection.document(id).collection("product");
        executeQuery(query, callback);
    }

    public void getIngredients(String shopId, String productId, OnSuccessListener<QuerySnapshot> callback) {
        Query query = restaurantsCollection.document(shopId).collection("product").document(productId).collection("ingredient");
        executeQuery(query, callback);
    }

    public void getOrders(String shopID, OnSuccessListener<QuerySnapshot> callback) {
        Query query = ordersCollection.whereEqualTo("shopId", shopID);
        executeQuery(query, callback);
    }

    public void getOrdersReviews(String shopID, OnSuccessListener<QuerySnapshot> callback) {
        Query query = ordersCollection.whereEqualTo("shopId", shopID);
        executeQuery(query, callback);
    }

    public void getOrderProducts(String orderId, OnSuccessListener<QuerySnapshot> callback) {
        Query query = ordersCollection.document(orderId).collection("product");
        executeQuery(query, callback);
    }

    public void getOrderIngredients(String orderId, String productId, OnSuccessListener<QuerySnapshot> callback) {
        Query query = ordersCollection.document(orderId).collection("product")
                .document(productId).collection("ingredient");
        executeQuery(query, callback);
    }

    public void getPromotions(String id, OnSuccessListener<QuerySnapshot> callback) {
        Query query = promotionsCollection.whereEqualTo("shopId", id);
        executeQuery(query, callback);
    }

    private void executeQuery(Query query, OnSuccessListener<QuerySnapshot> callback) {
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    callback.onSuccess(querySnapshot);
                } else {
                    callback.onSuccess(null); // No matching documents found
                }
            } else {
                callback.onSuccess(null); // Handle the failure
            }
        });
    }

}
