package com.wddyxd.productservice.pojo.DTO;


import com.wddyxd.common.pojo.SearchDTO;

/**
 * @program: items-assigner
 * @description: 商品推送接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 20:41
 **/

public class ProductFeedDTO extends SearchDTO {

    private Long categoryId;

    private SortColumn sortColumn;

    private SortOrder sortOrder;

    @Override
    public String toString() {
        return "ProductFeedDTO{" +
                "categoryId=" + categoryId +
                ", sortColumn='" + sortColumn + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public SortColumn getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(SortColumn sortColumn) {
        this.sortColumn = sortColumn;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public enum SortColumn {
        sales("p.sales"),
        price("ps.price");

        private final String column;
        SortColumn(String column) {
            this.column = column;
        }
        public String getColumn() {
            return column;
        }
    }

    public enum SortOrder {
        ASC("asc"),
        DESC("desc");
        private final String order;
        SortOrder(String order) {
            this.order = order;
        }
        public String getOrder() {
            return order;
        }
    }


}
