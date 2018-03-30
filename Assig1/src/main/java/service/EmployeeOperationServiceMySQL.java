package service;

import Repository.Activity.ActivityRepository;
import Repository.Client.ClientRepository;
import model.Activity;
import model.Client;
import model.builder.ActivityBuilder;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import model.validation.UserValidator;

import java.util.Calendar;

public class EmployeeOperationServiceMySQL implements EmployeeOperationService{

    private final ClientRepository clientRepository;
    private final ActivityRepository activityRepository;

    public EmployeeOperationServiceMySQL(ClientRepository clientRepository,ActivityRepository activityRepository)
    {
        this.clientRepository=clientRepository;
        this.activityRepository=activityRepository;
    }

    public Notification<Boolean> saveClient(String name, String idCardNumber, Long persNumCode, String address)
    {
        Client client=new ClientBuilder().setName(name)
                .setIdCardNumber(idCardNumber)
                .setPersNumCode(persNumCode)
                .setAddress(address)
                .build();
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();

        Notification<Boolean> clientRegisterNotification = new Notification<>();
        if (!clientValid) {
            clientValidator.getErrors().forEach(clientRegisterNotification::addError);
            clientRegisterNotification.setResult(Boolean.FALSE);
            return clientRegisterNotification;
        } else {

            clientRegisterNotification.setResult(clientRepository.save(client));
           // Client c=clientRepository.findByName(name);
           //activityRepository.addActivity(new ActivityBuilder().setName("create_user").setActivityTime(Calendar.getInstance().getTime()).build(),c.getId());
            return  clientRegisterNotification;
        }
    }

    public Notification<Boolean> updateClient(Long id,String name,String idCardNumber,Long persNumCode,String address)
    {
        Client client=new ClientBuilder().setId(id).setName(name).setIdCardNumber(idCardNumber).setPersNumCode(persNumCode).setAddress(address).build();
        ClientValidator clientValidator = new ClientValidator(client);
        boolean userValid = clientValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();
        if (!userValid) {
            clientValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
            return userRegisterNotification;
        } else {

            userRegisterNotification.setResult(clientRepository.updateClient(client));
            return  userRegisterNotification;
        }
    }

    public boolean deleteClient(Long id)
    {
        return clientRepository.deleteClient(id);
    }




}
