package com.coderland.jpajwt.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderland.jpajwt.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
