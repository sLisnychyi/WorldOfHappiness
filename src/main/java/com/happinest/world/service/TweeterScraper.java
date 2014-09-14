package com.happinest.world.service;

import com.google.common.collect.Sets;
import com.happinest.world.data.*;
import com.happinest.world.data.Location;
import com.happinest.world.persistance.PersistTwitts;
import twitter4j.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.happinest.world.service.QuickTwitterApiTest.*;

public class TweeterScraper implements InternetService, Runnable {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final int MAX_NUMBER_OF_TWEETS_PER_REQUEST = 500;
    private static final int MAX_NUMBER_OF_TWEETS = 1000;
    private final PersistTwitts persister;
    private final TwitterConfig config;
    private final com.happinest.world.data.Location location;
    private Twitter twitter;
    private final Date date;

    public TweeterScraper(Date date) {
        this.persister = new PersistTwitts();
        this.config = new TwitterConfig();
        this.location = null;
        this.twitter = new TwitterFactory(config.getTwitterConfiguration(1)).getInstance();
        this.date = date;
    }

    public TweeterScraper(Location location, Date date) {
        this.persister = new PersistTwitts();
        this.config = new TwitterConfig();
        this.location = location;
        this.twitter = new TwitterFactory(config.getTwitterConfiguration(3)).getInstance();
        this.date = date;
    }

    @Override
    public void scrapTwitterData(com.happinest.world.data.Location location, Date requestDate) throws TwitterException, InterruptedException {
        long lastId = Long.MAX_VALUE;
        long stopRequest = 0;

        String today = new SimpleDateFormat(DATE_FORMAT).format(requestDate);
        GeoLocation geoLocation = new GeoLocation(location.getCenter().getY(), location.getCenter().getX());
        Query query = new Query();
        query.setGeoCode(geoLocation, location.getRadiusInKm(), Query.KILOMETERS);
        query.setCount(100);

        while (stopRequest <= MAX_NUMBER_OF_TWEETS) {
            try {
                boolean alreadySeen = false;
                Set<Long> seenTweets = Sets.newHashSet();
                List<TwitterData> twits = new ArrayList<>();
                query.setUntil(today);
                long tweetNumber = 0;
                while (tweetNumber <= MAX_NUMBER_OF_TWEETS_PER_REQUEST) {
                    query.setMaxId(lastId - 1);
                    QueryResult result = twitter.search(query);
                    for (Status status : result.getTweets()) {
                        if (seenTweets.contains(status.getId())) {
                            alreadySeen = true;
                            System.out.println("Tweet already seen: ");
                        } else {
                            seenTweets.add(status.getId());
                        }
                        Date createdAt = status.getCreatedAt();
                        String twiterDate = new SimpleDateFormat(DATE_FORMAT).format(createdAt);
                        if (!today.equalsIgnoreCase(twiterDate)) {
                            today = twiterDate;
                        }
                        String twitText = status.getText();
                        String lang = status.getLang();
                        String tweetText = (" @" + status.getUser().getScreenName() + ":" + twitText).replace("\n", " ").replace("\t", " ") + " *** lang:" + lang;
                        System.out.println("Thread: " + Thread.currentThread().getName() + " TWITT_NO: " + tweetNumber + " id=" + status.getId() + " on " + createdAt +
                                " DATA: \t" + tweetText + " GEO:" + query.getGeocode());

                        twits.add(new TwitterData(alreadySeen, createdAt, location.getGeoPosition(), twitText, lang));
                        ++tweetNumber;

                        if (lastId < status.getId()) {
                            throw new IllegalStateException();
                        }
                        lastId = status.getId();
                    }
                }
                stopRequest += tweetNumber;
                //insert into the db
                persister.persistTweets(twits);

            } catch (Exception exception) {
                //today - 1
                Date date = null;
                try {
                    date = new SimpleDateFormat(DATE_FORMAT).parse(today);
                } catch (ParseException e) {
                    System.out.println("Cannot parse toDay.");
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int daysToDecrement = -7;
                cal.add(Calendar.DATE, daysToDecrement);
                today = new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
                //Thread.sleep(30000);
                System.out.println("Exception while retrieving twiteer results:");
                exception.printStackTrace();
              //  break;
//                synchronized (this) {
//                    System.out.println(String.format("CHANGING TWITTER TOKEN!\n OLD TOKEN: %s", NUMBER_OF_CONFIG_TOKEN));
//                    int i = NUMBER_OF_CONFIG_TOKEN.incrementAndGet();
//                    if(config.getTwiterConfigs().get(i) != null){
//                    this.twitter = new TwitterFactory(config.getTwitterConfiguration(i)).getInstance();
//                    }
//                    else{
//                        System.out.println(String.format("Can't find token in the list with = %s. Token would be dropped to 0.", i));
//                        this.twitter = new TwitterFactory(config.getTwitterConfiguration(0)).getInstance();
//                        NUMBER_OF_CONFIG_TOKEN.set(0);
//                    }
//                    System.out.println("TAKING NEW TOKEN: " + i);
//                }
            }

        }
    }

    @Override
    public void run() {
        try {
            scrapTwitterData(this.location, this.date);
        } catch (TwitterException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}