package cn.windf.plugin.service.business;


import cn.windf.core.entity.BaseEntity;
import cn.windf.core.entity.Page;
import cn.windf.core.entity.SearchData;
import cn.windf.core.repository.ManageRepository;
import cn.windf.core.service.ManageService;
import cn.windf.core.util.StringUtil;

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
        this.update(entity);
    }

    @Override
    public void update(T entity) {
        if (StringUtil.isNotEmpty(entity.getId())) {
            this.getManageRepository().update(entity);
        } else {
            this.getManageRepository().create(entity);
        }
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
