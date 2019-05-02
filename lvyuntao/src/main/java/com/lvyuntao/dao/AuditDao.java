package com.lvyuntao.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lvyuntao.dao.basedao.SqlMapper;
import com.lvyuntao.dao.mapper.AuditMapper;
import com.lvyuntao.model.base.BasePage;
import com.lvyuntao.model.table.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by SF on 2019/4/21.
 */
@Component
public class AuditDao {
    @Autowired
    private SqlMapper sqlMapper;
    @Autowired
    private AuditMapper auditMapper;
    public BasePage<Audit> getPageByKeyWord(Integer pagesize,Integer pageNo,String keyWord){

        Example example=new Example(Audit.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.orLike("status",keyWord);
        criteria.orLike("auditId",keyWord);
        criteria.orLike("personId",keyWord);
        PageHelper.startPage(pageNo,pagesize);
        PageInfo<Audit> pageInfo=new PageInfo(auditMapper.selectByExample(example));
        BasePage<Audit> basePage=new BasePage<>();
        basePage.initResult(pageInfo);
        return basePage;
    }
}
