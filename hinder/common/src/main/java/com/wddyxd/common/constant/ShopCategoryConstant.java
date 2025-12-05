package com.wddyxd.common.constant;

public enum ShopCategoryConstant {

    DEFAULT(1996954588037107713L,"默认分类"),
    BOOK_STORE(1996954588678836226L,"书店"),
    FOOD(1996954588678836227L,"食品");

    private Long id;
    private String name;

    ShopCategoryConstant(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
