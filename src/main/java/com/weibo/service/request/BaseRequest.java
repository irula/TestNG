package com.weibo.service.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Base request info.
 *
 * @author	Bert Lee
 * @version 2014-8-19
 */
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略多传的参数
public class BaseRequest {

}
