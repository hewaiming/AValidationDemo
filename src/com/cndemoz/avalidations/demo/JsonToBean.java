package com.cndemoz.avalidations.demo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonToBean {

	public List<JSONObject> JsonArrayToList(String data) {

		ArrayList<JSONObject> lists = null;
		try {
			JSONArray jsonarray = new JSONArray(data);

			lists = new ArrayList<JSONObject>();
			// System.out.println("jsonarray.length()---" + jsonarray.length());
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jsonobj = jsonarray.getJSONObject(i);
				lists.add(jsonobj);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return lists;
	}

	public static List<User> JsonArrayToUserBean(String data) {
		ArrayList<User> listBean = null;
		try {
			JSONArray jsonarray = new JSONArray(data);

			listBean = new ArrayList<User>();

			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jsonobj = jsonarray.getJSONObject(i);
				User mUser = new User();
				mUser.setUserName(jsonobj.getString("UserID"));
				listBean.add(mUser);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return listBean;
	}
}
