package com.example.selvet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.db.MyUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class get_weather_info
 */
@WebServlet("/get_weather_info")
public class get_weather_info extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public get_weather_info() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String arr[] = { "«Á", "“ı", "∂‡‘∆", "–°”Í", "ˆ≤" };

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
		String urlString = request.getRequestURL().toString();
		urlString = urlString.substring(0, urlString.lastIndexOf("/"));
		System.out.println(urlString);
		System.out.println(request.getRemoteHost());
		System.err.println(new MyUtil().simpDate("yyyy-MM-dd HH:mm:ss", new java.util.Date()));
		reader.close();
		Random random = new Random();
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("RESULT", "S");
//		jsonObject2.put("weather", arr[random.nextInt(5)]);
//		jsonObject2.put("temperature", random.nextInt(40));
//		jsonObject2.put("illumination", random.nextInt(5000));
//		jsonObject2.put("co2", random.nextInt(8000));
//		jsonObject2.put("pm2.5", random.nextInt(200));
//		jsonObject2.put("date", new MyUtil().simpDate("yyyy-MM-dd", new java.util.Date()));
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < 5; i++) {
			JSONObject jsonObject3 = new JSONObject();

			jsonObject3.put("date", calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH));
			jsonObject3.put("weather", arr[random.nextInt(5)]);
			jsonObject3.put("temperature", random.nextInt(40));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			if (i == 0) {
				jsonObject2.put("date", jsonObject3.get("date"));
				jsonObject2.put("weather", jsonObject3.get("weather"));
				jsonObject2.put("temperature", jsonObject3.get("temperature"));
			}
			jsonObjects.add(jsonObject3);
		}
		jsonObject2.put("ROW_DEATIL", jsonObjects.toString());
		PrintWriter owtPrintWriter = response.getWriter();
		owtPrintWriter.write(jsonObject2.toString());
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
