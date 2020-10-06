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
 * Servlet implementation class appointment
 */
@WebServlet("/appointment")
public class appointment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public appointment() {
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
	
		String pid = jsonobject.getString("pid");
		String name = jsonobject.getString("name");
		String phone = jsonobject.getString("phone");
		String doctorId = jsonobject.getString("doctorId");
		String appTime = jsonobject.getString("appTime");
		String urlString = request.getRequestURL().toString();
		urlString = urlString.substring(0, urlString.lastIndexOf("/"));
		System.out.println(urlString);
		System.out.println(request.getRemoteHost());
		System.err.println(new MyUtil().simpDate("yyyy-MM-dd HH:mm:ss", new java.util.Date()));
		reader.close();
		DB db = new DB();
		JSONObject jsonObject2 = new JSONObject();
		try {
			
		    int row=db.update("insert into seeadoctor (pid,name,phone,doctorId,appTime) values ('"+pid+"','"+name+"','"+phone+"',"+doctorId+",'"+appTime+"')");
			if (row == 1) {
				jsonObject2.put("RESULT", "S");
				
				db.getRs("select doctors.hospitalid,doctors.deptid,doctors.doctorname,doctors.desc,doctors.tag,seeadoctor.appTime from seeadoctor,doctors where seeadoctor.doctorId=doctors.doctorid and pid='"+pid+"' and appTime='"+appTime+"'");
				ResultSet set = db.getRs();
				JSONObject jsonObject3 = new JSONObject();
				if(set!=null&&set.next()) {				
					jsonObject3.put("hospitalId", set.getString(1));
					jsonObject3.put("deptId", set.getString(2));
					jsonObject3.put("doctorname", set.getString(3));
					jsonObject3.put("desc", set.getString(4));
					jsonObject3.put("tag", set.getString(5));
					jsonObject3.put("appTime", set.getString(6));
				}
				jsonObject2.put("data",jsonObject3.toString() );		
				
				
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
