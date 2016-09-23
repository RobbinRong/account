package com.robbin.rong.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.robbin.rong.account.R;
import com.robbin.rong.account.domain.Artical;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class ArticleAdapter extends BaseAdapter {
    private List<Artical.Content> data;
    private Context context;
    private BitmapUtils bitmapUtils;

    public ArticleAdapter(List<Artical.Content> data, Context context) {
        this.data = data;
        this.context = context;
        this.bitmapUtils=new BitmapUtils(context);
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
        ViewHolder holder=null;
        if (convertView == null) {
            view= LayoutInflater.from(context).inflate(R.layout.item_article,parent,false);
            holder = new ViewHolder();
            holder.userName= (TextView) view.findViewById(R.id.userName);
            holder.title= (TextView) view.findViewById(R.id.title);
            holder.userLogo= (ImageView) view.findViewById(R.id.image);
            holder.contentImg= (ImageView) view.findViewById(R.id.contentImg);
            holder.date= (TextView) view.findViewById(R.id.desc);
            view.setTag(holder);
        }
            else {
            view=convertView;
            holder= (ViewHolder) view.getTag();

        }
            Artical.Content content=data.get(position);
            holder.userName.setText(content.userName);
            holder.title.setText(content.title);
            holder.date.setText(content.date);
            bitmapUtils.display( holder.contentImg,content.contentImg);
            bitmapUtils.display( holder.userLogo,content.userLogo);
        return view;
    }
    class ViewHolder {
        TextView userName,title,date;
        ImageView userLogo,contentImg;
    }
}
