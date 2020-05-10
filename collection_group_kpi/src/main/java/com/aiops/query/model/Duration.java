package com.aiops.query.model;

import com.aiops.query.enums.Step;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Duration {
    private String start;
    private String end;
    private Step step;

    public Duration(){
        this.start = "2020-03";
        this.end = "2020-04";
        this.step = Step.MONTH;
    }

    public Duration(Date date, Step step){
        SimpleDateFormat format = new SimpleDateFormat();
        Date lastDate = date;

        if (step == Step.MINUTE){
            lastDate = new Date(date.getTime() - 60*1000);
            format = new SimpleDateFormat("yyyy-MM-dd HHmm");
        }
        else if (step == Step.HOUR){
            lastDate = new Date(date.getTime() - 60*60*1000);
            format = new SimpleDateFormat("yyyy-MM-dd HH");
        }
        else if (step == Step.DAY){
            lastDate = new Date(date.getTime() - 24*60*60*1000);
            format = new SimpleDateFormat("yyyy-MM-dd");
        }
        else if (step == Step.MONTH){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            lastDate = cal.getTime();
            format = new SimpleDateFormat("yyyy-MM");
        }
        this.start = format.format(lastDate);
        this.end = format.format(date);
        this.step = step;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }
}
