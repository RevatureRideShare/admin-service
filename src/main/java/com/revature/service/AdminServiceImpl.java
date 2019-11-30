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
import org.springframework.transaction.TransactionSystemException;

/**
 * This class contains all of the business logic for Admin, as well as calling the appropriate
 * AdminRepo methods. It implements the AdminService interface.
 * 
 * @author Jane Shin
 * @author Erik Haklar
 * @author Roberto Rodriguez
 */
@Service
public class AdminServiceImpl implements AdminService {

  private AdminRepo adminRepo;

  @Autowired
  public void setAdminRepo(AdminRepo adminRepo) {
    this.adminRepo = adminRepo;
  }

  /**
   * This method is used for creating an Admin by taking in an Admin object. If the id/primary key
   * already exists in the database, then this method throws a DuplicateKeyException. If the Admin
   * passed in is null, then the method should throw a NullPointerException because you cannot use
   * getAdminID() on a null object. If any of the bean validation on the Admin passed in is
   * violated, then the method should throw a ConstraintViolationException.
   */
  @Override
  public Admin createAdmin(Admin admin) throws NullPointerException {
    if (getAdminByID(admin.getAdminID()).isPresent()) {
      throw new DuplicateKeyException("Object already exists in database");
    } else {
      try {
        return adminRepo.save(admin);
      } catch (TransactionSystemException t) {
        Throwable myT = t.getCause().getCause();

        if (myT instanceof ConstraintViolationException) {
          throw ((ConstraintViolationException) myT);
        }
        throw t;
      }
    }
  }

  /**
   * This method is used for deleting an Admin by taking in an Admin object. If an Admin with the
   * id/primary key does not exist in the database, then this method throws a custom
   * DeleteNonexistentException. If the Admin passed in is null, then the method should throw a
   * NullPointerException because you cannot use getAdminID() on a null object.
   */
  @Override
  public void deleteAdmin(Admin admin) throws NullPointerException {
    if (!getAdminByID(admin.getAdminID()).isPresent()) {
      throw new DeleteNonexistentException(admin + " does not exist and cannot be deleted");
    } else {
      adminRepo.delete(admin);
    }
  }

  /**
   * This method is used for updating an Admin by taking in an Admin object. If the id/primary key
   * does not exist in the database, then this method throws a custom UpdateNonexistentException. If
   * the Admin passed in is null, then the method should throw a NullPointerException because you
   * cannot use getAdminID() on a null object. If any of the bean validation on the Admin passed in
   * is violated, then the method should throw a ConstraintViolationException.
   */
  @Override
  public Admin updateAdmin(Admin admin) throws NullPointerException {
    if (!getAdminByID(admin.getAdminID()).isPresent()) {
      throw new UpdateNonexistentException(admin + " does not exist and cannot be updated");
    } else {
      try {
        return adminRepo.save(admin);
      } catch (TransactionSystemException t) {
        Throwable myT = t.getCause().getCause();

        if (myT instanceof ConstraintViolationException) {
          throw ((ConstraintViolationException) myT);
        }
        throw t;
      }
    }
  }

  /**
   * This method is used for retrieving an Admin based on the adminID that is passed in. If an Admin
   * with the specified id/primary key exists in the database, the method should return an Optional
   * of that object. Otherwise, it returns an empty Optional.
   */
  @Override
  public Optional<Admin> getAdminByID(int adminID) {
    return adminRepo.findById(adminID);
  }

  /**
   * This method is used for retrieving all Admins in the database.
   */
  @Override
  public List<Admin> getAllAdmins() {
    return adminRepo.findAll();
  }

  @Override
  public Admin getAdminByEmail(String email) {
    return adminRepo.findByEmail(email);
  }

}
