package com.coderland.jpajwt.utility;

public enum ApiPermissions {
	
	CODER_READ("coder:read"),
	CODER_WRITE("coder:write");

	public  final String permissions;
	
	ApiPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	public String getPermissions() {
		return permissions;
	}
}
