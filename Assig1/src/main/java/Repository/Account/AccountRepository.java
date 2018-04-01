package Repository.Account;

import model.Account;

import java.util.List;

public interface AccountRepository {

    boolean addAccount(Account account, Long client_id);
    boolean removeAccount(Long id);
    List<Account> findAccountsForClient(Long clientId);
    Account findAccountById(Long accId);
    void removeAccountsForClient(Long client_id);
    boolean changeAccountSum(Long sum,Long id);
    void removeAll();
}
