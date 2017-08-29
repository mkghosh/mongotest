package com.mithun.mongotest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class ConnectionTest
 */
public class ConnectionTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String user;
	private final String database;
	private final char[] pass;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConnectionTest() {
		super();
		user = "levertonuser";
		database = "leverton";
		pass = "12345678".toCharArray();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		MongoClient mongoClient = null;

		HttpSession session = request.getSession();

		MongoCredential credentials = MongoCredential.createCredential(user, database, pass);

		System.out.println("Cursor is here." + credentials.toString());
		try {
//			mongoClient = new MongoClient(new ServerAddress("52.76.228.210", 32803), Arrays.asList(credentials));
			mongoClient = new MongoClient(new ServerAddress("mongodb", 27017), 
					Arrays.asList(credentials));

			System.out.println("Database connected successfully");
//			MongoDatabase db = mongoClient.getDatabase("leverton");
//			System.out.println(db.getName());
			mongoClient.getAddress();
			response.sendRedirect("success.jsp");
			session.setAttribute("msg", "success");
			return;
		
		} catch (MongoException mngoExcep) {
			response.sendRedirect("failed.jsp");
			session.setAttribute("msg", mngoExcep.getMessage());
			return;
		} catch (Exception e) {
			response.sendRedirect("failed.jsp");
			session.setAttribute("msg", e.getMessage());
			return;
		}
		finally {
			mongoClient.close();
		}

	}

}
