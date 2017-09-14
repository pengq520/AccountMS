package com.example.ios27.accountms;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ios27.accountms.dao.FlagDAO;
import com.example.ios27.accountms.dao.InaccountDAO;
import com.example.ios27.accountms.dao.OutaccountDAO;
import com.example.ios27.accountms.model.Tb_flag;
import com.example.ios27.accountms.model.Tb_inaccount;
import com.example.ios27.accountms.model.Tb_outaccount;

import java.util.List;

import static com.example.ios27.accountms.Inaccountinfo.FLAG;
import static com.example.ios27.accountms.R.id.btnflaginfo;


public class Showinfo extends AppCompatActivity {
    String strType;
    private ListView lvinfo;
    private Button btnflaginfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showinfo);

        lvinfo = (ListView)findViewById(R.id.lvinfo);
        btnflaginfo = (Button)findViewById(R.id.btnflaginfo);


}
    private void ShowInfo(int intType) {     //用来根据传入的管理类型显示相应信息
        String[] strInfos = null;              //定义字符串数组，用来存储输入信息
        ArrayAdapter<String> arrayAdapter = null;   //创建ArrayAdapter对象
        switch (intType) {                      //以inttype为条件进行判断
            case R.id.btnoutinfo:                  //如果是btnoutinfo按钮
                strType = "btnoutinfo";             //为strtype赋值
                OutaccountDAO outaccountinfo = new OutaccountDAO(Showinfo.this);//创建OutaccountDAO对象
                //获取所有支出信息，并存到List泛型集合中
                List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0, (int) outaccountinfo.getCount());
                strInfos = new String[listoutinfos.size()];   //设置字符串数组长度
                int i = 0;                                //定义一个开始标识
                for (Tb_outaccount tb_outaccount : listoutinfos) {     //遍历list泛型集合
                    //将支出的相关信息组合成一个字符串，存储到字符串数组的相应位置
                    strInfos[i] = tb_outaccount.getid() + "|" + tb_outaccount.getType() + " " + String.valueOf(tb_outaccount.getMoney()) + "元" + tb_outaccount.getTime();
                    i++;       //标识加一
                }
                break;
            case R.id.btnininfo:               //如果是btnininfo按钮
                strType = "btnininfo";           //为strType赋值
                InaccountDAO inaccountinfo = new InaccountDAO(Showinfo.this);  //创建InaccountDAO对象
                //获取所有收入信息，并存储到list泛型合集中
                List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0, (int) inaccountinfo.getCount());
                strInfos = new String[listinfos.size()];       //设置字符串数组的长度
                int m = 0;                                   //定义一个开始标识
                for (Tb_inaccount tb_inaccount : listinfos) {              //遍历list泛型集合
                    //将支出的相关信息组合成一个字符串，存储到字符串数组的相应位置
                    strInfos[m] = tb_inaccount.getid() + "|" + tb_inaccount.getType() + "   " + String.valueOf(tb_inaccount.getMoney()) + "元  " + tb_inaccount.getTime();
                    m++;
                }
                break;
            case R.id.btnflaginfo:
                strType="btnflaginfo";
                FlagDAO flaginfo=new FlagDAO(Showinfo.this);
                List<Tb_flag> listFlags=flaginfo.getScrollData(0,(int) flaginfo.getCount());
                strInfos=new String[listFlags.size()];
                int n=0;
                for (Tb_flag tb_flag:listFlags){
                    strInfos[n]=tb_flag.getid()+"|"+tb_flag.getFlag();
                    if (strInfos[n].length()>15)
                        strInfos[n]=strInfos[n].substring(0,15)+"....";
                    n++;
                }
                break;

        }
        //使用字符串数组初始化ArrayAdapter对象
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strInfos);
        lvinfo.setAdapter(arrayAdapter);

        btnflaginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowInfo(R.id.btnflaginfo);      //显示便签信息
            }
        });

        lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strInfo = String.valueOf(((TextView)view).getText());
                String strid= strInfo.substring(0,strInfo.indexOf('|'));
                Intent intent = null;
                if (strType=="btnoutinfo"|strType=="btnininfo"){
                    intent =new Intent(Showinfo.this,InfoManage.class);
                    intent.putExtra(FLAG,new String[]{strid,strType});
                }
                else if (strType=="btnflaginfo"){
                    intent=new Intent(Showinfo.this,FlagManage.class);
                    intent.putExtra(FLAG,strid);
                }
                startActivity(intent);
            }
        });
    }
}
