package com.revature.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.revature.bean.Admin;
import com.revature.exception.DeleteNonexistentException;
import com.revature.exception.UpdateNonexistentException;
import com.revature.service.AdminServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
class AdminServiceImplIntegrationTest {

  private AdminServiceImpl adminServiceImpl;
  private Admin newAdmin;
  private Admin existingAdmin;
  private Admin nonExistingAdmin;
  private Admin updatedAdmin;
  private Admin changedAdmin;
  private Admin nullAdmin;
  private Admin badFormatEmptyEmailAdmin;
  private Admin badFormatMaxSizeEmailAdmin;
  private Admin badFormatNotEmailAdmin;
  private Admin badFormatEmptyFirstNameAdmin;
  private Admin badFormatMaxSizeFirstNameAdmin;
  private Admin badFormatEmptyLastNameAdmin;
  private Admin badFormatMaxSizeLastNameAdmin;
  private Admin newAdminWithExistingEmail;

  @Autowired
  public void setAdminServiceImpl(AdminServiceImpl adminServiceImpl) {
    this.adminServiceImpl = adminServiceImpl;
  }

  @BeforeAll
  static void setUpBeforeClass() throws Exception {

  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {

  }

  @BeforeEach
  void setUp() throws Exception {
    nullAdmin = null;
    existingAdmin = new Admin(1, "admin@revature.com", "John", "Deer", true);
    updatedAdmin = new Admin(2, "admin2@revature.com", "Not", "Updated", true);
    nonExistingAdmin = new Admin(999, "admin@nonexistant,com", "Dont", "Existance", true);
    changedAdmin = new Admin(2, "admin2@revature.com", "I Am", "Updated", true);
    newAdmin = new Admin(4, "entertheadmin@revature.com", "Animatrix", "Woke", true);
    badFormatEmptyEmailAdmin = new Admin(20, "", "Nobody", "Jones", true);
    badFormatMaxSizeEmailAdmin =
        new Admin(20, "anAbsurdlyLongEmailThatShouldntBeThisLongButJustInCase@revature.com",
            "Nobody", "Jones", true);
    badFormatNotEmailAdmin = new Admin(20, "thisisntanemail", "Nobody", "Jones", true);
    badFormatEmptyFirstNameAdmin = new Admin(20, "amazingAdmin@admin.com", "", "Jones", true);
    badFormatMaxSizeFirstNameAdmin = new Admin(20, "amazingAdmin@admin.com",
        "This is my absurdly long first name ive had it ever since I was a child", "Jones", true);
    badFormatEmptyLastNameAdmin = new Admin(20, "amazingAdmin@admin.com", "Nobody", "", true);
    badFormatMaxSizeLastNameAdmin = new Admin(20, "amazingAdmin@admin.com", "Nobody",
        "My last name is so long I have no idea what to do with it, but I guess Ill live", true);
    newAdminWithExistingEmail = new Admin(10, "admin@revature.com", "John", "Deer", true);
  }

  @AfterEach
  void tearDown() throws Exception {

  }

  @Test
  @Sql("admin-script.sql")
  void testGetExistingAdminById() {
    assertEquals(adminServiceImpl.getAdminByID(existingAdmin.getAdminID()),
        Optional.of(existingAdmin));
  }

  @Test
  @Sql("admin-script.sql")
  void testGetAllTrainingLocation() {
    List<Admin> existingAdminList = new ArrayList<>();
    existingAdminList.add(existingAdmin);
    existingAdminList.add(updatedAdmin);
    assertEquals(adminServiceImpl.getAllAdmins(), existingAdminList);

    System.out.println(adminServiceImpl.getAllAdmins());
  }

  @Test
  @Sql("admin-script.sql")
  void testCreateNewAdmin() {
    Admin extraNewAdmin = adminServiceImpl.createAdmin(newAdmin);
    assertEquals(Optional.of(extraNewAdmin),
        adminServiceImpl.getAdminByID(extraNewAdmin.getAdminID()));
  }

  @Test
  @Sql("admin-script.sql")
  void testCreateExistingAdmin() {
    assertThrows(DuplicateKeyException.class, () -> {
      adminServiceImpl.createAdmin(existingAdmin);
    });
  }

  @Test
  void testCreateNullAdmin() {
    assertThrows(NullPointerException.class, () -> {
      adminServiceImpl.createAdmin(nullAdmin);
    });
  }

  @Test
  @Sql("admin-script.sql")
  void testDeleteExistingAdmin() {
    adminServiceImpl.deleteAdmin(existingAdmin);
    assertEquals(adminServiceImpl.getAdminByID(existingAdmin.getAdminID()), Optional.empty());
  }

  @Test
  void testDeleteNonExistingAdmin() {
    assertThrows(DeleteNonexistentException.class, () -> {
      adminServiceImpl.deleteAdmin(nonExistingAdmin);
    });
  }

  @Test
  @Sql("admin-script.sql")
  void testUpdateExistingAdmin() {
    System.out.println(
        "Current state of updatedAdmin" + adminServiceImpl.getAdminByID(updatedAdmin.getAdminID()));
    adminServiceImpl.updateAdmin(changedAdmin);
    assertEquals(adminServiceImpl.getAdminByID(changedAdmin.getAdminID()),
        Optional.of(changedAdmin));
  }

  @Test
  void testUpdateNonExistingAdmin() {
    assertThrows(UpdateNonexistentException.class, () -> {
      adminServiceImpl.updateAdmin(nonExistingAdmin);
    });
  }

  @Test
  void testCreateBadFormatEmptyEmailAdmin() {
    assertThrows(ConstraintViolationException.class, () -> {
      adminServiceImpl.createAdmin(badFormatEmptyEmailAdmin);
    });
  }

  @Test
  void testCreateBadFormatMaxSizeEmailAdmin() {
    assertThrows(ConstraintViolationException.class, () -> {
      adminServiceImpl.createAdmin(badFormatMaxSizeEmailAdmin);
    });
  }

  @Test
  void testCreateBadFormatNotAnEmailAdmin() {
    assertThrows(ConstraintViolationException.class, () -> {
      adminServiceImpl.createAdmin(badFormatNotEmailAdmin);
    });
  }

  @Test
  void testCreateBadFormatEmptyFirstNameAdmin() {
    assertThrows(ConstraintViolationException.class, () -> {
      adminServiceImpl.createAdmin(badFormatEmptyFirstNameAdmin);
    });
  }

  @Test
  void testCreateBadFormatMaxSizeFirstNameAdmin() {
    assertThrows(ConstraintViolationException.class, () -> {
      adminServiceImpl.createAdmin(badFormatMaxSizeFirstNameAdmin);
    });
  }

  @Test
  void testCreateBadFormatEmptyLastNameAdmin() {
    assertThrows(ConstraintViolationException.class, () -> {
      adminServiceImpl.createAdmin(badFormatEmptyLastNameAdmin);
    });
  }

  @Test
  void testCreateBadFormatMaxSizeLastNameAdmin() {
    assertThrows(ConstraintViolationException.class, () -> {
      adminServiceImpl.createAdmin(badFormatMaxSizeLastNameAdmin);
    });
  }

  @Test
  @Sql("admin-script.sql")
  void testCreateNewAdminWithSameEmail() {
    assertThrows(DataIntegrityViolationException.class, () -> {
      adminServiceImpl.createAdmin(newAdminWithExistingEmail);
    });
  }

  @Test
  @Sql("admin-script.sql")
  void testCreateGetAdminByEmail() {
    assertEquals(adminServiceImpl.getAdminByEmail(existingAdmin.getEmail()), existingAdmin);
  }


}
