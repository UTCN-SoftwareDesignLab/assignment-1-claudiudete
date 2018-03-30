package model.builder;

import model.Activity;

import java.util.Date;

public class ActivityBuilder {

    private Activity activity;

    public ActivityBuilder()
    {
        activity=new Activity();
    }
    public ActivityBuilder setId(Long id)
    {
        activity.setId(id);
        return this;
    }

    public ActivityBuilder setName(String name)
    {
        activity.setName(name);
        return this;
    }

    public ActivityBuilder setActivityTime(Date date)
    {
        activity.setActivityTime(date);
        return this;
    }

    public Activity build()
    {
        return activity;
    }
}
