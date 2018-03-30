package service;

import model.validation.Notification;

public interface EmployeeOperationService {

    Notification<Boolean> saveClient(String name, String idCardNumber, Long persNumCode, String address);
    Notification<Boolean> updateClient(Long id,String name,String idCardNumber,Long persNumCode,String address);
    boolean deleteClient(Long id);
}
