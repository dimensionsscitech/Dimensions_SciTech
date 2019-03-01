package com.wd.information.activity.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.information.R;
import com.wd.information.activity.bean.FicationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pengbo
 * @date:2019/2/28 desc:兴趣分类的适配器
 */
public class FicationAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
    private Context mContext;
    private List<FicationBean.ResultBean>mjihe;

    public FicationAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<FicationBean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        //刷新
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.fication_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        FicationBean.ResultBean resultBean = mjihe.get(i);
        holder.mAdapterTitle.setText(resultBean.getName());
        Uri uri = Uri.parse(resultBean.getPic());
        holder.mAdapterSdv.setImageURI(uri);

    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }
    class ViewHolder extends XRecyclerView.ViewHolder {
        TextView mAdapterTitle;
        SimpleDraweeView mAdapterSdv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //初始化：
            mAdapterTitle = itemView.findViewById(R.id.fication_adapter_title);
            mAdapterSdv = itemView.findViewById(R.id.fication_adapter_sdv);

        }
    }
}
