package com.example.store.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.store.R;
import com.example.store.model.Product;
import com.example.store.model.Report;

import java.util.List;

public class productAdapter extends ArrayAdapter<Product>  {

    private List<Product> productList;
    private boolean flag = false ;

    //the context object
    private Context mCtx;

    CardView cardView;
    public productAdapter(Context context, List<Product> productList){

        super(context,R.layout.list_item,productList);
        this.productList = productList;
        this.mCtx = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get products data at certain position using Product class
        Product product = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }


        ImageView img = (ImageView) convertView.findViewById(R.id.imageView2);
        TextView name = (TextView) convertView.findViewById(R.id.productName);
        TextView price = (TextView) convertView.findViewById(R.id.productPrice);
        TextView address = (TextView) convertView.findViewById(R.id.Address);
        TextView Time = (TextView) convertView.findViewById(R.id.time);
        TextView action = (TextView) convertView.findViewById(R.id.action);


        name.setText(productList.get(position).getName());
        price.setText(productList.get(position).getPrice());
        address.setText(productList.get(position).getAddress());
        Time.setText(productList.get(position).getTime());
        action.setText(productList.get(position).getAction());

        Glide.with(getContext())
                .load("http://192.168.178.154/store/images/"+productList.get(position).getImg())
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.notfound).into(img);

        return convertView;
    }
}
//http