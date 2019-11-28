package com.revature.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.bean.Admin;
import com.revature.controller.AdminController;
import com.revature.service.AdminService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class AdminControllerTest {
  @Mock
  AdminService adminService;

  @InjectMocks
  AdminController adminController;

  private MockMvc mvc;

  private Admin newAdmin;

  private Admin existingAdmin;

  private Admin nullAdmin;

  private Admin badFormatAdmin;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {

  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {

  }

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.mvc = MockMvcBuilders.standaloneSetup(adminController).build();

    newAdmin = new Admin(1, "newadmin@gmail.com", "NewFirst", "NewLast", true);
    existingAdmin = new Admin(2, "existingadmin@gmail.com", "First", "Last", true);
    nullAdmin = null;
    badFormatAdmin = new Admin(3, "", "", "", true);
  }

  @AfterEach
  void tearDown() throws Exception {

  }

  @Test
  void testCreateNewAdmin() throws JsonProcessingException, Exception {
    when(adminService.createAdmin(newAdmin)).thenReturn(newAdmin);
    mvc.perform(MockMvcRequestBuilders.post("/admin").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newAdmin))
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(newAdmin)));
  }

  @Test
  void testCreateExistingAdmin() throws JsonProcessingException, Exception {
    when(adminService.createAdmin(existingAdmin))
        .thenThrow(new DuplicateKeyException("Object already exists in database"));

    Map<String, Object> error = new HashMap<>();
    error.put("message", "Object already exists in database");

    mvc.perform(MockMvcRequestBuilders.post("/admin").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(existingAdmin))
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(error)));
  }

  @Test
  void testCreateNullAdmin() throws JsonProcessingException, Exception {
    when(adminService.createAdmin(nullAdmin)).thenThrow(NullPointerException.class);

    Map<String, Object> error = new HashMap<>();
    error.put("message", "Cannot pass in a null Admin object");

    mvc.perform(MockMvcRequestBuilders.post("/admin").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(nullAdmin))
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(error)));
  }

  @Test
  void testCreateBadFormatAdmin() throws JsonProcessingException, Exception {
    when(adminService.createAdmin(badFormatAdmin)).thenThrow(ConstraintViolationException.class);

    mvc.perform(MockMvcRequestBuilders.post("/admin").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(badFormatAdmin))
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
  }

  @Test
  void testGetAllAdmins() throws Exception {
    List<Admin> existingList = new LinkedList<>();
    existingList.add(existingAdmin);

    when(adminService.getAllAdmins()).thenReturn(existingList);
    mvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(existingList)));
  }
}
