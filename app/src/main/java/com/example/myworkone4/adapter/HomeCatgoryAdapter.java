package com.example.myworkone4.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myworkone4.bean.HomeCampaign;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myworkone4.R;
import com.squareup.picasso.Picasso;

import java.util.List;
public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder>{


    private  static int VIEW_TYPE_L=0;
    private  static int VIEW_TYPE_R=1;
    private LayoutInflater mInflater;
    private List<HomeCampaign> mDatas;
    private Context mContext;

    public HomeCatgoryAdapter(List<HomeCampaign> datas, Context context){
        mDatas = datas;
        this.mContext = context;
    }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

            mInflater = LayoutInflater.from(viewGroup.getContext());
            if(type == VIEW_TYPE_R){
                return  new ViewHolder(mInflater.inflate(R.layout.template_home_cardview2,viewGroup,false));
            }
            return  new ViewHolder(mInflater.inflate(R.layout.template_home_cardview,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {

            HomeCampaign homeCampaign = mDatas.get(i);
            viewHolder.textTitle.setText(homeCampaign.getTitle());
            Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(viewHolder.imageViewBig);
            Picasso.with(mContext).load(homeCampaign.getCpTwo().getImgUrl()).into(viewHolder.imageViewSmallTop);
            Picasso.with(mContext).load(homeCampaign.getCpThree().getImgUrl()).into(viewHolder.imageViewSmallBottom);
        }
        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        @Override
        public int getItemViewType(int position) {
            if(position % 2==0){
                return  VIEW_TYPE_R;
            }
            else return VIEW_TYPE_L;
        }

        static  class ViewHolder extends RecyclerView.ViewHolder{
            TextView textTitle;
            ImageView imageViewBig;
            ImageView imageViewSmallTop;
            ImageView imageViewSmallBottom;
            public ViewHolder(View itemView) {
                super(itemView);
                textTitle = (TextView) itemView.findViewById(R.id.text_title);
                imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
                imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
                imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);
            }
        }
}
