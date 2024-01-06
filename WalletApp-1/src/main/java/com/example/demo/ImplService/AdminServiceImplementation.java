package com.example.demo.ImplService;

import com.example.demo.Model.AdminTable;
import com.example.demo.Repo.AdminRepo;
import com.example.demo.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImplementation implements AdminService {

    @Autowired
    AdminRepo adminRepo;

    @Override
    public AdminTable addAdminToDatabase(AdminTable adminTable) {
        return adminRepo.save(adminTable);
    }

    ;

    @Override
    public List<AdminTable> getAllAdminByAdminNameAndAdminPassword(String adminName, String adminPassword) {
        return adminRepo.findByAdminNameAndAdminPassword(adminName, adminPassword);
    }

    ;

}
