package com.example.myworkone4.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.example.myworkone4.R;
import com.example.myworkone4.bean.ShoppingCart;
import com.example.myworkone4.bean.Wares;
import com.example.myworkone4.utils.CartProvider;
import com.example.myworkone4.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;




public class HWAdatper extends SimpleAdapter<Wares> {


    CartProvider provider ;

    public HWAdatper(Context context, List<Wares> datas) {
        super(context, R.layout.template_hot_wares, datas);

        provider = new CartProvider(context);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final Wares wares) {
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        viewHolder.getTextView(R.id.text_title).setText(wares.getName());
        viewHolder.getTextView(R.id.text_price).setText("￥ "+wares.getPrice());

        Button button =viewHolder.getButton(R.id.btn_add);
        if(button !=null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    provider.put(convertData(wares));

                    ToastUtils.show(context, "已添加到购物车");
                }
            });
        }

    }


    public ShoppingCart convertData(Wares item){

        ShoppingCart cart = new ShoppingCart();

        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());

        return cart;
    }


    public void  resetLayout(int layoutId){


        this.layoutResId  = layoutId;

        notifyItemRangeChanged(0,getDatas().size());


    }



}
