package za.simshezi.foodiemanagement.api;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import za.simshezi.foodiemanagement.model.ShopModel;

public class FirebaseAPI {
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private CollectionReference restaurantsCollection;
    private static FirebaseAPI firebase;

    private FirebaseAPI() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        restaurantsCollection = db.collection("restaurants");
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
        user.put("status", model.isStatus());
        user.put("rating", model.getRating());

        restaurantsCollection.add(user)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();
                    StorageReference imageRef = storageRef.child("restaurants_images/" + documentId + ".jpg");

                    UploadTask uploadTask = imageRef.putBytes(model.getImage());

                    uploadTask.addOnSuccessListener(taskSnapshot -> callback.onSuccess(true))
                            .addOnFailureListener(e -> {
                                callback.onSuccess(false); // Handle the failure
                            });
                })
                .addOnFailureListener(e -> {
                    callback.onSuccess(false); // Handle the failure
                });
    }

    public void editShop(ShopModel model, OnSuccessListener<Boolean> callback) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", model.getName());
        user.put("cellphone", model.getCellphone());
        user.put("status", model.isStatus());

        DocumentReference documentRef = restaurantsCollection.document(model.getId());
        documentRef.set(user, SetOptions.merge())
                .addOnSuccessListener(documentReference -> {
                    StorageReference imageRef = storageRef.child("restaurants_images/" + model.getId() + ".jpg");

                    UploadTask uploadTask = imageRef.putBytes(model.getImage());

                    uploadTask.addOnSuccessListener(taskSnapshot -> callback.onSuccess(true))
                            .addOnFailureListener(e -> {
                                callback.onSuccess(false); // Handle the failure
                            });
                })
                .addOnFailureListener(e -> {
                    callback.onSuccess(false); // Handle the failure
                });
    }

    public void getShop(String email, OnSuccessListener<DocumentSnapshot> callback) {
        Query query = restaurantsCollection.whereEqualTo("email", email);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    callback.onSuccess(querySnapshot.getDocuments().get(0));
                } else {
                    callback.onSuccess(null); // No matching documents found
                }
            } else {
                callback.onSuccess(null); // Handle the failure
            }
        });
    }

    public void getShopLogo(String documentId, OnSuccessListener<byte[]> callback) {
        StorageReference imageRef = storageRef.child("restaurants_images/" + documentId + ".jpg");

        long MAX_DOWNLOAD_SIZE = 1024 * 1024; // 1 MB

        imageRef.getBytes(MAX_DOWNLOAD_SIZE)
                .addOnSuccessListener(bytes -> callback.onSuccess(bytes))
                .addOnFailureListener(exception -> callback.onSuccess(null));
    }
}
