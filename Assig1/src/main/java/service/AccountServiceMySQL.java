package service;

import Repository.Account.AccountRepository;
import model.Account;
import model.builder.AccountBuilder;

public class AccountServiceMySQL implements AccountService {

    private final AccountRepository accountRepository;


   public AccountServiceMySQL(AccountRepository accountRepository)
   {
       this.accountRepository=accountRepository;
   }

   public boolean addAccountToClient(Long id,Long sum,String type)
   {
       return accountRepository
               .addAccount(new AccountBuilder().setSum(sum).setType(type).build(),id);


   }

   public boolean removeAccount(Long acc_id)
   {
       return accountRepository.removeAccount(acc_id);
   }

   public boolean transferBetweenAccounts(Long accId1,Long accId2,Long sum)
   {
       Account acc1=accountRepository.findAccountById(accId1);
       Account acc2=accountRepository.findAccountById(accId2);

       if(sum>acc1.getSum()) return false;
       else
       {
           accountRepository.changeAccountSum(acc1.getSum()-sum,accId1);
           accountRepository.changeAccountSum(acc2.getSum()+sum,accId2);
           return true;
       }

   }

   public boolean payBill(Long id,Long price)
   {
       Account acc=accountRepository.findAccountById(id);
       if(price>acc.getSum()) return false;
       else {
           accountRepository.changeAccountSum(acc.getSum() - price, id);
           return true;
       }
   }


}
