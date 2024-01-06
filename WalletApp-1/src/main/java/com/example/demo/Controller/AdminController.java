package com.example.demo.Controller;

import com.example.demo.Model.AdminTable;
import com.example.demo.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/addAdminToDataBase")
    public ResponseEntity<AdminTable> addAdminData(@RequestBody AdminTable adminTable) {

        AdminTable adminTable1 = adminService.addAdminToDatabase(adminTable);
        if (adminTable1 != null) {
            return ResponseEntity.ok(adminTable1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    ;

    @GetMapping("/getAllAdminData/{adminName}/{adminPassword}")
    public ResponseEntity<List<AdminTable>> getAllAdminData(@PathVariable String adminName,
                                                            @PathVariable String adminPassword) {

        List<AdminTable> adminTableList = adminService.getAllAdminByAdminNameAndAdminPassword(adminName, adminPassword);

        if (adminTableList != null) {
            return ResponseEntity.ok(adminTableList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

};
