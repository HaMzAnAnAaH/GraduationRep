package com.alaah.Road_Repair_System.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alaah.Road_Repair_System.R;
import com.alaah.Road_Repair_System.model.Product;
import com.alaah.Road_Repair_System.activities.probleminfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class productAdapter extends ArrayAdapter<Product>  {
    private List<Product> productList;
    private boolean flag = false ;
    Button but;
    //the context object
    private Context mCtx;

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
        TextView name = (TextView) convertView.findViewById(R.id.textView);
        TextView price = (TextView) convertView.findViewById(R.id.textView1);
        Button but = (Button) convertView.findViewById(R.id.button3);

         Glide.with(getContext())
               .load("http://10.3.147.201/store/images/"+productList.get(position).getImg())
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.notfound).into(img);

        name.setText(productList.get(position).getName());
        price.setText(productList.get(position).getAdd());
        but.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                Context context =v.getContext();
               Intent intent = new Intent(context, probleminfo.class);
               intent.putExtra("ProductId", productList.get(position).getID().intValue());
                context.startActivity(intent);
            }
        });



       // Glide.with(getContext())
               /* .load("http://10.0.2.2/store/project/"+productList.get(position).getImg())
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.notfound).into(img);*/

        return convertView;
    }


}
//http