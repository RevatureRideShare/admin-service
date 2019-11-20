package com.revature.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.bean.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, UUID>{

}
