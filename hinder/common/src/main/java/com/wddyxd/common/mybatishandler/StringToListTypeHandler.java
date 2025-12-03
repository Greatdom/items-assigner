package com.wddyxd.common.mybatishandler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 16:37
 **/

// 映射目标类型：List<String>
@MappedTypes(List.class)
// 数据库字段类型：VARCHAR
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringToListTypeHandler extends BaseTypeHandler<List<String>> {

    // 分隔符（需与 SQL 中 GROUP_CONCAT 的 SEPARATOR 一致）
    private static final String SEPARATOR = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        // 入库时用不到（当前场景仅查询），可空实现
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getString(columnName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getString(columnIndex));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getString(columnIndex));
    }

    // 核心转换：字符串 → List<String>（空字符串→空列表，去空值）
    private List<String> convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(value.split(SEPARATOR))
                .map(String::trim)
                .filter(Objects::nonNull)
                .toList();
    }
}