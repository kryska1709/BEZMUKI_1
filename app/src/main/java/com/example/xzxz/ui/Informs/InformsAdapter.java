package com.example.xzxz.ui.Informs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xzxz.Inform;
import com.example.xzxz.R;

import java.util.List;

public class InformsAdapter extends RecyclerView.Adapter<InformsAdapter.InformViewHolder> {
    private List<Inform> informList;

    public InformsAdapter(List<Inform> informList) {
        this.informList = informList;
    }

    @NonNull
    @Override
    public InformViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inform, parent, false);
        return new InformViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformViewHolder holder, int position) {
        Inform inform = informList.get(position);
        holder.descriptionTextView.setText(inform.getDescription());
        holder.emailTextView.setText(inform.getEmail());
        holder.phoneTextView.setText(inform.getPhone());
        holder.addressTextView.setText(inform.getAddress());
    }

    @Override
    public int getItemCount() {
        return informList.size();
    }

    static class InformViewHolder extends RecyclerView.ViewHolder { //для хранения ссылок на элементы
        TextView descriptionTextView;
        TextView emailTextView;
        TextView phoneTextView;
        TextView addressTextView;

        public InformViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.inform_description);
            emailTextView = itemView.findViewById(R.id.inform_email);
            phoneTextView = itemView.findViewById(R.id.inform_phone);
            addressTextView = itemView.findViewById(R.id.inform_address);
        }
    }
}
