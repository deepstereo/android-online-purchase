package com.example.si.skozak_ikaur_mapd711_onlinepurchase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.OrderSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.ProductSchema;

import java.util.List;
import java.util.Objects;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {


    private List<OrderSchema> orderListDataset;
    private List<ProductSchema> productListDataset;


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
    public OrderListAdapter(List<OrderSchema> myDataset, List<ProductSchema> myProductDataset) {
        orderListDataset = myDataset;
        productListDataset = myProductDataset;
    }

    // Create new views (invoked by the layout manager)

    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        OrderListAdapter.ViewHolder orderListViewHolder = new OrderListAdapter.ViewHolder(v);
        return orderListViewHolder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OrderListAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.orderProductAmount.setText(String.valueOf(orderListDataset.get(position).amount));
        // holder.orderProductId.setText(productListDataset.get(position).productName);
        String productId = orderListDataset.get(position).product;

        for (ProductSchema product : productListDataset) {
            if (Objects.equals(product.productID, productId)) {
                holder.orderProductId.setText(product.productName);
            }
        }




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderListDataset.size();
    }
}
