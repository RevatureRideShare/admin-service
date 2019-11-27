package com.revature.service;

import com.revature.bean.Admin;
import com.revature.exception.DeleteNonexistentException;
import com.revature.exception.UpdateNonexistentException;
import com.revature.repo.AdminRepo;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  private AdminRepo adminRepo;

  @Autowired
  public void setAdminRepo(AdminRepo adminRepo) {
    this.adminRepo = adminRepo;
  }

  @Override
  public Admin createAdmin(Admin admin)
      throws IllegalArgumentException, ConstraintViolationException {
    if (getAdmin(admin.getAdminID()).isPresent()) {
      throw new DuplicateKeyException("Object already exists in database");
    } else {
      return adminRepo.save(admin);
    }
  }

  @Override
  public void deleteAdmin(Admin admin) throws IllegalArgumentException {
    if (!getAdmin(admin.getAdminID()).isPresent()) {
      throw new DeleteNonexistentException(admin + " does not exist and cannot be deleted");
    } else {
      adminRepo.delete(admin);
    }
  }

  @Override
  public Admin updateAdmin(Admin admin)
      throws IllegalArgumentException, ConstraintViolationException {
    if (!getAdmin(admin.getAdminID()).isPresent()) {
      throw new UpdateNonexistentException(admin + " does not exist and cannot be updated");
    } else {
      return adminRepo.save(admin);
    }
  }

  @Override
  public Optional<Admin> getAdmin(int adminID) {
    return adminRepo.findById(adminID);
  }

  @Override
  public List<Admin> getAllAdmins() {
    return adminRepo.findAll();
  }
}
