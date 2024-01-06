package com.example.demo.Service;

import com.example.demo.Model.AdminTable;

import java.util.List;

public interface AdminService {

    AdminTable addAdminToDatabase(AdminTable adminTable);

    List<AdminTable> getAllAdminByAdminNameAndAdminPassword(String adminName, String adminPassword);

}
