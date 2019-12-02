package com.revature.controller;

import com.revature.bean.Admin;
import com.revature.service.AdminService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all of the controller logic for Admin, as well as calling the appropriate
 * AdminService methods.
 * 
 * @author Jane Shin
 * @author Roberto Rodriguez
 */
@RestController
@CrossOrigin
public class AdminController {
  private AdminService adminService;

  @Autowired
  public void setAdminService(AdminService adminService) {
    this.adminService = adminService;
  }

  private static Logger log = Logger.getLogger("AdminController");

  /**
   * This method is a RESTful endpoint that allows the creation of an admin. It returns a
   * ResponseEntity with the Admin passed in and HttpStatus.CREATED if successful. If it faces
   * ConstraintViolationException, DuplicateKeyException, or NullPointerException, it returns a
   * ResponseEntity with an error body containing an appropriate message, and
   * HttpStatus.BAD_REQUEST.
   * 
   * @param admin The admin being created
   * @return The ResponseEntity object with appropriate body and HTTP status code
   */
  @PostMapping("/admin")
  public ResponseEntity<?> createAdmin(@RequestBody(required = false) Admin admin) {
    log.info("Inside AdminController's POST /admin endpoint, trying to create " + admin);

    Admin newAdmin;
    Map<String, Object> error = new HashMap<>();

    try {
      newAdmin = adminService.createAdmin(admin);
      log.info(admin + " successfully created");
      return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    } catch (ConstraintViolationException c) {
      error.put("message", c.getMessage());
      log.info(admin + " causing ConstraintViolationException");
      return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    } catch (DuplicateKeyException d) {
      error.put("message", d.getMessage());
      log.info(admin + " causing DuplicateKeyException");
      return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    } catch (NullPointerException n) {
      error.put("message", "Cannot pass in a null Admin object");
      log.info(admin + " causing NullPointerException");
      return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * This method is a RESTful endpoint that returns all of the admins in the database. It returns a
   * ResponseEntity with that list and HttpStatus.OK.
   * 
   * @return The ResponseEntity object with a list of all admins and HTTP status code
   */
  @GetMapping("/admin")
  public ResponseEntity<List<Admin>> getAllAdmins() {
    log.info("Inside AdminController's GET /admin endpoint, trying to get all admins");
    List<Admin> allAdmins = adminService.getAllAdmins();

    log.info("Successfully returning all admins in database");
    return new ResponseEntity<>(allAdmins, HttpStatus.OK);
  }

  /**
   * Endpoint for getting a single admin based off of their email.
   * 
   * @param email Email of the admin.
   * @return Returns a response entity with either an ok or a no content Http status depending on
   *         the results.
   */
  @GetMapping("/admin/{email}")
  public ResponseEntity<?> getAdminByEmail(@PathVariable("email") String email) {
    log.info("Inside AdminController's GET /admin/{email} endpoint, trying admin with email of "
        + email);
    Admin admin = adminService.getAdminByEmail(email);

    if (admin != null) {
      log.info("Successfully returning " + admin + " in database");
      return new ResponseEntity<>(admin, HttpStatus.OK);
    } else {
      log.info("Admin with email " + email + " is not in database");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

}
