package model.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Notification<T> {

    private T result;
    private List<String> errors;

    public Notification()
    {
        this.errors=new ArrayList<String>();
    }

    public void addError(String error)
    {
        errors.add(error);
    }

    public boolean hasError()
    {
        return errors.size()>0;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() throws ResultFetchException {
        if (hasError()) {
            throw new ResultFetchException(errors);
        }
        return result;
    }

    public String getFormattedErrors()
    {
        return errors.stream().map(Object::toString).collect(Collectors.joining("\n"));

    }







}
