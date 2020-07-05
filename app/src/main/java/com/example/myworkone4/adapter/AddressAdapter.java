package com.example.myworkone4.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.myworkone4.R;
import com.example.myworkone4.bean.Address;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

public class AddressAdapter extends SimpleAdapter<Address> {
    private List<Address> mAddress;

    @ViewInject(R.id.txt_name)
    TextView mName;

    @ViewInject(R.id.txt_phone)
    TextView mPhone;

    @ViewInject(R.id.txt_address)
    TextView mAddr;


    public AddressAdapter(Context context,List<Address> datas) {
        super(context,R.layout.template_address,datas);
        mAddress=datas;
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Address item) {
        viewHoder.getTextView(R.id.txt_name).setText(item.getConsignee());
        viewHoder.getTextView(R.id.txt_phone).setText(item.getPhone());
        viewHoder.getTextView(R.id.txt_address).setText(item.getAddr());
    }

    private void show(){

    }


    /*@Override
    protected void convert(BaseViewHolder viewHoder, final Address item) {

        viewHoder.getTextView(R.id.txt_name).setText(item.getConsignee());
        viewHoder.getTextView(R.id.txt_phone).setText(item.getPhone());
        viewHoder.getTextView(R.id.txt_address).setText(item.getAddr());*/

        //final CheckBox checkBox = viewHoder.getCheckBox(R.id.cb_is_defualt);

       // final boolean isDefault = item.getIsDefault();
        //checkBox.setChecked(isDefault);





    /*public String replacePhoneNum(String phone){//设置电话号码中间四位为****

        return phone.substring(0,phone.length()-(phone.substring(3)).length())+"****"+phone.substring(7);
    }


   public interface AddressLisneter{

        public void setDefault(Address address);
    }*/



}
