package com.reflektion.searchcontrol.controller;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringBootServerCodegen", date = "2016-06-29T19:31:03.612Z")
public class NotFoundException extends SearchControlApiException {
	private int code;
	public NotFoundException (int code, String msg) {
		super(code, msg);
		this.code = code;
	}
}
