package com.example.xzxz.ui.pay;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xzxz.Dish;
import com.example.xzxz.R;
import com.example.xzxz.databinding.FragmentPayBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PayFragment extends Fragment {
    private FragmentPayBinding binding;
    private Button buttonClearOrder;
    private PayAdapter payAdapter;
    private List<Dish> dishList = new ArrayList<>();
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerViewCart;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        payAdapter = new PayAdapter(dishList, this);
        recyclerView.setAdapter(payAdapter);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("pays");
        buttonClearOrder = binding.getRoot().findViewById(R.id.button_clear_order);
        buttonClearOrder.setOnClickListener(v -> clearOrder()); // Установка слушателя на кнопку


        loadDishes();

        return root;
    }

    private void loadDishes() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dishList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dish dish = snapshot.getValue(Dish.class);
                    if (dish != null) {
                        dishList.add(dish);
                    }
                }
                payAdapter.notifyDataSetChanged(); // Update the adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PayFragment", "Error loading " + databaseError.getMessage());
            }
        });
    }


    private void clearOrder() {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference.child(userId).removeValue() // Удаление всех блюд текущего пользователя
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            dishList.clear(); // Очистка локального списка
                            payAdapter.notifyDataSetChanged(); // Обновление адаптера
                            Log.d("PayFragment", "Order cleared successfully.");
                            Toast.makeText(getContext(), "Корзина очищена", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("PayFragment", "Failed to clear order: " + task.getException().getMessage());
                            Toast.makeText(getContext(), "произошла ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
