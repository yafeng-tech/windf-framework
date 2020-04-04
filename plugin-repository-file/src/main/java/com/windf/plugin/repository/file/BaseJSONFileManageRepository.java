package com.windf.plugin.repository.file;

import com.windf.core.entity.BaseEntity;
import com.windf.core.repository.ManageRepository;
import com.windf.plugin.repository.file.config.RepositoryConfig;
import com.windf.plugin.repository.file.util.FileManageUtil;
import com.windf.plugin.repository.file.util.JSONRepositoryUtil;

import java.io.File;

public abstract class BaseJSONFileManageRepository<T extends BaseEntity> extends BaseJSONFileRepository implements ManageRepository<T> {

}
