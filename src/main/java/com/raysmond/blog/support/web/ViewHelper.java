package com.raysmond.blog.support.web;


import com.raysmond.blog.AppSetting;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Raysmond<jiankunlei@gmail.com>
 */
@Service
public class ViewHelper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy");
    private static final SimpleDateFormat DATE_FORMAT_MONTH_DAY = new SimpleDateFormat("MMM dd");

    private AppSetting appSetting;

    @Autowired
    public ViewHelper(AppSetting appSetting){
        this.appSetting = appSetting;
    }

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

    public String getMonthAndDay(Date date){
        return date == null ? "" : DATE_FORMAT_MONTH_DAY.format(date);
    }

    public String metaTitle(String title){
        return title + " · " + appSetting.getSiteName();
    }
}
