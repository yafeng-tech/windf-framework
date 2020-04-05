package cn.windf.plugin.repository.mysql;

import cn.windf.core.entity.BaseEntity;
import cn.windf.core.repository.ManageRepository;

/**
 * 基本的mysql的存储
 * TODO 提炼公共方法
 */
public abstract class BaseManageMysqlRepository<T extends BaseEntity> extends BaseMysqlRepository implements ManageRepository<T> {
}
