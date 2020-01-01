package com.emse.spring.faircorp;

import com.emse.spring.faircorp.autoControlThread.AutoLightThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@SpringBootApplication
public class FaircorpApplication {
	public static void main(String[] args) {
		SpringApplication.run(FaircorpApplication.class, args);
		AutoLightThread autoLightThread = new AutoLightThread();
		autoLightThread.run();
	}
}
