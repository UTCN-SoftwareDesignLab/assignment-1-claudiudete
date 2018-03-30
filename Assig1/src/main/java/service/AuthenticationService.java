package service;

import Repository.AuthenticationException;
import model.User;
import model.validation.Notification;


public interface AuthenticationService {

    Notification<User> login(String username, String password);
}
