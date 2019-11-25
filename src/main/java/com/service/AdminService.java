package com.service;

import com.revature.bean.Admin;
import com.revature.repo.AdminRepo;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

  private AdminRepo adminRepo;

  public AdminService(AdminRepo adminRepo) {
    this.adminRepo = adminRepo;
  }

  public Admin createAdmin(Admin admin) {
    Admin returnAdmin = adminRepo.save(admin);
    return returnAdmin;
  }

}
