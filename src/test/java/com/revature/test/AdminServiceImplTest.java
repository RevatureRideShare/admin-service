package com.revature.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.revature.bean.Admin;
import com.revature.repo.AdminRepo;
import com.revature.service.AdminServiceImpl;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

@SpringBootTest
class AdminServiceImplTest {
  @Mock
  AdminRepo adminRepo;

  @InjectMocks
  AdminServiceImpl adminServiceImpl = new AdminServiceImpl();

  private Admin newAdmin;

  private Admin existingAdmin;

  private Admin nullAdmin;

  private Admin badFormatAdmin;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    newAdmin = new Admin(1, "newadmin@gmail.com", "NewFirst", "NewLast", true);

    existingAdmin = new Admin(2, "existingadmin@gmail.com", "First", "Last", true);

    nullAdmin = null;

    badFormatAdmin = new Admin(3, "", "", "", true);
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void testCreateNewAdmin() {
    when(adminRepo.findById(newAdmin.getAdminID())).thenReturn(Optional.empty());
    when(adminRepo.save(newAdmin)).thenReturn(newAdmin);
    assertEquals(newAdmin, adminServiceImpl.createAdmin(newAdmin));
    verify(adminRepo).save(newAdmin);
  }

  @Test
  void testCreateExistingAdmin() {
    when(adminRepo.findById(existingAdmin.getAdminID())).thenReturn(Optional.of(existingAdmin));
    Assertions.assertThrows(DuplicateKeyException.class, () -> {
      adminServiceImpl.createAdmin(existingAdmin);
    });
  }

  @Test
  void testCreateNullAdmin() {
    when(adminRepo.save(nullAdmin)).thenThrow(IllegalArgumentException.class);
    Assertions.assertThrows(NullPointerException.class, () -> {
      adminServiceImpl.createAdmin(nullAdmin);
    });
  }

  @Test
  void testCreateBadFormatAdmin() {
    when(adminRepo.findById(badFormatAdmin.getAdminID())).thenReturn(Optional.empty());
    when(adminRepo.save(badFormatAdmin)).thenThrow(ConstraintViolationException.class);
    Assertions.assertThrows(ConstraintViolationException.class, () -> {
      adminServiceImpl.createAdmin(badFormatAdmin);
    });
    verify(adminRepo).save(badFormatAdmin);
  }

  @Test
  void testGetNewAdmin() {
    when(adminRepo.findById(newAdmin.getAdminID())).thenReturn(Optional.empty());
    assertEquals(Optional.empty(), adminServiceImpl.getAdmin(newAdmin.getAdminID()));
    verify(adminRepo).findById(newAdmin.getAdminID());
  }

  @Test
  void testGetExistingAdmin() {
    when(adminRepo.findById(existingAdmin.getAdminID())).thenReturn(Optional.of(existingAdmin));
    assertEquals(Optional.of(existingAdmin), adminServiceImpl.getAdmin(existingAdmin.getAdminID()));
    verify(adminRepo).findById(existingAdmin.getAdminID());
  }

  @Test
  void testGetNullAdmin() {
    when(adminRepo.findById(null)).thenThrow(IllegalArgumentException.class);
    Assertions.assertThrows(NullPointerException.class, () -> {
      adminServiceImpl.getAdmin(nullAdmin.getAdminID());
    });
  }

  @Test
  void testGetBadFormatAdmin() {
    when(adminRepo.findById(badFormatAdmin.getAdminID())).thenReturn(Optional.empty());
    assertEquals(Optional.empty(), adminServiceImpl.getAdmin(badFormatAdmin.getAdminID()));
    verify(adminRepo).findById(badFormatAdmin.getAdminID());
  }

  @Test
  void testGetAllAdmins() {

  }

}
