package com.lvyuntao.model.table;

import lombok.Data;

import javax.persistence.Id;

/**
 * Created by SF on 2019/4/21.
 */
@Data
public class Person {
    @Id
    private String personId;
    private String name;
    private String sex;
    private Integer age;
    private String idCard;
    private String phone;
    private String address;
    private String otherPhone;
    private String creditMark;
    private String driverNumber;
    private String idCardA;
    private String idCardB;
    private String driverCardA;
    private String driverCardB;
}
