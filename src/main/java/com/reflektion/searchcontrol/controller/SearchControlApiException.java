package com.reflektion.searchcontrol.controller;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringBootServerCodegen", date = "2016-06-29T19:31:03.612Z")
public class SearchControlApiException extends Exception{
	private int code;
	public SearchControlApiException(int code, String msg) {
		super(msg);
		this.code = code;
	}
}
