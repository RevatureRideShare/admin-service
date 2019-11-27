package com.revature.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.revature.bean.Admin;
import com.revature.exception.DeleteNonexistentException;
import com.revature.exception.UpdateNonexistentException;
import com.revature.repo.AdminRepo;
import com.revature.service.AdminServiceImpl;

import java.util.LinkedList;
import java.util.List;
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
    List<Admin> existingList = new LinkedList<>();
    existingList.add(existingAdmin);
    when(adminRepo.findAll()).thenReturn(existingList);
    assertEquals(existingList, adminServiceImpl.getAllAdmins());
    verify(adminRepo).findAll();
  }

  @Test
  void testDeleteNewAdmin() {
    when(adminRepo.findById(newAdmin.getAdminID())).thenReturn(Optional.empty());
    Assertions.assertThrows(DeleteNonexistentException.class, () -> {
      adminServiceImpl.deleteAdmin(newAdmin);
    });
  }

  @Test
  void testDeleteExistingAdmin() {
    when(adminRepo.findById(existingAdmin.getAdminID())).thenReturn(Optional.of(existingAdmin));
    List<Admin> existingList = adminServiceImpl.getAllAdmins();
    adminServiceImpl.deleteAdmin(existingAdmin);
    List<Admin> afterDeletingList = adminServiceImpl.getAllAdmins();
    existingList.remove(existingAdmin);

    assertEquals(existingList, afterDeletingList);
    verify(adminRepo).delete(existingAdmin);
  }

  @Test
  void testDeleteNullAdmin() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      adminServiceImpl.deleteAdmin(nullAdmin);
    });
  }

  @Test
  void testDeleteBadFormatAdmin() {
    when(adminRepo.findById(badFormatAdmin.getAdminID())).thenReturn(Optional.empty());
    Assertions.assertThrows(DeleteNonexistentException.class, () -> {
      adminServiceImpl.deleteAdmin(badFormatAdmin);
    });
  }

  @Test
  void testUpdateNewAdmin() {
    when(adminRepo.findById(newAdmin.getAdminID())).thenReturn(Optional.empty());

    Assertions.assertThrows(UpdateNonexistentException.class, () -> {
      adminServiceImpl.updateAdmin(newAdmin);
    });
  }

  @Test
  void testUpdateExistingAdmin() {
    Admin updatedExistingAdmin =
        new Admin(existingAdmin.getAdminID(), "updatedadmin@gmail.com", "UFirst", "ULast", true);

    when(adminRepo.findById(updatedExistingAdmin.getAdminID()))
        .thenReturn(Optional.of(existingAdmin));

    when(adminRepo.save(updatedExistingAdmin)).thenReturn(updatedExistingAdmin);

    assertEquals(updatedExistingAdmin, adminServiceImpl.updateAdmin(updatedExistingAdmin));

    verify(adminRepo).save(updatedExistingAdmin);
  }

  @Test
  void testUpdateNullAdmin() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      adminServiceImpl.updateAdmin(nullAdmin);
    });
  }

  @Test
  void testUpdateBadFormatAdmin() {
    Admin updatedBadAdmin = new Admin(existingAdmin.getAdminID(), "", "", "", true);

    when(adminRepo.findById(updatedBadAdmin.getAdminID())).thenReturn(Optional.of(existingAdmin));

    when(adminRepo.save(updatedBadAdmin)).thenThrow(ConstraintViolationException.class);

    Assertions.assertThrows(ConstraintViolationException.class, () -> {
      adminServiceImpl.updateAdmin(updatedBadAdmin);
    });

    verify(adminRepo).save(updatedBadAdmin);
  }
}
