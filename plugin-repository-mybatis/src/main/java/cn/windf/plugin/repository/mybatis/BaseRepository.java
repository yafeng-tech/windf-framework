package cn.windf.plugin.repository.mybatis;

import cn.windf.core.entity.BaseEntity;
import cn.windf.core.entity.Page;
import cn.windf.core.entity.SearchData;
import cn.windf.core.repository.ManageRepository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 基础接口，所有mangger都要继承她
 * @param <T>
 */
public interface BaseRepository<T extends BaseEntity> extends BaseMapper<T>, ManageRepository<T> {
    default void create(T entity) {
        insert(entity);
    }

    default void update(T entity) {
        updateById(entity);
    }

    default void delete(List<String> ids) {
        deleteBatchIds(ids);
    }

    default T detail(String id) {
        return selectById(id);
    }

    Page<T> search(SearchData searchData);

}
