package service;

import Repository.Account.AccountRepository;
import Repository.Client.ClientRepository;
import model.Account;
import model.builder.AccountBuilder;

import java.util.Calendar;

public class AccountServiceMySQL implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;


   public AccountServiceMySQL(AccountRepository accountRepository,ClientRepository clientRepository)
   {

       this.accountRepository=accountRepository;
       this.clientRepository=clientRepository;
   }

   public boolean addAccountToClient(Long id,Long sum,String type)
   {
       try {
           clientRepository.findByID(id);
           return accountRepository
                   .addAccount(new AccountBuilder().setSum(sum).setType(type).setCreationDate(Calendar.getInstance().getTime()).build(), id);
       }
       catch(Exception e)
       {
           return false;
       }


   }

   public boolean removeAccount(Long acc_id)
   {
       return accountRepository.removeAccount(acc_id);
   }

   public int transferBetweenAccounts(Long accId1,Long accId2,Long sum)
   {
       Account acc1=accountRepository.findAccountById(accId1);
       Account acc2=accountRepository.findAccountById(accId2);

        if(accountRepository.findAccountById(accId1)==null) return 1;
        else if(accountRepository.findAccountById(accId2)==null) return 1;
        else if(sum>acc1.getSum()) return 0;

       else
       {
           accountRepository.changeAccountSum(acc1.getSum()-sum,accId1);
           accountRepository.changeAccountSum(acc2.getSum()+sum,accId2);
          return 2;
       }

   }

   public int payBill(Long id,Long price)
   {
       Account acc=accountRepository.findAccountById(id);
       if(price>acc.getSum()) return 0;
       if(acc==null) return 1;
       else {
           accountRepository.changeAccountSum(acc.getSum() - price, id);
           return 2;
       }
   }

   public boolean updateAccount(Long id,Long sum)
   {
       return accountRepository.changeAccountSum(sum,id);
   }


}
