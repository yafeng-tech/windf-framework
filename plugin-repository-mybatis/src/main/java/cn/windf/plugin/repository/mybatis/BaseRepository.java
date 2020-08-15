package cn.windf.plugin.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 基础接口，所有mangger都要继承她
 * @param <T>
 */
public interface BaseRepository<T> extends BaseMapper<T> {

}
