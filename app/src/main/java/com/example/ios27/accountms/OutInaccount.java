package com.example.ios27.accountms;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import java.time.Year;
import com.example.ios27.accountms.dao.InaccountDAO;
import com.example.ios27.accountms.dao.OutaccountDAO;
import com.example.ios27.accountms.model.Tb_inaccount;
import com.example.ios27.accountms.model.Tb_outaccount;

import java.util.Calendar;

import static android.R.attr.id;
import static android.R.attr.publicKey;

public class OutInaccount extends AppCompatActivity {

    protected static final int DATE_DIALOG_ID = 0;//建立日期对话框常量
    EditText txtInMoney,txtInTime,txtInHandler,txtInMark;//建立四个EditText对象
    Spinner spInType;//创建Spinner对象
    Button btnInSaveButton;//创建Button对象"保存"
    Button btnInCancelButton;//创建Button对象"取消"
    private int mYear;//年
    private int mMonth;//月
    private int mDay;//日


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinaccount);
        txtInMoney = (EditText) findViewById(R.id.txtlnMoney);//获取金额文本框
        txtInTime = (EditText) findViewById(R.id.txtlnTime);//获取时间文本框
        txtInHandler = (EditText) findViewById(R.id.txtlnHandler);//获取付款方文本框
        txtInMark = (EditText) findViewById(R.id.txtlnMark);//获取备注文本框
        spInType = (Spinner) findViewById(R.id.splnType);//获取类别下拉列表
        btnInCancelButton = (Button) findViewById(R.id.btnlnSave);//获取保存按钮
        btnInSaveButton = (Button) findViewById(R.id.btnlnCancel);//获取取消按钮

        txtInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
        btnInSaveButton.setOnClickListener(new View.OnClickListener() {//为保存按钮设置监听事件
            @Override
            public void onClick(View view) {
                String strInMoney = txtInMoney.getText().toString();//获取金额文本框的值
                if (!strInMoney.isEmpty()){//判断金额不为空
                    //创建InaccountDAO对象
                    OutaccountDAO outaccountDAO = new OutaccountDAO(OutInaccount.this);
                    Tb_outaccount tb_outaccount = new Tb_outaccount(outaccountDAO.getMaxId()+1,
                            Double.parseDouble(strInMoney),txtInTime.getText().toString(),
                            spInType.getSelectedItem().toString(),txtInHandler.getText().toString(),
                            txtInMark.getText().toString());//创建tb_inaccount对象
                    outaccountDAO.add(tb_outaccount);//添加收入信息
                    //弹出信息提示
                    Toast.makeText(OutInaccount.this,"【新增收入】数据添加成功!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(OutInaccount.this,"请输入收入金额!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnInCancelButton.setOnClickListener(new View.OnClickListener() {//为取消按钮设置监听事件
            @Override
            public void onClick(View view) {
                txtInMoney.setText("");//设置金额文本框为空
                txtInMoney.setHint("0.00");//设置金额文本框提示
                txtInTime.setText("");//设时间文本框为空
                txtInTime.setHint("2011-01-01");//为时间文本框设置提示
                txtInHandler.setText("");//设置付款方文本框为空
                txtInMark.setText("");//设置备注文本框为空
                spInType.setSelection(0);//设置类别下拉列表默认选择第一项
            }
        });
    }

    private void updateDisplay() {
        txtInTime.setText(new StringBuilder().append(mYear).append("-").append
                (mMonth + 1).append("-").append(mDay));
    }//显示设置的时间

    @Override
    protected Dialog onCreateDialog(int id)//重写onCreateDialog（）方法
    {
        switch (id) {
            case DATE_DIALOG_ID://弹出时间选择对话框
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;//为年份赋值
            mMonth = monthOfYear;//为月份赋值
            mDay = dayOfMonth;//为天赋值
            updateDisplay();//显示设置的日期
        }
    };

}

