package com.coderland.jpajwt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sec_role")
public class Role  implements GrantedAuthority{
	
	/**
	 * Default Auto Generated
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="role_seq" )
	@SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1, initialValue = 1)
	private Long Id;

	private String role;
	
	@Column(name = "coder_id")
	private Long coderId;

	@Override
	public String getAuthority() {
		return role;
	}
}
