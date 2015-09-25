package com.raysmond.blog.support.web;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Raysmond on 9/26/15.
 */
@Service
public class ViewHelper {

    @Autowired
    private PygmentsService highlightService;

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

    public String highlightCode(String content){
        return highlightService.highlight(content);
    }

}
