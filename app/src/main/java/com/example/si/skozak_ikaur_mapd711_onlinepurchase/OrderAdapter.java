package com.example.si.skozak_ikaur_mapd711_onlinepurchase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.AppDatabase;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.OrderSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.ProductSchema;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


    private List<OrderSchema> orderDataset;
    private List<ProductSchema> productDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orderProductId;
        public TextView orderProductAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            orderProductId = (TextView) itemView.findViewById(R.id.order_product_id);
            orderProductAmount = (TextView) itemView.findViewById(R.id.order_product_amount);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderAdapter(List<OrderSchema> myDataset, List<ProductSchema> myProductDataset) {
        orderDataset = myDataset;
        productDataset = myProductDataset;
    }

    // Create new views (invoked by the layout manager)

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        OrderAdapter.ViewHolder orderViewHolder = new OrderAdapter.ViewHolder(v);
        return orderViewHolder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.orderProductId.setText(productDataset.get(position).productName);
//        holder.orderProductId.setText(orderDataset.get(position).product);
        holder.orderProductAmount.setText(String.valueOf(orderDataset.get(position).amount));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderDataset.size();
    }
}
