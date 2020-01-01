package com.emse.spring.faircorp.autoControlThread;

import com.emse.spring.faircorp.DTO.AutoLightDto;
import com.emse.spring.faircorp.DTO.LightDto;
import com.emse.spring.faircorp.model.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AutoLightThread extends Thread {
    private Long roomId;

    public AutoLightThread() {

    }

    public AutoLightThread(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public void run() {
        while(true) {
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                URL urlForGetRequest = new URL("http://localhost:8080/api/autoLightControllers");
                String readLine = null;
                HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
                conection.setRequestMethod("GET");
                int responseCode = conection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conection.getInputStream()));
                    StringBuffer response = new StringBuffer();
                    while ((readLine = in .readLine()) != null) {
                        response.append(readLine);
                    } in .close();
                    // print result
                    System.out.println("JSON String Result " + response.toString());

                    try {
                        JSONParser parser = new JSONParser();
                        JSONArray obj = (JSONArray) parser.parse(response.toString());

                        for (int i = 0; i < obj.size(); i++) {
                            /* Retrieve the auto light controller and create object */
                            JSONObject lightController = (JSONObject) obj.get(i);
                            AutoLightDto autoLight = new AutoLightDto();
                            autoLight.setRoomId((Long) lightController.get("roomId"));
                            if (lightController.get("autoLightControlState") == "ON") {
                                autoLight.setAutoLightControlState(Status.ON);
                            }
                            else {
                                autoLight.setAutoLightControlState(Status.OFF);
                            }
                            autoLight.setId((Long) lightController.get("id"));
                            System.out.println("HEEEEEEEEEEERE " + lightController.get("sunriseTime"));
                            autoLight.setSunriseTime(lightController.get("sunriseTime").toString());
                            autoLight.setSunsetTime(lightController.get("sunsetTime").toString());

                            /* Get current time */
                            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
                            Date date = new Date();
                            System.out.println(dateFormat.format(date));
                            String currentDate = dateFormat.format(date).toString();
                            System.out.println(currentDate);
                            System.out.println("here " + autoLight.getSunriseTime());


//                            try {
//                                System.out.println((dateFormat.parse(currentDate).before(dateFormat.parse(autoLight.getSunriseTime()))));
//
//                            } catch (java.text.ParseException e) {
//                                e.printStackTrace();
//                            }

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    //GetAndPost.POSTRequest(response.toString());
                }
                else {
                    System.out.println("GET NOT WORKED");
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}