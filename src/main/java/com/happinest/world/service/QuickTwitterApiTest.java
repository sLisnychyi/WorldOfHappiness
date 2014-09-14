package com.happinest.world.service;

import com.happinest.world.data.*;
import com.happinest.world.data.Location;
import twitter4j.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class QuickTwitterApiTest {

    //public static volatile AtomicInteger NUMBER_OF_CONFIG_TOKEN = new AtomicInteger(2);

    public static void main(String[] args) throws TwitterException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        //ExecutorService executor = Executors.newSingleThreadExecutor();
        TwitterConfig config = new TwitterConfig();
        //InternetService scraper = new TweeterScraper();
        try {
            //double[] wisconsin = {-92.8893, 42.4919, -86.8052, 47.0808};
            //Point center = LocationService.getCenter(wisconsin[0], wisconsin[1], wisconsin[2], wisconsin[3]);
            //double radius = LocationService.getRadius(center, new Point(wisconsin[2], wisconsin[3]));
            //com.happinest.world.data.Location location = new Location("wisconsin", center, radius);
            // no concurrency decision
//            for(Location location : getLocations()){
//                scraper.scrapTwitterData(location);
//            }
            // concurrency decision
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(2014, Calendar.SEPTEMBER, 7);
            Date requestStartDate = cal.getTime();

            while (true) {
                for (Location location : getLocations()) {
                    for(int i = 0 ; i < config.getTwiterConfigs().size(); i++){
                    executor.submit(new TweeterScraper(location, requestStartDate, new TwitterFactory(config.getTwitterConfiguration(i)).getInstance()));
                    }
                }
                executor.shutdown();
                while (!executor.isTerminated()) {
                }
                System.out.println("*******");
                requestStartDate =  InternetServiceHelper.decrimentDate(requestStartDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Location> getLocations() {
        List<Location> result = new ArrayList<>();
        Map<String, double[]> states = LocationStorage.getUsaStates();
        for (Map.Entry<String, double[]> entry : states.entrySet()) {
            double[] value = entry.getValue();
            Point center = LocationService.getCenter(value[0], value[1], value[2], value[3]);
            double radius = LocationService.getRadius(center, new Point(value[2], value[3]));
            com.happinest.world.data.Location location = new Location(entry.getKey(), center, radius);
            result.add(location);
        }
        return result;
    }


}
