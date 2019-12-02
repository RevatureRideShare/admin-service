package com.revature.service;

import com.revature.bean.Admin;
import com.revature.exception.DeleteNonexistentException;
import com.revature.exception.UpdateNonexistentException;
import com.revature.repo.AdminRepo;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
 * @author Roberto Rodriguez
 */
@Service
public class AdminServiceImpl implements AdminService {

  private AdminRepo adminRepo;

  private static Logger log = Logger.getLogger("AdminServiceImpl");

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
    log.info("Inside AdminServiceImpl's createAdmin method with admin " + admin);
    if (getAdminByID(admin.getAdminID()).isPresent()) {
      log.info("Admin is causing a DuplicateKeyException");
      throw new DuplicateKeyException("Object already exists in database");
    } else {
      try {
        log.info("Successfully creating admin " + admin);
        return adminRepo.save(admin);
      } catch (TransactionSystemException t) {
        Throwable myT = t.getCause().getCause();

        if (myT instanceof ConstraintViolationException) {
          log.info("Admin is throwing ConstraintViolationException");
          throw ((ConstraintViolationException) myT);
        }
        log.info("Admin is throwing another type of TransactionSystemException");
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
    log.info("Inside AdminServiceImpl's deleteAdmin method with admin " + admin);
    if (!getAdminByID(admin.getAdminID()).isPresent()) {
      log.info("Admin is throwing a DeleteNonExistentException");
      throw new DeleteNonexistentException(admin + " does not exist and cannot be deleted");
    } else {
      log.info("Admin " + admin + " is successfully being deleted");
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
    log.info("Inside AdminServiceImpl's updateAdmin with admin " + admin);
    if (!getAdminByID(admin.getAdminID()).isPresent()) {
      log.info("Admin is throwing an UpdateNonexistentException");
      throw new UpdateNonexistentException(admin + " does not exist and cannot be updated");
    } else {
      try {
        log.info("Admin " + admin + " is successfully being updated");
        return adminRepo.save(admin);
      } catch (TransactionSystemException t) {
        Throwable myT = t.getCause().getCause();

        if (myT instanceof ConstraintViolationException) {
          log.info("Admin is throwing a ConstraintViolationException");
          throw ((ConstraintViolationException) myT);
        }
        log.info("Admin is throwing another type of TransactionSystemException");
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
    log.info("Inside AdminServiceImpl's getAdminByID with admin ID of " + adminID);
    log.info("Admin is: " + adminRepo.findById(adminID));
    return adminRepo.findById(adminID);
  }

  /**
   * This method is used for retrieving all Admins in the database.
   */
  @Override
  public List<Admin> getAllAdmins() {
    log.info("Inside AdminServiceImpl's getAllAdmins in the database");
    log.info("Admin list is: " + adminRepo.findAll());
    return adminRepo.findAll();
  }

  /**
   * This method is used for retrieving an Admin based on the Admin's email that is passed in. If an
   * Admin with the specified email exists in the database, the method should return the Admin.
   * Otherwise, it returns a null object.
   */
  @Override
  public Admin getAdminByEmail(String email) {
    log.info("Inside AdminServicEImpl's getAdminByEmail with email " + email);
    log.info("Admin is: " + adminRepo.findByEmail(email));
    return adminRepo.findByEmail(email);
  }
}
