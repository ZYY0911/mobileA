package com.example.selvet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.db.DB;
import com.example.db.MyUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class getOrderById
 */
@WebServlet("/getOrderById")
public class getOrderById extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getOrderById() {
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
		String id = jsonobject.getString("id");
		String urlString = request.getRequestURL().toString();
		urlString = urlString.substring(0, urlString.lastIndexOf("/"));
		System.out.println(urlString);
		System.out.println(request.getRemoteHost());
		System.err.println(new MyUtil().simpDate("yyyy-MM-dd HH:mm:ss", new java.util.Date()));
		reader.close();
		DB db = new DB();
		JSONObject jsonObject2 = new JSONObject();
		db.getRs("select * from order_t  where id ='" + id + "'");

		ResultSet set = db.getRs();
		try {
			if (set != null && set.next()) {
				jsonObject2.put("RESULT", "S");
				jsonObject2.put("id", set.getInt(1));
				jsonObject2.put("type", set.getString(2));
				jsonObject2.put("date", set.getString(3).replace(".0", ""));
				jsonObject2.put("cost", set.getFloat(4));
				DB db1 = new DB();
				db1.getRs("select * from orderdetails where id='" + set.getInt(1) + "'");
				ResultSet set1 = db1.getRs();
				List<JSONObject> jsonobjects = new ArrayList<JSONObject>();
				if (set1 != null) {
					while (set1.next()) {
						JSONObject jsonobject3 = new JSONObject();
						jsonobject3.put("business", set1.getString(3));
						jsonobject3.put("commodity", set1.getString(4));
						jsonobject3.put("price", set1.getInt(5));
						jsonobject3.put("count", set1.getString(6));
						jsonobjects.add(jsonobject3);
					}
				}
				jsonObject2.put("ROWS_DETAIL", jsonobjects.toString());
			} else {
				jsonObject2.put("RESULT", "F");
			}
		} catch (SQLException e) {
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
