package com.revature.service;

import com.revature.bean.Admin;
import com.revature.repo.AdminRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  private AdminRepo adminRepo;

  @Autowired
  public void setAdminRepo(AdminRepo adminRepo) {
    this.adminRepo = adminRepo;
  }

  @Override
  public Admin createAdmin(Admin admin) {
    Admin returnAdmin = adminRepo.save(admin);
    return returnAdmin;
  }

  @Override
  public void deleteAdmin(Admin admin) {

  }

  @Override
  public Admin updateAdmin(Admin admin) {
    return null;
  }

  @Override
  public Optional<Admin> getAdmin(int adminID) {
    return null;
  }

  @Override
  public List<Admin> getAllAdmins() {
    return null;
  }
}
