package Repository.Activity;

import model.Activity;

import java.util.List;

public interface ActivityRepository  {

    boolean addActivity(Activity activity, Long user_id);
    List<Activity> findActivitiesForEmployee(Long user_id);
    Activity findActivityById(Long id);
    void removeAll();

}
