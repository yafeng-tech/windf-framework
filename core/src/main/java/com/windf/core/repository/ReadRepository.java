package com.windf.core.repository;

import com.windf.core.entity.BaseEntity;
import com.windf.core.entity.Page;
import com.windf.core.entity.SearchData;

/**
 * 对实体进行简单的查询和列表查询
 */
public interface ReadRepository<T extends BaseEntity> {

    /**
     * 查看详情
     * @param id    根据id查询
     * @return
     */
    T detail(String id);

    /**
     * 搜索
     * @param searchData    搜索信息
     * @return
     */
    Page<T> search(SearchData searchData);
}
