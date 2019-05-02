package com.lvyuntao.service.audit;

import com.lvyuntao.model.base.BasePage;
import com.lvyuntao.model.table.Audit;

/**
 * Created by SF on 2019/4/21.
 */
public interface AuditService {
    BasePage<Audit> getPage(Integer pageSize,Integer pageNo,String keyWord);
}
