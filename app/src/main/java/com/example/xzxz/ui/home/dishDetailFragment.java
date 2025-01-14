package com.example.xzxz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.xzxz.R;
import com.example.xzxz.Dish;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dishDetailFragment extends Fragment {

    private Dish dish;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_detail, container, false);
        dish = (Dish) getArguments().getSerializable("dish");

        ImageView imageView = view.findViewById(R.id.dish_image);
        TextView nameTextView = view.findViewById(R.id.dish_name);
        TextView descriptionTextView = view.findViewById(R.id.dish_ingredients);
        TextView priceTextView = view.findViewById(R.id.dish_price);
        Button addToCartButton = view.findViewById(R.id.button_add_to_cart);

        // Установите данные
        nameTextView.setText(dish.getName());
        descriptionTextView.setText(dish.getDescription());
        priceTextView.setText(dish.getPrice());

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

        // Инициализация Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("pays");

        addToCartButton.setOnClickListener(v -> addDishToCart(dish));

        return view;
    }
        private void addDishToCart(Dish dish) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                String dishId = databaseReference.push().getKey(); // Генерация уникального ключа для блюда
                databaseReference.child(userId).child(dishId).setValue(dish)
                        .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Блюдо добавлено в корзину", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(getContext(), "Ошибка при добавлении блюда", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(getContext(), "Пользователь не авторизован", Toast.LENGTH_SHORT).show();
            }
        }
    }
