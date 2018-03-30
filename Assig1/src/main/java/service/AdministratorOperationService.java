package service;

import model.validation.Notification;

import java.util.Date;

public interface AdministratorOperationService {

    Notification<Boolean> createUser(String role, String username, String password);
    Notification<Boolean> updateUser(Long id,String username,String password);
    boolean deleteUser(Long id);
    void generateReport(Date d1,Date d2);


}
