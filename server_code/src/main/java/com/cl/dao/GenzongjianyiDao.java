package com.cl.dao;

import com.cl.entity.GenzongjianyiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.GenzongjianyiView;


/**
 * 跟踪建议
 * 
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
public interface GenzongjianyiDao extends BaseMapper<GenzongjianyiEntity> {
	
	List<GenzongjianyiView> selectListView(@Param("ew") Wrapper<GenzongjianyiEntity> wrapper);

	List<GenzongjianyiView> selectListView(Pagination page,@Param("ew") Wrapper<GenzongjianyiEntity> wrapper);
	
	GenzongjianyiView selectView(@Param("ew") Wrapper<GenzongjianyiEntity> wrapper);


}
