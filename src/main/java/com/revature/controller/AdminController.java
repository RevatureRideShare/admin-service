package com.revature.controller;

import com.revature.bean.Admin;
import com.revature.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AdminController {

  private AdminService adminService;

  @Autowired
  public void setAdminService(AdminService adminService) {
    this.adminService = adminService;
  }

  @PostMapping("/admin")
  public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
    try {
      adminService.createAdmin(admin);
      return new ResponseEntity<>(admin, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/test")
  public String test() {
    System.out.println("Wow it worked!!!!!");
    return "Success";
  }

}
