package com.raysmond.blog.support.web;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raysmond on 9/26/15.
 */
@Service
public class ViewHelper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy");

    private long startTime;

    public long getResponseTime(){
        return System.currentTimeMillis() - startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getFormattedDate(Date date){
        return date == null ? "" : DATE_FORMAT.format(date);
    }

}
