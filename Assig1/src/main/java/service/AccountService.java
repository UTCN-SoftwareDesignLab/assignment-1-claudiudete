package service;

public interface AccountService {

    boolean addAccountToClient(Long id,Long sum,String type);
    boolean removeAccount(Long acc_id);
    int transferBetweenAccounts(Long accId1,Long accId2,Long sum);
    boolean updateAccount(Long id,Long sum);
    int payBill(Long id,Long price);
}
