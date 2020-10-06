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
 * Servlet implementation class updateCase
 */
@WebServlet("/updateCase")
public class updateCase extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public updateCase() {
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
		String caseid1=jsonobject.getString("caseid1");
		String caseid2=jsonobject.getString("caseid2");
		String name = jsonobject.getString("name");
		String sex = jsonobject.getString("sex");
		String ID = jsonobject.getString("ID");
		String birthday = jsonobject.getString("birthday");
		String tel = jsonobject.getString("tel");
		
		String address = jsonobject.getString("address");
		String urlString = request.getRequestURL().toString();
		urlString = urlString.substring(0, urlString.lastIndexOf("/"));
		System.out.println(urlString);
		System.out.println(request.getRemoteHost());
		System.err.println(new MyUtil().simpDate("yyyy-MM-dd HH:mm:ss", new java.util.Date()));
		reader.close();
		DB db = new DB();
		JSONObject jsonObject2 = new JSONObject();
		try {
			String sql = "update caseinfo set ";
			if (!caseid2.equals("")) {
				sql += " caseid='" + caseid2 + "',";
			}
			if (!name.equals("")) {
				sql += " name='" + name + "',";
			}
			if (!birthday.equals("")) {
				sql += " birthday='" + birthday + "',";
			}
			if (!sex.equals("")) {
				sql += " sex='" + sex + "',";
			}
			if (!tel.equals("")) {
				sql += " tel='" + tel + "',";
			}
			if (!ID.equals("")) {
				sql += " ID='" + ID + "',";
			}
			if (!address.equals("")) {
				sql += " address='" + address + "',";
			}
			sql = sql.substring(0, sql.lastIndexOf(","));
			sql += " where caseid = '" + caseid1 + "'";
			System.out.println(sql);
			int row = db.update(sql);
			if (row == 1) {
				jsonObject2.put("RESULT", "S");
				
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
