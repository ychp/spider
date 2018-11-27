package com.ychp.mybatis.builder.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ychp.code.builder.Builder;
import com.ychp.mybatis.builder.dto.MybatisColumnDto;
import com.ychp.mybatis.builder.utils.MybatisUtils;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-08-08
 */
public class MybatisBuilder extends Builder {

    private static final List<String> IGNORE_EQUALS_COLUMN = Lists.newArrayList("createdAt", "updatedAt");

    @Override
    protected String getFileName(String template, String baseName) {
        String fileName = template.substring(template.indexOf(File.separator) + 1);
        return fileName.replace("Model", baseName);
    }

    protected static Map<String, Object> generalTemplateParamMap(String tableName, String basePackage) {
        Map<String, Object> templateParamMap = Maps.newHashMap();
        templateParamMap.put("tableName", tableName);
        templateParamMap.put("modelName", MybatisUtils.camelNameWithAll(tableName.replace("sky", "")));
        templateParamMap.put("modelParam", MybatisUtils.camelName(tableName.replace("sky", "")));
        templateParamMap.put("package", basePackage);

        String host = "127.0.0.1";
        String port = "3306";
        String database = "blog_new";
        String username = "root";
        String password = "anywhere";

        String url="jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password;
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url);
            DatabaseMetaData dbMetaData = connection.getMetaData();
            String catalog = connection.getCatalog();

            ResultSet resultSet = dbMetaData.getTables(catalog, "%", tableName, new String[] { "TABLE" });

            while (resultSet.next()) {
                String remarks = resultSet.getString("REMARKS");
                templateParamMap.put("tableRemarks", remarks);
            }

            MybatisColumnDto columnDto;
            String columnName;
            String columnType;
            int datasize;

            //主键
            MybatisColumnDto primaryColumnDto = null;
            ResultSet columnRs = dbMetaData.getPrimaryKeys(catalog, null, tableName);
            while(columnRs.next()){
                columnName = columnRs.getString("COLUMN_NAME");
                primaryColumnDto = new MybatisColumnDto();
                primaryColumnDto.setSqlColumn(columnName);
                primaryColumnDto.setJavaColumn(MybatisUtils.camelName(columnName));
                primaryColumnDto.setJavaXmlColumn(columnName);
            }

            List<MybatisColumnDto> columns = Lists.newArrayList();
            ResultSet colRet = dbMetaData.getColumns(catalog,"%", tableName,"%");
            while(colRet.next()) {
                columnName = colRet.getString("COLUMN_NAME");
                columnType = colRet.getString("TYPE_NAME");
                datasize = colRet.getInt("COLUMN_SIZE");
                if(primaryColumnDto != null && primaryColumnDto.getSqlColumn().equals(columnName)){
                    primaryColumnDto.setJavaType(MybatisUtils.getJavaTypeByDBType(columnType, datasize));
                    primaryColumnDto.setNeedEquals(false);
                    templateParamMap.put("primaryColumn", primaryColumnDto);
                    continue;
                }

                columnDto = new MybatisColumnDto();
                columnDto.setSqlColumn(columnName);
                columnDto.setJavaColumn(MybatisUtils.camelName(columnName));
                columnDto.setJavaXmlColumn(columnName);
                columnDto.setJavaType(MybatisUtils.getJavaTypeByDBType(columnType, datasize));
                columnDto.setComment(colRet.getString("REMARKS"));

                if(IGNORE_EQUALS_COLUMN.contains(columnDto.getJavaColumn())) {
                    columnDto.setNeedEquals(false);
                }
                columns.add(columnDto);
            }

            templateParamMap.put("columns", columns);


        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
          if(connection != null){
              try {
                  connection.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
        }
        return templateParamMap;
    }

    public static void main(String[] args){
        String templatePath = "mybatis/src/main/resources/templates";
        String outPath = "/Users/yingchengpeng/ychp/spider/mybatis/src/main/resources/code";
        String tableName = "sky_user_summary";
        String basePackage = "com.ychp.user";
        Builder builder = new MybatisBuilder();
        builder.build(templatePath, outPath, MybatisUtils.camelNameWithAll(tableName).replace("Sky", ""),
                generalTemplateParamMap(tableName, basePackage), true, true);
    }
}
