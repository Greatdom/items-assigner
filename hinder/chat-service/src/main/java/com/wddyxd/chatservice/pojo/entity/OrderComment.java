package com.wddyxd.chatservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-24 01:00
 **/
@TableName("order_comment")
public class OrderComment implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long orderItemId;
    private Long productId;
    private Long skuId;
    private Long userId;
    private Integer score;//评分
    private String content;//评价内容
    private Boolean isFollowComment;//是否是追评
    private Long commentId;//被跟评的ID-启用时order_item_id失效
    private Boolean isAnonymous;//是否匿名
    private Boolean isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    public String toString() {
        return "OrderComment{" +
                "id=" + id +
                ", orderItemId=" + orderItemId +
                ", productId=" + productId +
                ", skuId=" + skuId +
                ", userId=" + userId +
                ", score=" + score +
                ", content='" + content + '\'' +
                ", isFollowComment=" + isFollowComment +
                ", commentId=" + commentId +
                ", isAnonymous=" + isAnonymous +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getFollowComment() {
        return isFollowComment;
    }

    public void setFollowComment(Boolean followComment) {
        isFollowComment = followComment;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
