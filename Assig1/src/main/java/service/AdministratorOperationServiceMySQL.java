package service;

import Repository.RightsRolesRepository;
import Repository.User.UserRepository;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.Date;

public class AdministratorOperationServiceMySQL implements AdministratorOperationService{

   private final UserRepository userRepository;
   private final RightsRolesRepository rightsRolesRepository;

   public AdministratorOperationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
      this.userRepository = userRepository;
      this.rightsRolesRepository = rightsRolesRepository;
   }


   @Override
   public Notification<Boolean> createUser(String role, String username, String password) {
      Role userRole=rightsRolesRepository.findRoleByTitle(role);
      User user = new UserBuilder()
              .setUsername(username)
              .setPassword(password)
              .setRoles(Collections.singletonList(userRole))
              .build();
      UserValidator userValidator = new UserValidator(user);
      boolean userValid = userValidator.validate();
      Notification<Boolean> userRegisterNotification = new Notification<>();

      if (!userValid) {
         userValidator.getErrors().forEach(userRegisterNotification::addError);
         userRegisterNotification.setResult(Boolean.FALSE);
         return userRegisterNotification;
      } else {
         user.setPassword(encodePassword(password));
         userRegisterNotification.setResult(userRepository.save(user));
         return  userRegisterNotification;
      }
      }

   @Override
   public Notification<Boolean> updateUser(Long id, String username, String password) {
      User user=new UserBuilder().setId(id).setUsername(username).setPassword(password).build();
      UserValidator userValidator = new UserValidator(user);
      boolean userValid = userValidator.validate();
      Notification<Boolean> userRegisterNotification = new Notification<>();
      if (!userValid) {
         userValidator.getErrors().forEach(userRegisterNotification::addError);
         userRegisterNotification.setResult(Boolean.FALSE);
         return userRegisterNotification;
      } else {
         user.setPassword(encodePassword(password));
         userRegisterNotification.setResult(userRepository.updateUser(user));
         return  userRegisterNotification;
      }
      }

   @Override
   public boolean deleteUser(Long id) {
       return userRepository.deleteById(id);

   }

   public void generateReport(Date d1, Date d2)
   {

   }

   private String encodePassword(String password) {
      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hash = digest.digest(password.getBytes("UTF-8"));
         StringBuilder hexString = new StringBuilder();

         for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
         }

         return hexString.toString();
      } catch (Exception ex) {
         return null;
      }
   }
}
