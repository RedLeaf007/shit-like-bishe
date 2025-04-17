package com.cl.dao;

import com.cl.entity.JiankangmubiaoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiankangmubiaoView;


/**
 * 健康目标
 * 
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
public interface JiankangmubiaoDao extends BaseMapper<JiankangmubiaoEntity> {
	
	List<JiankangmubiaoView> selectListView(@Param("ew") Wrapper<JiankangmubiaoEntity> wrapper);

	List<JiankangmubiaoView> selectListView(Pagination page,@Param("ew") Wrapper<JiankangmubiaoEntity> wrapper);
	
	JiankangmubiaoView selectView(@Param("ew") Wrapper<JiankangmubiaoEntity> wrapper);


}
