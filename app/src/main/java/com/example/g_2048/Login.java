package com.example.g_2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity{
	// 帐号和密码
		private EditText edname;
		private EditText edpassword;

		private Button btregister;
		private Button btlogin;
		// 创建SQLite数据库
		public static SQLiteDatabase db;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login);
			edname = (EditText) findViewById(R.id.edname);
			edpassword = (EditText) findViewById(R.id.edpassword);
			btregister = (Button) findViewById(R.id.btregister);
			btlogin = (Button) findViewById(R.id.btlogin);
			db = SQLiteDatabase.openOrCreateDatabase(Login.this.getFilesDir().toString()
					+ "/test.dbs", null);
			// 跳转到注册界面
			btregister.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(Login.this, Register.class);
					startActivity(intent);
				}
			});
			btlogin.setOnClickListener(new LoginListener());
		}

		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			db.close();
		}


		class LoginListener implements OnClickListener {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = edname.getText().toString();
				String password = edpassword.getText().toString();
				if (name.equals("") || password.equals("")) {
					// 弹出消息框
					new AlertDialog.Builder(Login.this).setTitle("错误")
							.setMessage("帐号或密码不能空").setPositiveButton("确定", null)
							.show();
				} else {
					isUserinfo(name, password);
				}
			}

			// 判断输入的用户是否正确
			public Boolean isUserinfo(String name, String pwd) {
				try{
					String str="select * from tb_user where name=? and password=?";
					Cursor cursor = db.rawQuery(str, new String []{name,pwd});
					if(cursor.getCount()<=0){
						new AlertDialog.Builder(Login.this).setTitle("错误")
						.setMessage("请检查账号，密码，确保已经注册！").setPositiveButton("确定", null)
						.show();
						return false;
					}else{
						Intent intent = new Intent();
						intent.setClass(Login.this, MainActivity.class);
						startActivity(intent);
						return true;
					}
					
				}catch(SQLiteException e){
					createDb();
				}
				return false;
			}
		
		}
		// 创建数据库和用户表
		public void createDb() {
			db.execSQL("create table tb_user( name varchar(30) primary key,password varchar(30))");
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
}
