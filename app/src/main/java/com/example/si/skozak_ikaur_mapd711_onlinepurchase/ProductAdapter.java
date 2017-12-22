package com.example.si.skozak_ikaur_mapd711_onlinepurchase;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.ProductSchema;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductSchema> productDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImage;
        public TextView productTitle;
        public EditText productAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
            productTitle = (TextView) itemView.findViewById(R.id.product_name);
            productAmount = (EditText) itemView.findViewById(R.id.product_amount);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductAdapter(List<ProductSchema> myDataset) {
        productDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ViewHolder productViewHolder = new ViewHolder(v);
        return productViewHolder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.productImage.setImageResource(productDataset.get(position).imageID);
        holder.productTitle.setText(productDataset.get(position).productName);
        holder.productAmount.setHint(R.string.amount_hint);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productDataset.size();
    }
}

