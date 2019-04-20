package com.rohan90.majdoor.api.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestResponse<T> {

	@JsonProperty("message")
	private String message;
	@JsonProperty("status")
	private boolean status;
	@JsonProperty("data")
	private T data;
	@JsonProperty("error")
	private ApiError error;

	
	public RestResponse(boolean status,String message, T data) {
		super();
		this.message = message;
		this.status = status;
		this.data = data;
	}

	public RestResponse(ApiError error) {
		this.error = error;
		this.status = false;
		this.message = error.getMessage();
	}

	public static <T> RestResponse<T> ok(T data) {
		return new RestResponse<T>(true,null,data);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ApiError getError() {
		return error;
	}

	public void setError(ApiError error) {
		this.error = error;
	}
	
}
