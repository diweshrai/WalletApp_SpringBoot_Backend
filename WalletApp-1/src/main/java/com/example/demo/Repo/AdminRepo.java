package com.example.demo.Repo;

import com.example.demo.Model.AdminTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepo extends JpaRepository<AdminTable, Integer> {

//    private String adminName;
//    private String adminPassword;

    List<AdminTable> findByAdminNameAndAdminPassword(String adminName, String adminPassword);

}
