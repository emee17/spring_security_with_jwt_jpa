package com.coderland.jpajwt.utility;
 

import java.util.*;

import com.google.common.collect.Sets;

public enum ApiRole {

	
	ADMIN(Sets.newHashSet(ApiPermissions.CODER_READ, ApiPermissions.CODER_WRITE)), //Guavas -- new HashSet<ApiPermissions>(Arrays.asList(ApiPermissions.CODER_READ, ApiPermissions.CODER_WRITE))
	USER(Sets.newHashSet(ApiPermissions.CODER_READ));//Guavas -- new HashSet<ApiPermissions>(Arrays.asList(ApiPermissions.CODER_READ))
	
	
	
	public final Set<ApiPermissions> permissions;
	
	private ApiRole(Set<ApiPermissions> permissions) {
		this.permissions = permissions;
	}
	
	public Set<ApiPermissions> getPermissions(){
		return permissions;
	}
	

	
	
	
	
	
	
}
