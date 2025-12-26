package com.wddyxd.orderservice.pojo.VO;


import com.wddyxd.orderservice.pojo.entity.OrderAddress;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;

import java.util.HashMap;
import java.util.List;

/**
 * @program: items-assigner
 * @description: 订单详情响应体
 * @author: wddyxd
 * @create: 2025-12-03 12:20
 **/

public class OrderDetailVO {

    private OrderMain orderMain;

    private HashMap<String,UserProfileVO> userProfileVOMap;

    private ProductProfileVO productProfileVO;

//    在另外的接口获取
//    private UserCouponVO userCouponVO;

    private OrderAddress orderAddress;

//    在另外的接口获取
//    private List<OrderStatusLog> orderStatusLogs;


    @Override
    public String toString() {
        return "OrderDetailVO{" +
                "orderMain=" + orderMain +
                ", userProfileVOMap=" + userProfileVOMap +
                ", productProfileVO=" + productProfileVO +
                ", orderAddress=" + orderAddress +
                '}';
    }

    public OrderMain getOrderMain() {
        return orderMain;
    }

    public void setOrderMain(OrderMain orderMain) {
        this.orderMain = orderMain;
    }

    public HashMap<String, UserProfileVO> getUserProfileVOMap() {
        return userProfileVOMap;
    }

    public void setUserProfileVOMap(HashMap<String, UserProfileVO> userProfileVOMap) {
        this.userProfileVOMap = userProfileVOMap;
    }

    public ProductProfileVO getProductProfileVO() {
        return productProfileVO;
    }

    public void setProductProfileVO(ProductProfileVO productProfileVO) {
        this.productProfileVO = productProfileVO;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    public enum userProfileVOMapKeys {
        buyer( "buyer"),
         merchant( "merchant");

        private final String key;
         userProfileVOMapKeys(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

}
