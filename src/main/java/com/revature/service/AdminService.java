package com.revature.service;

import com.revature.bean.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
  public Admin createAdmin(Admin admin) throws NullPointerException;

  public void deleteAdmin(Admin admin) throws NullPointerException;

  public Admin updateAdmin(Admin admin) throws NullPointerException;

  public Optional<Admin> getAdmin(int adminID);

  public List<Admin> getAllAdmins();

  /*
   * Will uncomment these methods when services communicate.
   */
  /*
   * public void deleteUser(User user);
   * 
   * public void setAccountStatus(int userID, boolean accountStatus);
   * 
   * public void setRole(int userID, Role role);
   */
}
