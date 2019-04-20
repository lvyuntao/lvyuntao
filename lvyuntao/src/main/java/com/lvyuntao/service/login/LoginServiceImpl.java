package com.lvyuntao.service.login;

import com.lvyuntao.Parmsexception;
import com.lvyuntao.dao.mapper.AdminMapper;
import com.lvyuntao.model.table.Admin;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SF on 2019/4/20.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin adminLogin(String userId, String passWord) {
        if(StringUtils.isEmpty(userId)) throw new Parmsexception("账号不能为空");
        if(StringUtils.isEmpty(passWord)) throw new Parmsexception("密码不能为空");
        Admin admin=new Admin();
        admin.setAdminId(userId);
        admin.setPassword(passWord);
        Admin result = adminMapper.selectOne(admin);
        if(result==null)throw new Parmsexception("账号或密码错误");
        return result;
    }
}
