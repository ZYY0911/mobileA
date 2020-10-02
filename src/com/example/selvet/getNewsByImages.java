package com.example.selvet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.db.DB;
import com.example.db.MyUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class getNewsByImages
 */
@WebServlet("/getNewsByImages")
public class getNewsByImages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getNewsByImages() {
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
		db.getRs("select * from news where id = '" + id + "'");
		ResultSet set = db.getRs();
		try {
			if (set != null && set.next()) {
				jsonObject2.put("RESULT", "S");
				jsonObject2.put("id", set.getInt(1));
				jsonObject2.put("type", set.getString(2));
				jsonObject2.put("img", urlString + "/images/" + set.getString(3));
				jsonObject2.put("title", set.getString(10));
				jsonObject2.put("content", set.getString(4));
				jsonObject2.put("publicTime", set.getString(5).replace(".0", ""));
				jsonObject2.put("audienceCount", set.getInt(6));
				jsonObject2.put("praiseCount", set.getInt(7));
				jsonObject2.put("subject", set.getString(8));
				jsonObject2.put("flag", set.getInt(9));
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
