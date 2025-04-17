package com.cl.dao;

import com.cl.entity.JiankangtixingEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiankangtixingView;


/**
 * 健康提醒
 * 
 * @author 
 * @email 
 * @date 2025-02-23 21:09:27
 */
public interface JiankangtixingDao extends BaseMapper<JiankangtixingEntity> {
	
	List<JiankangtixingView> selectListView(@Param("ew") Wrapper<JiankangtixingEntity> wrapper);

	List<JiankangtixingView> selectListView(Pagination page,@Param("ew") Wrapper<JiankangtixingEntity> wrapper);
	
	JiankangtixingView selectView(@Param("ew") Wrapper<JiankangtixingEntity> wrapper);


}
