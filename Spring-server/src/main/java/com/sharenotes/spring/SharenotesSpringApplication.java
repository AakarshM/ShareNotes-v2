package com.sharenotes.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class SharenotesSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharenotesSpringApplication.class, args);
		try{

			getConnection();
			System.out.println("DB CONNED!");

		} catch (Exception ex){

		}
	}

	private static Connection getConnection() throws URISyntaxException, SQLException {
		String dbUrl = System.getenv("JDBC_DATABASE_URL");
		return DriverManager.getConnection(dbUrl);
	}
}
