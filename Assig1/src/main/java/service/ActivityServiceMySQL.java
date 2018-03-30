package service;

import Repository.Activity.ActivityRepository;
import model.Activity;
import model.builder.ActivityBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ActivityServiceMySQL implements ActivityService{

    private final ActivityRepository activityRepository;

    public ActivityServiceMySQL(ActivityRepository activityRepository)
    {
        this.activityRepository=activityRepository;
    }

    public void addActivity(Long id,String name,Date date)
    {
        activityRepository.addActivity(new ActivityBuilder().setName(name).setActivityTime(date).build(),id);
    }

    public void generateReport(Long emp_id)
     {
        String FILENAME="E:\\Facultate\\Anu 3\\Sem2\\report.txt";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME));
            List<Activity> activities=activityRepository.findActivitiesForEmployee(emp_id);

            Iterator it=activities.iterator();

            while(it.hasNext())
            {
                Activity a= (Activity) it.next();
                bw.write(a.getName()+" "+a.getActivityTime());
                bw.newLine();


            }

            bw.close();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
