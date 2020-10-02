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

import com.example.bean.Subway;
import com.example.db.DB;
import com.example.db.MyUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class getSubwaysByStation
 */
@WebServlet("/getSubwaysByStation")
public class getSubwaysByStation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getSubwaysByStation() {
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
		String stationName = jsonobject.getString("stationName");
		String urlString = request.getRequestURL().toString();
		urlString = urlString.substring(0, urlString.lastIndexOf("/"));
		System.out.println(urlString);
		System.out.println(request.getRemoteHost());
		System.err.println(new MyUtil().simpDate("yyyy-MM-dd HH:mm:ss", new java.util.Date()));
		reader.close();
		DB db = new DB();
		DB db1 = new DB();
		DB db2 = new DB();
		DB db3 = new DB();
		JSONObject jsonObject2 = new JSONObject();
		db.getRs("select * from subwaystation where stationname ='" + stationName + "'");
		ResultSet set = db.getRs();

		try {
			if (set != null) {
				jsonObject2.put("RESULT", "S");
				List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
				while (set.next()) {
					// ，包括地铁编号、名称、下一站名称、到达本站时长
					JSONObject jsonObject3 = new JSONObject();
					int subwayid = set.getInt(2);
					db1.getRs("select * from subway where id='" + subwayid + "'");
					db3.getRs("select * from subwaystation where id='" + subwayid + "'");
					ResultSet set3 = db3.getRs();
					List<Subway> station = new ArrayList<Subway>();
					while (set3.next()) {
						station.add(new Subway(set3.getString(4), set3.getInt(5)));
					}
					ResultSet set1 = db1.getRs();
					if (set1 != null && set1.next()) {
						jsonObject3.put("subwayid", subwayid);
						jsonObject3.put("name", set1.getString(2));
						int speed = set1.getInt(3);
						int stationIndex = set1.getInt(4);
						db2.getRs("select * from subwaystation where id='" + subwayid + "' and stationIndex ='"
								+ (stationIndex + 1) + "'");
						ResultSet set2 = db2.getRs();
						if (set2 != null && set2.next()) {
							String nestName = set2.getString(4);
							boolean isFirst = false;
							long distance = 0;
							for (int i = 0; i < station.size(); i++) {
								if (station.get(i).getName().equals(stationName)) {
									isFirst = true;
								}
								if (isFirst) {
									if (station.get(i).getName().equals(nestName)) {
										isFirst = false;
									}
									distance += station.get(i).getDiatance();
								}
							}
							jsonObject3.put("nextname", set2.getString(4));
							jsonObject3.put("time", distance / speed);
						}
						System.out.println(speed + "----" + stationIndex);
					}
					jsonObjects.add(jsonObject3);
				}
				jsonObject2.put("ROWS_DETAIL", jsonObjects.toString());
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
			db1.closed();
			db2.closed();
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
