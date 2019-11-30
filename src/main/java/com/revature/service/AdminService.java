package com.revature.service;

import com.revature.bean.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
  public Admin createAdmin(Admin admin) throws NullPointerException;

  public void deleteAdmin(Admin admin) throws NullPointerException;

  public Admin updateAdmin(Admin admin) throws NullPointerException;

  public Optional<Admin> getAdminByID(int adminID);

  public Admin getAdminByEmail(String email);

  public List<Admin> getAllAdmins();
}
