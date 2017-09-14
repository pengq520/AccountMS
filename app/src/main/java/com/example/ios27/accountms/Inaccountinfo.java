package com.example.ios27.accountms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ios27.accountms.dao.InaccountDAO;
import com.example.ios27.accountms.model.Tb_inaccount;

import java.util.List;
import java.util.StringTokenizer;

public class Inaccountinfo extends AppCompatActivity {
    public static final String FLAG = "id";//定义一个常量，用来作为请求码
    ListView lvinfo;//创建ListView对象
    String strType="";//创建字符串，记录管理类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inaccountinfo);
        lvinfo = (ListView)findViewById(R.id.lvinaccountinfo);
        ShowInfo(R.id.btnininfo);

        lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strInfo = String.valueOf(((TextView)view).getText());//记录收入信息
                String strid = strInfo.substring(0,strInfo.indexOf("|"));//从收入信息中截取收入编号
                Intent intent = new Intent(Inaccountinfo.this,InfoManage.class);//创建Intent对象
                intent.putExtra(FLAG,new String[]{strid,strType});//设置传递数据
                startActivity(intent);//执行Intent操作
            }
        });
    }

    private void ShowInfo(int intType){//用来根据管理类型显示相应信息
        String[] strInfos = null;//定义字符串数组，用来存储收入信息
        ArrayAdapter<String>arrayAdapter = null;//创建Arraydapter对象
        strType = "btnininfo";//为strType赋值
        InaccountDAO inaccountinfo = new InaccountDAO(Inaccountinfo.this);
        List<Tb_inaccount>listinfos=inaccountinfo.getScrollData(0,(int)inaccountinfo.getCount());
        strInfos = new String[listinfos.size()];//设置字符串数组的长度
        int m=0;//定义一个开始标示
        for (Tb_inaccount tb_inaccount:listinfos){//遍历List泛型集合
            //将收入相关组合成一个字符串，存储到字符串数组的相应位置
            strInfos[m]=tb_inaccount.getid()+"|"+tb_inaccount.getType()+" "+
                    String.valueOf(tb_inaccount.getMoney())+"  元  "+tb_inaccount.getTime();
            m++;//表示加一
        }
        //使字符串数组初始化ArrayAdapter对象
        arrayAdapter = new ArrayAdapter<String>(R.layout.simple_list_item_1,strInfos);
        lvinfo.setAdapter(arrayAdapter);//为ListView列表设置数据源
    }
}
