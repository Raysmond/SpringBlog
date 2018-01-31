package com.raysmond.blog.support.web;

import com.domingosuarez.boot.autoconfigure.jade4j.JadeHelper;
import com.raysmond.blog.services.AppSetting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Raysmond
 */
@Service
@JadeHelper("viewHelper")
public class ViewHelper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy");
    private static final SimpleDateFormat DATE_FORMAT_MONTH_DAY = new SimpleDateFormat("MMM dd");

    private AppSetting appSetting;

    private String applicationEnv;
    private long startTime;

    /**
     * Check if current user is authenticated
     *
     * @return true/false
     */
    public boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null != authentication
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @Autowired
    public ViewHelper(AppSetting appSetting) {
        this.appSetting = appSetting;
    }

    public long getResponseTime() {
        return System.currentTimeMillis() - startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getFormattedDate(Date date) {
        return date == null ? "" : DATE_FORMAT.format(date);
    }

    public String getFormattedDate(ZonedDateTime date) {
        return date == null ? "" : date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getMonthAndDay(Date date) {
        return date == null ? "" : DATE_FORMAT_MONTH_DAY.format(date);
    }

    public String metaTitle(String title) {
        return title + " · " + appSetting.getSiteName();
    }

    public String getApplicationEnv() {
        return applicationEnv;
    }

    public void setApplicationEnv(String applicationEnv) {
        this.applicationEnv = applicationEnv;
    }
}
