package com.example.xzxz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList; // Добавлено
import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private List<Dish> dishList = new ArrayList<>(); // Инициализация списка
    private OnDishClickListener listener;

    public void updateDishes(List<Dish> dishList) {
        this.dishList = dishList;
        notifyDataSetChanged(); // Уведомляем адаптер об изменениях
    }

    public interface OnDishClickListener { // обработка нажатий на элементы списка
        void onDishClick(Dish dish);
    }

    public DishAdapter(List<Dish> dishList, OnDishClickListener listener) {
        this.dishList = dishList != null ? dishList : new ArrayList<>(); // Проверка на null
        this.listener = listener;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish item = dishList.get(position); // Используем dishList
        holder.bind(item, listener); // Передаем элемент и слушатель
    }

    @Override
    public int getItemCount() {
        return dishList.size(); // Теперь dishList всегда инициализирован
    }

    static class DishViewHolder extends RecyclerView.ViewHolder {//для хранения ссылок на элементы
        private TextView nameTextView;
        private ImageView imageView;
        private Button addToCartButton;

        public DishViewHolder(@NonNull View itemView) { //методы адаптера
            super(itemView);
            nameTextView = itemView.findViewById(R.id.dish_name);
            imageView = itemView.findViewById(R.id.dish_image);
            addToCartButton = itemView.findViewById(R.id.button_add_to_cart);
        }

        public void bind(Dish dish, OnDishClickListener listener) {
            nameTextView.setText(dish.getName());
            // Установка изображения в зависимости от названия блюда
            switch (dish.getName()) {
                case "лаваш с брынзой и зеленью":
                    imageView.setImageResource(R.drawable.brunza);
                    break;
                case "круассан с шоколадом":
                    imageView.setImageResource(R.drawable.kruass);
                    break;
                case "бриошь с чёрной смородиной":
                    imageView.setImageResource(R.drawable.briosh);
                    break;
                case "шоколадный маффин":
                    imageView.setImageResource(R.drawable.maf);
                    break;
                case "ирландский кофе":
                    imageView.setImageResource(R.drawable.irl_coffee);
                    break;
                case "матча латте голубая":
                    imageView.setImageResource(R.drawable.blu_matcha);
                    break;
                case "молочный клубничный коктейль":
                    imageView.setImageResource(R.drawable.kokt_klub);
                    break;
                case "лимонад клубника базилик":
                    imageView.setImageResource(R.drawable.lim_klubn_bazil);
                    break;
                case "чай малина-имбирь":
                    imageView.setImageResource(R.drawable.malina_imbir);
                    break;
                case "эспрессо тоник манго-маракуйя":
                    imageView.setImageResource(R.drawable.man_mar);
                    break;
                case "профитроли со сгущенкой":
                    imageView.setImageResource(R.drawable.profitrol);
                    break;
                case "яблоко":
                    imageView.setImageResource(R.drawable.apple);
                    break;
                case "красный бархат":
                    imageView.setImageResource(R.drawable.redbarzh);
                    break;
                case "безе кофейное":
                    imageView.setImageResource(R.drawable.beze);
                    break;
                case "тирамису":
                    imageView.setImageResource(R.drawable.tiramisu);
                    break;
            }

            itemView.setOnClickListener(v -> listener.onDishClick(dish));

        }
    }
}
