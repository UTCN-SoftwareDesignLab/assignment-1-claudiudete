package service;

import java.util.Date;

public interface ActivityService {

    void addActivity(Long id,String name,Date date);
    void generateReport(Long emp_id);
}
