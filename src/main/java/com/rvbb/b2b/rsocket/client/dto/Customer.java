package com.rvbb.b2b.rsocket.client.dto;

import com.rvbb.b2b.rsocket.client.entity.CustomerEntity;
import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private String phone;
    private int age;

    public static Customer of(CustomerEntity entity){
        return Customer.builder().id(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .age(entity.getAge()).build();
    }
}
