package com.lvyuntao.model.base;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SF on 2019/2/19.
 */
@Data
public class BasePage<T> implements Serializable {
    @ApiModelProperty("列表信息")
    private List<T> list;
    @ApiModelProperty("总数量")
    private Integer totalCount;
    @ApiModelProperty("总页数")
    private Integer totalPage;
    @ApiModelProperty("返回的其他数据(一般不用)")
    private Object extra;


    public void initResult(List<T> list, Integer totalCount, Integer pageSize) {
        this.list = list;
        this.totalCount = totalCount;
        if (pageSize > 0) {
            this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        }
    }

    public BasePage initResult(PageInfo<T> pageInfo) {
        if (pageInfo == null) {
            return this;
        }
        this.list = pageInfo.getList();
        this.totalCount = (int) pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
        return this;
    }


}
