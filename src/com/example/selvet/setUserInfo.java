package com.example.selvet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.db.DB;
import com.example.db.MyUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class setUserInfo
 */
@WebServlet("/setUserInfo")
public class setUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public setUserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedReader reader = request.getReader();
		String json = reader.readLine();
		JSONObject jsonobject = JSONObject.fromObject(json);
		String userid = jsonobject.getString("userid");
		String name = jsonobject.getString("name");
		String avatar = jsonobject.getString("avatar");
		String phone = jsonobject.getString("phone");
		String id = jsonobject.getString("id");
		String gender = jsonobject.getString("gender");
		String urlString = request.getRequestURL().toString();
		urlString = urlString.substring(0, urlString.lastIndexOf("/"));
		System.out.println(urlString);
		System.out.println(request.getRemoteHost());
		System.err.println(new MyUtil().simpDate("yyyy-MM-dd HH:mm:ss", new java.util.Date()));
		reader.close();
		DB db = new DB();
		JSONObject jsonObject2 = new JSONObject();
		try {
			String sql = "update usertable set";
			if (!avatar.equals("")) {
				sql += " avatar='" + avatar + "',";
			}
			if (!name.equals("")) {
				sql += " name='" + name + "',";
			}
			if (!gender.equals("")) {
				sql += " gender='" + gender + "',";
			}
			if (!phone.equals("")) {
				sql += " phone='" + phone + "',";
			}
			if (!id.equals("")) {
				sql += " id='" + id + "',";
			}
			sql = sql.substring(0, sql.lastIndexOf(","));
			sql += " where userid = '" + userid + "'";
			System.out.println(sql);
			int row = db.update(sql);
			if (row == 1) {
				jsonObject2.put("RESULT", "S");
				db.getRs("select * from usertable where userid ='" + userid + "' ");
				ResultSet set = db.getRs();
				JSONObject jsonObject3 = new JSONObject();
				if (set != null && set.next()) {
					jsonObject3.put("userid", set.getString(1));
					jsonObject3.put("userName", set.getString(2));
				}
				jsonObject2.put("data", jsonObject3.toString());
			} else {
				jsonObject2.put("RESULT", "F");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jsonObject2.clear();
			jsonObject2.put("RESULT", "F");
			e.printStackTrace();
		} finally {
			db.closed();
			PrintWriter owtPrintWriter = response.getWriter();
			owtPrintWriter.write(jsonObject2.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
