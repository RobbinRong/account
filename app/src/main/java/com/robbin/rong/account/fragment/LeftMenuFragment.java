package com.robbin.rong.account.fragment;

import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.robbin.rong.account.MainActivity;

import com.robbin.rong.account.R;
import com.robbin.rong.account.base.AccountPage;
import com.robbin.rong.account.domain.AccountCateory;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


public class LeftMenuFragment extends  BaseFragment {
    //private  static  final String TAG="LeftMenuFragment";
    @ViewInject(R.id.listView)
    private ListView lvList;
    private  MenuAdapter adapter;
    private ArrayList<AccountCateory.Alt> menuList=new ArrayList<>();
    private ArrayList<String> defultData=new ArrayList<>();
    private int currentpos;
    private MainActivity activity;


    @Override
    public View initViews() {
       View view=View.inflate(mActivity, R.layout.fragmentleftmenu, null);
        ViewUtils.inject(this,view);
        return view;
    }

    public void setData(AccountCateory data){
        Log.e("rjb",data.toString());
        menuList= data.showapi_res_body.allList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        activity = (MainActivity) getActivity();
        lvList.setItemChecked(0, true);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!menuList.isEmpty()){
                    currentpos = i;
                    adapter.notifyDataSetChanged();
                    setCurrentDetaiPager(i);
                    lvList.setItemChecked(i, true);
                }
                activity.mDrawerLayout.closeDrawer(GravityCompat.START);


            }
        });
        defultData.add("defultData1");
        defultData.add("defultData2");
        defultData.add("defultData3");
        defultData.add("defultData4");
        adapter=new MenuAdapter();
        lvList.setAdapter(adapter);
    }



    private void setCurrentDetaiPager(int i) {

        MainActivity mainUi= (MainActivity) mActivity;
        ContentFragment contentFragment=mainUi.getContentFragment();
        AccountPage accountPage = contentFragment.getNewsCenterPager();
        accountPage.setCurrentDetaiPager(i);


    }

    private class MenuAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return menuList.isEmpty()?defultData.size():menuList.size();
        }

        @Override
        public  Object getItem(int i) {
            return menuList.isEmpty()?defultData.get(i):menuList.get(i).name;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
           /* if(TextUtils.isEmpty(((AccountCateory.Alt)getItem(i)).name)){
                return new View(mActivity);
            }*/
            View view1=View.inflate(mActivity,R.layout.menulistitem,null);
            TextView textView= (TextView) view1.findViewById(R.id.textView);
            String text = null;
            if(menuList.isEmpty()){
                text=defultData.get(i);
            }
            else {

                text=menuList.get(i).name;
            }
           textView.setText(text);
            this.notifyDataSetChanged();
         /*   if(i==currentpos){
                textView.setEnabled(true);
                }
            else{
                textView.setEnabled(false);
            }*/
            return view1;
        }
    }
}
