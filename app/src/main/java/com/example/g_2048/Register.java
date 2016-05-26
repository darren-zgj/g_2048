package com.example.g_2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class Register extends Activity{
	private EditText edname1;
	private EditText edpassword1;
	private Button btregister1;
	SQLiteDatabase db;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		edname1 = (EditText) findViewById(R.id.edname1);
		edpassword1 = (EditText) findViewById(R.id.edpassword1);
		btregister1 = (Button) findViewById(R.id.btregister1);
		btregister1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = edname1.getText().toString();
				String password = edpassword1.getText().toString();
				
				
				
				if (!(name.equals("") && password.equals(""))) {
					if (addUser(name, password)) {
						DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// 跳转到登录界面
								Intent in = new Intent();
								in.setClass(Register.this,
										Login.class);
								startActivity(in);
								// 销毁当前activity
								Register.this.onDestroy();
							}
						};
						new AlertDialog.Builder(Register.this)
								.setTitle("注册成功").setMessage("注册成功")
								.setPositiveButton("确定", ss).show();

					} else {
						new AlertDialog.Builder(Register.this)
								.setTitle("注册失败").setMessage("注册失败")
								.setPositiveButton("确定", null);
					}
				} else {
					new AlertDialog.Builder(Register.this)
							.setTitle("帐号密码不能为空").setMessage("帐号密码不能为空")
							.setPositiveButton("确定", null);
				}

			}
		});

	}

	// 添加用户
	public Boolean addUser(String name, String password) {
		String str = "insert into tb_user values(?,?) ";
		Login main = new Login();
		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
				+ "/test.dbs", null);
		main.db = db;
		try {
			db.execSQL(str, new String[] { name, password });
			return true;
		} catch (Exception e) {
			main.createDb();
		}
		return false;
	}
}
