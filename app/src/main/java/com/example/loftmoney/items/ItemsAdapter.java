package com.example.loftmoney.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftmoney.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MoneyViewHolder> {
    private ItemAdapterClick itemAdapterClick;
    private List<Item> itemList = new ArrayList<>();

    public void setData(List<Item> items){
        itemList = items;
        notifyDataSetChanged();
}

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.inflate(R.layout.item_money, parent, false);
        return new MoneyViewHolder(view, itemAdapterClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        MenuBuilder itemList;
        return itemList.size();
    }

    public class MoneyViewHolder {
        public void bind(Item item) {
        }
    }

static class MoneyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private ItemAdapterClick itemAdapterClick;

        public MoneyViewHolder(View view, ItemAdapterClick itemAdapterClick){
           super(view);
           this.itemAdapterClick = itemAdapterClick;
           this.name = view.findViewById(R.id.item_name);
           this.price = view.findViewById(R.id.item_price);

        }

        public void bind(Item item) {
            name.setText(item.getName());
            price.setText(item.getPrice());
        }
    }

}
