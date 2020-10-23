package com.rvbb.b2b.rsocket.client.entity;

import lombok.*;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="phone")
    private String phone;

    @Column(name="age")
    private int age;

    @Column(name="created")
    private Date created;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
