package com.lvyuntao.service.login;

import com.lvyuntao.model.table.Admin;

/**
 * Created by SF on 2019/4/20.
 */
public interface LoginService {
    Admin adminLogin(String userId,String passWord);
}
