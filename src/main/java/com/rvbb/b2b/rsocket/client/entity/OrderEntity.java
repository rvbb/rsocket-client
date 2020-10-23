package com.rvbb.b2b.rsocket.client.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_demo")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="order_date")
    private Date orderDate;

    @Column(name="total")
    private Float total;

    @Column(name="customerId")
    private Long customerId;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
