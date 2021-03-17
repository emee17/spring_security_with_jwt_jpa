package com.coderland.jpajwt.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderland.jpajwt.model.Coder;

@Repository
public interface CoderRepository extends JpaRepository<Coder, Long> {

	Optional<Coder> findByUsername(String username);
	
}
