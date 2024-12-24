package com.example.xzxz.ui.Informs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xzxz.Inform;
import com.example.xzxz.R;
import com.example.xzxz.databinding.FragmentInformsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InformsFragment extends Fragment {

    private FragmentInformsBinding binding;
    private InformsAdapter informsAdapter;
    private List<Inform> informList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInformsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerInfo;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        informsAdapter = new InformsAdapter(informList);
        recyclerView.setAdapter(informsAdapter);

        loadInforms(); // Метод для загрузки данных из Firebase

        return root;
    }

    private void loadInforms() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("informs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                informList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Inform inform = snapshot.getValue(Inform.class);
                    if (inform != null) {
                        informList.add(inform);
                    }
                }
                informsAdapter.notifyDataSetChanged(); // Обновление адапт
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("InformsFragment", "Ошибка загрузки данных: " + databaseError.getMessage());
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
