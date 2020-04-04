package com.windf.plugin.service.business;


import com.windf.core.entity.BaseEntity;
import com.windf.core.entity.Page;
import com.windf.core.entity.SearchData;
import com.windf.core.repository.ManageRepository;
import com.windf.core.service.ManageService;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 可以对实体进行正常的管理的默认实现
 * 增删改查，导入导出
 */
public abstract class BaseManageService<T extends BaseEntity> implements ManageService<T> {

    @Override
    public void create(T entity) {
        this.getManageRepository().create(entity);
    }

    @Override
    public void update(T entity) {
        this.getManageRepository().update(entity);
    }

    @Override
    public void delete(List<String> ids) {
        this.getManageRepository().delete(ids);
    }

    @Override
    public void batchImport(File file, Map<String, String> fieldMap) {

    }

    @Override
    public File batchExport(SearchData searchData) {
        return null;
    }

    @Override
    public T detail(String id) {
        return this.getManageRepository().detail(id);
    }

    @Override
    public Page<T> search(SearchData searchData) {
        return this.getManageRepository().search(searchData);
    }

    /**
     * 获取基本的管理仓储操作
     * @return
     */
    public abstract ManageRepository<T> getManageRepository();
}
