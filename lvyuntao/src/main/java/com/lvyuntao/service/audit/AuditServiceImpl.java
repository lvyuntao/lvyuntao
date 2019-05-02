package com.lvyuntao.service.audit;

import com.lvyuntao.dao.AuditDao;
import com.lvyuntao.model.base.BasePage;
import com.lvyuntao.model.table.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SF on 2019/4/21.
 */
@Service
public class AuditServiceImpl implements AuditService {
    @Autowired
    AuditDao auditDao;
    @Override
    public BasePage<Audit> getPage(Integer pageSize, Integer pageNo, String keyWord) {
        return auditDao.getPageByKeyWord(pageSize,pageNo,keyWord);
    }
}
