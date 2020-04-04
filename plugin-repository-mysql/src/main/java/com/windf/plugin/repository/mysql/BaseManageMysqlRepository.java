package com.windf.plugin.repository.mysql;

import com.windf.core.entity.BaseEntity;
import com.windf.core.entity.Page;
import com.windf.core.entity.SearchData;
import com.windf.core.repository.ManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 基本的mysql的存储
 * TODO 提炼公共方法
 */
public abstract class BaseManageMysqlRepository<T extends BaseEntity> extends BaseMysqlRepository implements ManageRepository<T> {
}
