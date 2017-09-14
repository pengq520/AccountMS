package com.example.ios27.accountms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ios27.accountms.dao.PwdDAO;

public class Login extends AppCompatActivity {

    private Button btnlogin;
    private EditText txtlogin;
    private Button btnclose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        txtlogin = (EditText)findViewById(R.id.txtLogin);
        btnlogin =(Button)findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Login.this,MainActivity.class);
                PwdDAO pwdDAO = new PwdDAO(Login.this);
                if((pwdDAO.getCount()==0| pwdDAO.find().getPassword().isEmpty())
        && txtlogin.getText().toString().isEmpty()){
                    startActivity(intent);
                }
                else {
                    if(pwdDAO.find().getPassword().equals(txtlogin.getText().toString())){
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Login.this,"请输入正确的密码!",Toast.LENGTH_SHORT).show();
                    }
                }
                txtlogin.setText("");
            }
        });

        btnclose = (Button)findViewById(R.id.btnClose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args) {
                finish();
            }
        });
    }
}
