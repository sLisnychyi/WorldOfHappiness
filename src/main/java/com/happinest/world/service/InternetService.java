package com.happinest.world.service;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Date;

public interface InternetService  {
    public void scrapTwitterData(com.happinest.world.data.Location location,  Date requestDate, Twitter twitter) throws TwitterException, InterruptedException;
}
