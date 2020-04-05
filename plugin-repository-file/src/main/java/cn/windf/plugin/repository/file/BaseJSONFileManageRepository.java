package cn.windf.plugin.repository.file;

import cn.windf.core.entity.BaseEntity;
import cn.windf.core.repository.ManageRepository;

public abstract class BaseJSONFileManageRepository<T extends BaseEntity> extends BaseJSONFileRepository implements ManageRepository<T> {

}
