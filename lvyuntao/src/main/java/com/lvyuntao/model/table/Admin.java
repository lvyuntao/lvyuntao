package com.lvyuntao.model.table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

/**
 * Created by SF on 2019/4/20.
 */
@Getter
@Setter
public class Admin {
    @Id
    private String adminId;
    private String password;
    private Integer levelId;

}
