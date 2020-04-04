package com.windf.core.entity;

import java.util.Date;

/**
 * 大多数表都要继承，创建时间和修改时间，预计状态是必须的
 * 状态为 1 表示有效，状态为 0表示无效；如果有更多状态，需要自己扩展
 */
public class BaseEntity implements Entitiable {
    protected String id;
    protected Date createDate;
    protected Date updateDate;
    protected String status;
    protected String siteCode;

    public BaseEntity() {
    }

    public BaseEntity(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}
