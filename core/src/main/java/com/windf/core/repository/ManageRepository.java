package com.windf.core.repository;

import com.windf.core.entity.BaseEntity;

import java.util.List;

/**
 * 可以对实体进行正常的管理
 */
public interface ManageRepository<T extends BaseEntity> extends ReadRepository<T> {
    /**
     * 创建
     * @param entity    要添加的信息
     */
    void create(T entity);

    /**
     * 修改
     * @param entity    要修改的信息
     */
    void update(T entity);

    /**
     * 删除
     * @param ids   更具id进行搜索
     */
    void delete(List<String> ids);
}
