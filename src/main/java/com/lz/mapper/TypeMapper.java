package com.lz.mapper;

import com.lz.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TypeMapper {
    List<Type> getAllType();
    Type getTypeById(@Param("id")Long id);
    Type getTypeByName(@Param("name")String name);
    void saveType(Type type);//返回影响数据库行数
    void updateType(Type type);//返回影响数据库行数
    void deleteType(Long id);


}
