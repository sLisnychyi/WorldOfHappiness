package com.happinest.world.service;

import com.happinest.world.data.Point;

public class LocationService {

    public static Point getCenter(double x1, double y1, double x2, double y2){
        return new Point((x2+x1)/2, (y2+y1)/2);
    }

    public static double getRadius(Point center, Point corner){
        double lon = center.getY();
        double lon2 = corner.getY();
        double lat = center.getX();
        double lat2 = corner.getX();
        return 111.2 * Math.sqrt((lon - lon2)*(lon - lon2) + (lat - lat2)*Math.cos(Math.PI*lon/180)*(lat - lat2)*Math.cos(Math.PI*lon/180));
    }
}
