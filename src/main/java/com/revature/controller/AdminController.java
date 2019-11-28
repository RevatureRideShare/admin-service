package com.revature.controller;

import com.revature.bean.Admin;
import com.revature.service.AdminService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all of the controller logic for Admin, as well as calling the appropriate
 * AdminService methods.
 * 
 * @author Jane Shin
 * @author Erik Haklar
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
    Admin newAdmin;
    Map<String, Object> error = new HashMap<>();

    try {
      newAdmin = adminService.createAdmin(admin);
      return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    } catch (ConstraintViolationException c) {
      error.put("message", c.getMessage());
      return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    } catch (DuplicateKeyException d) {
      error.put("message", d.getMessage());
      return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    } catch (NullPointerException n) {
      error.put("message", "Cannot pass in a null Admin object");
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
    List<Admin> allAdmins = adminService.getAllAdmins();

    return new ResponseEntity<>(allAdmins, HttpStatus.OK);
  }

  /*
   * Leaving this blank until we figure out AMQ
   * 
   * @DeleteMapping("/user/{email}") public ResponseEntity<?> deleteUser(@PathVariable("email")
   * String email) { return null; }
   */
}
