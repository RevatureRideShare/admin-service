package com.revature.test;

import static org.junit.jupiter.api.Assertions.fail;

import com.revature.bean.Admin;
import com.revature.service.AdminServiceImpl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminServiceImplIntegrationTest {

  private AdminServiceImpl adminServiceImpl;
  private Admin newAdmin;
  private Admin existingAdmin;
  private Admin nonExistingAdmin;
  private Admin updatedAdmin;
  private Admin changedAdmin;
  private Admin nullAdmin;

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


  }

  @AfterEach
  void tearDown() throws Exception {

  }

  @Test
  void test() {
    fail("Not yet implemented");
  }

}
