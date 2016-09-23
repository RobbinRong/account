package com.robbin.rong.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.robbin.rong.account.R;
import com.robbin.rong.account.domain.Account;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class AccountAdapter extends BaseAdapter{
    private List<Account.Content> data;
    private Context context;
    private BitmapUtils bitmapUtils;
    public AccountAdapter(List<Account.Content> data, Context context) {
        this.data = data;
        this.context = context;
        bitmapUtils=new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view= LayoutInflater.from(context).inflate(R.layout.item_account,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.pub_num= (TextView) view.findViewById(R.id.pub_num);
            viewHolder.wei_num= (TextView) view.findViewById(R.id.wei_num);
            viewHolder.code_2_img= (ImageView) view.findViewById(R.id.code_2_img);
            viewHolder.user_logo= (ImageView) view.findViewById(R.id.user_logo);
            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();

        }
        Account.Content account=data.get(position);
        viewHolder.pub_num.setText(account.pubNum);
        viewHolder.wei_num.setText(account.weiNum);

        if (!account.userLogo.equals(viewHolder.user_logo.getTag())) {
            bitmapUtils.display( viewHolder.user_logo,account.userLogo);
            viewHolder.user_logo.setTag(account.userLogo);

        }

        if (!account.code2img.equals(viewHolder.code_2_img.getTag())) {
            bitmapUtils.display( viewHolder.code_2_img,account.code2img);
            viewHolder.code_2_img.setTag(account.code2img);

        }
        return view;
    }
    class ViewHolder {
        TextView pub_num,wei_num;
        ImageView code_2_img,user_logo;
    }
}
