package com.wddyxd.common.pojo;


import com.wddyxd.common.paramValidateGroup.SelectGroup;
import jakarta.validation.constraints.Min;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-04 18:34
 **/

public class SearchDTO {

    @Min(value = 1, message = "页码不能小于1",groups = {SelectGroup.class})
    private Integer pageNum = 1;
    @Min(value = 1, message = "页大小不能小于1",groups = {SelectGroup.class})
    private Integer pageSize = 10 ;
    private String search = "";

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
