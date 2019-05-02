package com.lvyuntao.model.table;

import lombok.Data;

import javax.persistence.Id;

/**
 * Created by SF on 2019/4/21.
 */
@Data
public class Car {
    @Id
    private String carId;
    private String idCard;
    private String type;
    private Long periodValidity;
    private Integer standardIf;
}
