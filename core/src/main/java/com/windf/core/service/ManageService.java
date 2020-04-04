package com.windf.core.service;

import com.windf.core.entity.BaseEntity;
import com.windf.core.entity.SearchData;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 可以对实体进行正常的管理
 * 增删改查，导入导出
 */
public interface ManageService<T extends BaseEntity> extends ReadService<T> {
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

    /**
     * 批量创建
     * @param file      要导入的文件
     * @param fieldMap  字段对应关系，自定名，对应的实体字段
     */
    void batchImport(File file, Map<String, String> fieldMap);

    /**
     * 批量导出
     * @param searchData    搜索信息
     * @return
     */
    File batchExport(SearchData searchData);
}
