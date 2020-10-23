package com.rvbb.b2b.rsocket.client.dto;

import com.rvbb.b2b.rsocket.client.dto.response.CustomizedCustomerResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.Date;

@Builder
@Setter
@Getter
public class DetailOrder {

	private Long id;

	private Date orderDate;

	private Float total;

//	private Customer customer; -- unknow error: two classes return tu Customer
	private CustomizedCustomerResponse cust;

	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}
}
