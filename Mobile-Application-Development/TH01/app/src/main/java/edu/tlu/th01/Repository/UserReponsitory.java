package edu.tlu.th01.Repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import edu.tlu.th01.Models.User;

public class UserReponsitory {
    FirebaseFirestore db;

    public UserReponsitory() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(User user, Context context) {
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Đăng ký thành công (Firebase)!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Đăng ký thất bại (Firebase)!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void isUsernameExists(String username, Context context, OnSuccessListener<Boolean> successListener, OnFailureListener failureListener) {
        db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            successListener.onSuccess(true); // Username exists
                        } else {
                            successListener.onSuccess(false); // Username does not exist
                        }
                    }
                })
                .addOnFailureListener(failureListener);
    }

    public void deleteUser(User user, Context context) {
    }

    public void updateUser(User user, Context context) {
    }

    public void getUser(User user, Context context) {
    }

    public void getAllUser(Context context) {
    }
}
