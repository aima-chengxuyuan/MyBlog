package com.lz.service;

import com.lz.entity.Type;

import java.util.List;

/**
 * 博客类型操作接口
 *
 * @author jc
 */
public interface TypeService {

    /**
     * 显示所有分类
     *
     * @return
     */
    List<Type> getAllType();

    /**
     * 通过编号获取分类
     *
     * @param id 编号
     * @return
     */
    Type getTypeById(Long id);

    /**
     * 通过分类名称获取分类
     *
     * @param name 分类名称
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 保存分类
     *
     * @param type
     */
    void saveType(Type type);

    /**
     * 更新分类
     *
     * @param type
     */
    void updateType(Type type);

    /**
     * 删除分类
     *
     * @param id 编号
     */
    void deleteType(Long id);
}
