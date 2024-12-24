package com.example.xzxz.ui.pay;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xzxz.Dish;
import com.example.xzxz.R;

import java.util.List;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayViewHolder> {

    private List<Dish> dishList;
    private PayFragment payFragment;

    public PayAdapter(List<Dish> dishList, PayFragment payFragment) {
        this.dishList = dishList;
        this.payFragment = payFragment;
    }

    @NonNull
    @Override
    public PayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay, parent, false);
        return new PayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.bind(dish, payFragment); // Use the bind method
    }

    @Override
    public int getItemCount() {
        return dishList != null ? dishList.size() : 0;
    }

    static class PayViewHolder extends RecyclerView.ViewHolder { //для хранения ссылок на элементы
        private TextView dishName;
        private TextView dishPrice;
        private Button buttonRemove;

        public PayViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dish_name);
            dishPrice = itemView.findViewById(R.id.dish_price);
        }

        public void bind(Dish dish, PayFragment payFragment) {
            dishName.setText(dish.getName());
            dishPrice.setText(String.valueOf(dish.getPrice()));
        }
    }
}
