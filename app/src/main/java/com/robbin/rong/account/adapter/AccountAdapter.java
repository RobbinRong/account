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
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_account,parent,false);
            ViewHolder holder = new ViewHolder();
            holder.pub_num= (TextView) convertView.findViewById(R.id.pub_num);
            holder.wei_num= (TextView) convertView.findViewById(R.id.wei_num);
            holder.code_2_img= (ImageView) convertView.findViewById(R.id.code_2_img);
            holder.user_logo= (ImageView) convertView.findViewById(R.id.user_logo);
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Account.Content account=data.get(position);


        holder.pub_num.setText(account.pubNum);
        holder.wei_num.setText(account.weiNum);

        if (!account.userLogo.equals(holder.user_logo.getTag())) {
            bitmapUtils.display( holder.user_logo,account.userLogo);
            holder.user_logo.setTag(account.userLogo);

        }

        if (!account.code2img.equals(holder.code_2_img.getTag())) {
            bitmapUtils.display( holder.code_2_img,account.code2img);
            holder.code_2_img.setTag(account.code2img);

        }
        return convertView;
    }
    class ViewHolder {
        TextView pub_num,wei_num;
        ImageView code_2_img,user_logo;
    }
}
