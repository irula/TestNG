package com.weibo.service.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User ID request info.
 *
 * @author	Bert Lee
 * @version 2014-8-19
 */
public class UserIDRequest extends BaseRequest {

	@JsonProperty("id")
	@NotNull(message = "id param is null")
	@Min(value = 1, message = "id param must be great or equal than \\{{value}\\}") // 4.3. Message interpolation -《JSR 303: Bean Validation》
	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserIDRequest [id=" + id + "]";
	}

}
