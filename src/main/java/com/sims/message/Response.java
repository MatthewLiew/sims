package com.sims.message;

import org.springframework.stereotype.Service;

@Service
public class Response {
	
	private String status;
	private String flag;

	public Response () {
	}
	
	public Response(String status, String flag) {
		this.status = status;
		this.flag=flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
