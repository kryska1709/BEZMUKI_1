package com.example.xzxz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private List<Dish> dishList;
    private Context context;
    private DatabaseReference databaseReference;

    public CartItemAdapter(Context context, List<Dish> dishList, DatabaseReference databaseReference) {
        this.context = context;
        this.dishList = dishList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.textViewDishName.setText(dish.getName());
        holder.textViewDishPrice.setText(String.valueOf(dish.getPrice()));
        holder.buttonRemove.setOnClickListener(v -> removeDish(position, String.valueOf(dish.getId())));
    }


    @Override
    public int getItemCount() {
        return dishList.size();
    }

    private void removeDish(int position, String dishId) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(userId).child(dishId).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dishList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, dishList.size());
                        Toast.makeText(context, "Блюдо удалено из корзины", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Ошибка при удалении блюда", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDishName;
        TextView textViewDishPrice;
        Button buttonRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDishName = itemView.findViewById(R.id.textViewDishName);
            textViewDishPrice = itemView.findViewById(R.id.textViewDishPrice);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }
}
