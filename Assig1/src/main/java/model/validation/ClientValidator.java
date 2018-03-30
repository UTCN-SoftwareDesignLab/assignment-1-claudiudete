package model.validation;

import model.Client;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private static final int MIN_LENGTH= 4;
    private static final int MIN_LENGTH_PERSNUMCODE=10;

    private final Client client;
    private final List<String> errors;


    public ClientValidator(Client client)
    {
        this.client = client;
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validateName(String name)
    {
        if(client.getName().length()<MIN_LENGTH)
            errors.add("Name too short!");

    }

    private void validateIdCardNumber(String idCardNumber)
    {
        if(client.getIdCardNumber().length()<MIN_LENGTH)
            errors.add("Identification card number too short");
    }

    private void validatePersNumCode(Long persNumCode)
    {
        if(persNumCode>0) {
            if (Math.log10(client.getPersNumCode()) + 1 < MIN_LENGTH_PERSNUMCODE)
                errors.add("Personal numeric code too short");
        }
        else
        {
            errors.add("Invalid personal numeric code");
        }
    }

    private void validateAddress(String address)
    {
        if(client.getAddress().length()<MIN_LENGTH)
            errors.add("Address too short");
    }

    public boolean validate() {
        validateAddress(client.getAddress());
        validateIdCardNumber(client.getIdCardNumber());
        validateName(client.getName());
        validatePersNumCode(client.getPersNumCode());
        return errors.isEmpty();
    }


}
