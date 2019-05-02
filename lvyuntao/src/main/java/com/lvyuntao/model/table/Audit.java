package com.lvyuntao.model.table;

import lombok.Data;

import javax.persistence.Id;

/**
 * Created by SF on 2019/4/21.
 */
@Data
public class Audit {
    @Id
    private Integer personId;
    private String name;
    private String idCard;
    private String driverNumber;
    private Integer driverMark;
    private Long idCardValidity;
    private Long driverValidity;
    private String idCardA;
    private String idCardB;
    private String driverCardA;
    private String driverCardB;
    private String carId;
//    审核有效期
    private String periodValidity;
    private Integer standardIf;
    private String crimeEvent;
    private String traffic_event;
    private String status;
    private String auditId;
}
