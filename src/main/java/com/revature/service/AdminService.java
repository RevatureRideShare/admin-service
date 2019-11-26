package com.revature.service;

import com.revature.bean.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
  public Admin createAdmin(Admin admin);

  public void deleteAdmin(Admin admin);

  public Admin updateAdmin(Admin admin);

  public Optional<Admin> getAdmin(int adminID);

  public List<Admin> getAllAdmins();
}
