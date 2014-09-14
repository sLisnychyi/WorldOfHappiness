package com.happinest.world.data;

public class Location {

    private final String geoPosition;
    private final Point center;
    private final double radiusInKm;

    public Point getCenter() {
        return center;
    }

    public double getRadiusInKm() {
        return radiusInKm;
    }

    public String getGeoPosition() {
        return geoPosition;
    }

    public Location(String geoPosition, Point center, double radiusInKm) {
        this.geoPosition = geoPosition;
        this.center = center;
        this.radiusInKm = radiusInKm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.radiusInKm, radiusInKm) != 0) return false;
        if (center != null ? !center.equals(location.center) : location.center != null) return false;
        if (geoPosition != null ? !geoPosition.equals(location.geoPosition) : location.geoPosition != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = geoPosition != null ? geoPosition.hashCode() : 0;
        result = 31 * result + (center != null ? center.hashCode() : 0);
        temp = radiusInKm != +0.0d ? Double.doubleToLongBits(radiusInKm) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "geoPosition='" + geoPosition + '\'' +
                ", center=" + center +
                ", radiusInKm=" + radiusInKm +
                '}';
    }
}
