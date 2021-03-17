package com.coderland.jpajwt.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderland.jpajwt.model.Coder;
import com.coderland.jpajwt.services.MyCoderDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/coder")
public class CoderResource {
	
	@Autowired private MyCoderDetailService myCoderDetailService;

	@GetMapping("/")  // Accessible by Admin  and User
	public ResponseEntity<?> allCoder() {
		log.info("insider detailCoder allCoder");
		return ResponseEntity.ok(myCoderDetailService.getAll());
	}
	
	@GetMapping("/{id}")  // Accessible by Admin  and user
	public ResponseEntity<?> detailCoder(@PathVariable("id") Long id)  {
		log.info("insider detailCoder "+id);
		Coder coder = myCoderDetailService.getyId(id);
		return ResponseEntity.ok(coder);
	}
	
	@PostMapping("/")  // Accessible by Admin only
	public ResponseEntity<?> createCoder(@RequestBody Coder coder) {
		log.info("insider createCoder");
		return ResponseEntity.ok(coder);
	}
	
	@PutMapping("/{id}")  // Accessible by Admin only
	public ResponseEntity<?> modifyCoder(@RequestBody Coder coder ,@PathVariable("id") Long id) {
		log.info("insider modify "+ id);
		return ResponseEntity.ok(coder);
	}
	
	@DeleteMapping("/{id}")  // Accessible by Admin only
	public String deleteCoder(@PathVariable("id") Long id) {
		log.info("insider deleteCoder" + id);
		return "insider deleteCoder" + id;
	}
	

}
