package com.example.demo.Repo;

import com.example.demo.Model.Account;
import com.example.demo.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AccountRepo extends JpaRepository<Account, Integer> {

    public List<Account> findByCustomer(Customer customer);

    List<Account> findByOpeningBalanceLessThan(double ob);

    public List<Account> findByOpeningDateBetween(LocalDate start, LocalDate end);

    List<Account> findByOpeningBalanceGreaterThan(double ob);

    List<Account> findDistinctByOpeningBalance(double ob);

    List<Account> findByOrderByOpeningBalanceAsc();

    List<Account> findByOrderByOpeningBalanceDesc();

}
