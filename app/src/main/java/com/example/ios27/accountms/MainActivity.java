package com.example.ios27.accountms;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ios27.accountms.dao.InaccountDAO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    class pictureAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<Picture> pictures;
        //为类创建构造函数
        public  pictureAdapter(String[] titles, int[] images, Context context){
            super();
            pictures=new ArrayList<Picture>();
            inflater=LayoutInflater.from(context);
            for(int i=0;i<images.length;i++){
                Picture picture=new Picture(titles[i],images[i]);
                pictures.add(picture);
            }
        }
        public int getCount(){
            if(null!= pictures){
                return  pictures.size();
            }else {
                return 0;
            }
        }
        public Object getItem(int arg0){
            return pictures.get(arg0);
        }
        public long getItemId(int arg0){
            return arg0;
        }
        public  View getView(int arg0, View arg1, ViewGroup arg2){
            ViewHolder viewHolder;
            if(arg1==null){
                arg1=inflater.inflate(R.layout.gvitem,null);
                viewHolder=new ViewHolder();
                viewHolder.title=(TextView)arg1.findViewById(R.id.ItemTitle);
                viewHolder.image=(ImageView)arg1.findViewById(R.id.ItemImage);
                arg1.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)arg1.getTag();
            }
            viewHolder.title.setText(pictures.get(arg0).getTitle());
            viewHolder.image.setImageResource(pictures.get(arg0).getImageId());
            return  arg1;
        }

    }
    GridView gvInfo;//创建GridView对象
    String[] titles=new String[]{"新增支出","新增收入","我的支出","我的收入","数据管理","系统设置","收支便签","退出"
    };//定义字符串数组，储存系统功能的文本
    int[] images=new int[]{R.drawable.add_out_account,R.drawable.add_in_account,R.drawable.up,R.drawable.add,
    R.drawable.data_manage,R.drawable.shizhi,R.drawable.account_flag,R.drawable.exit};//定义int数组，存储功能对应的图标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gvInfo=(GridView)findViewById(R.id.gvInfo);
        pictureAdapter adapter=new pictureAdapter(titles,images,this);
        gvInfo.setAdapter(adapter);
        gvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =null;//创建Intent对象
                switch (i){
                    case 0:
                        //使用AddOutaccount窗口初始化Intent
                        intent=new Intent(MainActivity.this,OutInaccount.class);
                        startActivity(intent);//打开AddOutaccount
                        break;
                    case 1:
                        //使用AddInaccount窗口初始化Intent
                        intent=new Intent(MainActivity.this,AddInaccount.class);
                        startActivity(intent);//打开AddOutaccount
                        break;
                    case 2:
                        //使用Outaccountinfo窗口初始化Intent
                        intent=new Intent(MainActivity.this,Outaccountinfo.class);
                        startActivity(intent);//打开AddOutaccount
                        break;
                    case 3:
                        //使用Inaccountinfo窗口初始化Intent
                        intent=new Intent(MainActivity.this,Inaccountinfo.class);
                        startActivity(intent);//打开AddOutaccount
                        break;
                    case 4:
                        //使用Showinfo窗口初始化Intent
                        intent=new Intent(MainActivity.this,Showinfo.class);
                        startActivity(intent);//打开AddOutaccount
                        break;
                    case 5:
                        //使用Sysset窗口初始化Intent
                        intent=new Intent(MainActivity.this,Sysset.class);
                        startActivity(intent);//打开AddOutaccount
                        break;
                    case 6:
                        //使用Accountflag窗口初始化Intent
                        intent=new Intent(MainActivity.this,Accountflag.class);
                        startActivity(intent);//打开AddOutaccount
                        break;
                    case 7:
                        finish();//关闭当前Activity
                }
            }
        });

    }
    class ViewHolder{   //  创建ViewHolder类
        public TextView title;//创建TextView类
        public ImageView image;//创建ImageView类
    }
    class Picture{
        private String title;
        private int imageId;
        public Picture(){
            super();
        }
        public Picture(String title,int imageId){
            super();
            this.title=title;
            this.imageId=imageId;
        }
        public String getTitle(){
            return title;
        }
        public void setTitle(String title){
            this.title=title;
        }
        public int getImageId(){
            return imageId;
        }
        public void setimageId(int imageId){
            this.imageId=imageId;
        }
    }

}
