package com.coderland.jpajwt.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sec_coder")
public class Coder {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coder_seq")
	@SequenceGenerator(sequenceName = "coder_seq", name = "coder_seq", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@NotNull(message = "Username must not be null")
	@Size(min = 3, message = "username should have atleast 3 characters")
	private String username;
	
	@NotNull(message = "Passwor must not be null")
	@Size(min = 4, message = "password should have atleast 4 characters")
	private String password;
	
	private boolean enabled;
	
	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)//targetEntity = Role.class,
	@JoinColumn(name = "coder_id", referencedColumnName = "id" )//, table = "sec_role"
	private Set<Role> roles;
}
