package com.windf.plugin.repository.mysql;

import com.windf.core.entity.OrderItem;
import com.windf.core.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 基本的mysq的存储
 */
@Repository
public abstract class BaseMysqlRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * 拼接order
     * @param orderList
     * @return
     */
    protected String getOrderSql(List<OrderItem> orderList) {
        StringBuffer orderSql = new StringBuffer();

        if (CollectionUtil.isNotEmpty(orderList)) {
            Map<String, String> fieldMap = getFieldMap();
            for (int i = 0; i < orderList.size(); i++) {
                OrderItem orderItem = orderList.get(i);

                String fieldName = fieldMap.get(orderItem.getDataIndex());

                orderSql.append(" ");
                orderSql.append(fieldName);
                orderSql.append(" ");
                orderSql.append(orderItem.getSort().name());
                if (i != orderList.size() - 1) {
                    orderSql.append(",");
                }
            }
        } else {
            orderSql.append(" t.update_date desc, t.id desc ");
        }

        return orderSql.toString();
    }

    /**
     * 获取实体和字段的对应关系
     * @return
     */
    protected abstract Map<String,String> getFieldMap();

}
