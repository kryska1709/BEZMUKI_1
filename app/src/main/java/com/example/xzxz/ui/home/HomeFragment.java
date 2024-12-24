package com.example.xzxz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xzxz.R;
import com.example.xzxz.databinding.FragmentHomeBinding;
import com.example.xzxz.Dish; // Импортируйте Вашу модель Dish
import com.example.xzxz.DishAdapter; // Импортируйте адаптер
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DishAdapter dishAdapter; // Адаптер для RecyclerView
    private HomeViewModel homeViewModel; // Объявляем ViewModel

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dishAdapter = new DishAdapter(new ArrayList<>(), this::onDishClick);
        recyclerView.setAdapter(dishAdapter);

        // Наблюдаем за изменениями в списке блюд
        homeViewModel.getDishList().observe(getViewLifecycleOwner(), dishes -> {
            dishAdapter.updateDishes(dishes);
        });

        loadDishes(); // Метод для загрузки данных из Firebase
        return root;
    }

    private void onDishClick(Dish dish) {
        // Открытие нового фрагмента с деталями блюда
        Bundle bundle = new Bundle();
        bundle.putSerializable("dish", dish);
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_navigation_dishDetail, bundle);
    }

    private void loadDishes() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("menu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Dish> dishList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dish dish = snapshot.getValue(Dish.class);
                    if (dish != null) {
                        dishList.add(dish);
                    }
                }
                // Обновляем ViewModel
                homeViewModel.setDishList(dishList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
