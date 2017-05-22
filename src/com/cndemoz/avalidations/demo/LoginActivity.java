/**
 *  Copyright 2014 ken.cai (http://www.shangpuyun.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 *
 */
package com.cndemoz.avalidations.demo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import com.cndemoz.avalidations.EditTextValidator;
import com.cndemoz.avalidations.ValidationModel;
import com.cndemoz.avalidations.validations.PasswordValidation;
import com.cndemoz.avalidations.validations.UserNameValidation;

/**
 * 登陆校验演示
 * 
 * @Description:
 * @author ken.cai
 * @date 2014-11-21 下午9:42:53
 * @version V1.0
 * 
 */
public class LoginActivity extends Activity implements OnClickListener {
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;
	private EditTextValidator editTextValidator;
	private String GetDataURL = "http://125.64.59.11:8000/scgy/android/odbcPhP/Login.php";
	private String username, password;
	private JSONArrayParser jsonParser = new JSONArrayParser();
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		usernameEditText = (EditText) findViewById(R.id.login_username_edittext);
		passwordEditText = (EditText) findViewById(R.id.login_password_edittext);
		loginButton = (Button) findViewById(R.id.login_button);

		loginButton.setOnClickListener(this);

		editTextValidator = new EditTextValidator(this).setButton(loginButton)
				.add(new ValidationModel(usernameEditText, new UserNameValidation()))
				.add(new ValidationModel(passwordEditText, new PasswordValidation())).execute();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:

			if (editTextValidator.validate()) {
				Toast.makeText(this, "通过校验", Toast.LENGTH_SHORT).show();
				username = usernameEditText.getText().toString();
				password = passwordEditText.getText().toString();
				new LoginTask().execute();
			}
			break;
		}
	}

	public class LoginTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			List<NameValuePair> mparams = new ArrayList<NameValuePair>();
			mparams.clear();
			mparams.add(new BasicNameValuePair("UserName", username)); // 用户名
			mparams.add(new BasicNameValuePair("PassWord", password));// 密码

			JSONArray json = jsonParser.makeHttpRequest(GetDataURL, "POST", mparams);
			if (json != null) {
				return json.toString();// 从服务器返回有数据
			} else {
				Log.i("PHP服务器数据返回情况：---", "从PHP服务器无数据返回！");
				return "";
			}

		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("")) {
				Toast.makeText(mContext, "登录失败，用户名或密码不对！", 0).show();
			} else {
				List<User> User_ListBean = JsonToBean.JsonArrayToUserBean(result);
				if (User_ListBean.get(0).getUserName().equals(username)) {
					Toast.makeText(mContext, "登录成功", 0).show();
				} else {
					Toast.makeText(mContext, "登录失败，用户名不对！", 0).show();
				}

			}
			super.onPostExecute(result);
		}
	}

}
