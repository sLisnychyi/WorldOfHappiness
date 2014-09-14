package com.happinest.world.data;

import java.util.Date;

public class TwitterData {

    private final boolean isChecked;
    private final Date date;
    private final String geoPosition;
    private final String text;
    private final String language;


    public TwitterData(boolean checked, Date date, String geoPosition, String text, String language) {
        isChecked = checked;
        this.date = date;
        this.geoPosition = geoPosition;
        this.text = text;
        this.language = language;
    }

    public boolean isChecked() {
        return isChecked;
    }


    public Date getDate() {
        return date;
    }

    public String getGeoPosition() {
        return geoPosition;
    }

    public String getText() {
        return text;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitterData that = (TwitterData) o;

        if (isChecked != that.isChecked) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (geoPosition != null ? !geoPosition.equals(that.geoPosition) : that.geoPosition != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (isChecked ? 1 : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (geoPosition != null ? geoPosition.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TwitterData{" +
                "isChecked=" + isChecked +
                ", date=" + date +
                ", geoPosition='" + geoPosition + '\'' +
                ", text='" + text + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
