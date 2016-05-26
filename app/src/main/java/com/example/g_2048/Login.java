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
	// �ʺź�����
		private EditText edname;
		private EditText edpassword;

		private Button btregister;
		private Button btlogin;
		// ����SQLite���ݿ�
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
			// ��ת��ע�����
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
					// ������Ϣ��
					new AlertDialog.Builder(Login.this).setTitle("����")
							.setMessage("�ʺŻ����벻�ܿ�").setPositiveButton("ȷ��", null)
							.show();
				} else {
					isUserinfo(name, password);
				}
			}

			// �ж�������û��Ƿ���ȷ
			public Boolean isUserinfo(String name, String pwd) {
				try{
					String str="select * from tb_user where name=? and password=?";
					Cursor cursor = db.rawQuery(str, new String []{name,pwd});
					if(cursor.getCount()<=0){
						new AlertDialog.Builder(Login.this).setTitle("����")
						.setMessage("�����˺ţ����룬ȷ���Ѿ�ע�ᣡ").setPositiveButton("ȷ��", null)
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
		// �������ݿ���û���
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
