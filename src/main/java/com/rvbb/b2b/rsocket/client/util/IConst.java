package com.rvbb.b2b.rsocket.client.util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IConst {

	Map<Integer, String> BANKLISTDEMO_TYPES = Stream
			.of(new Object[][] { { 1, "private" }, { 2, "public"}, })
			.collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

	String LOGGER_REQUEST_URL = "url not recognized";

	String RESPONSE_OK = "OK";
	String RESPONSE_UNKNOWN = "Unknown";
}
