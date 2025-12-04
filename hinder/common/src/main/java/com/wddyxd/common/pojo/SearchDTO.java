package com.wddyxd.common.pojo;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-04 18:34
 **/

public class SearchDTO {

    private Integer pageNum = 1;
    private Integer pageSize = 10 ;
    private String search = "";

    /**
     * 校验分页参数合法性
     * @param searchDTO 分页参数
     */
    public void validatePageParams(SearchDTO searchDTO) {
        if (searchDTO.getPageNum() == null || searchDTO.getPageNum() < 1) {
            searchDTO.setPageNum(1); // 非法页码默认设为第1页
        }
        if (searchDTO.getPageSize() == null || searchDTO.getPageSize() <= 0 || searchDTO.getPageSize() > 100) {
            searchDTO.setPageSize(10); // 非法页大小默认设为10，同时限制最大页大小（防止全表查询）
        }
    }

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
