package com.lz.service.impl;

import com.lz.entity.Type;
import com.lz.mapper.TypeMapper;
import com.lz.service.TypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 博客类型操作实现类
 *
 * @author jc
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeMapper typeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Type> getAllType() {
        return typeMapper.getAllType();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Type getTypeById(Long id) {
        return typeMapper.getTypeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveType(Type type) {
        typeMapper.saveType(type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateType(Type type) {//返回影响数据库行数
        typeMapper.updateType(type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteType(Long id) {
        typeMapper.deleteType(id);
    }
}
