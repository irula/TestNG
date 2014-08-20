package com.weibo.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User name response info.
 *
 * @author	Bert Lee
 * @version 2014-8-19
 */
public class UserNameResponse {

	@JsonProperty("name")
	protected String name = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserNameResponse [name=" + name + "]";
	}

}
