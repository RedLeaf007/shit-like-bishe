package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.JiankangtixingEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiankangtixingView;


/**
 * 健康提醒
 *
 * @author 
 * @email 
 * @date 2025-02-23 21:09:27
 */
public interface JiankangtixingService extends IService<JiankangtixingEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JiankangtixingView> selectListView(Wrapper<JiankangtixingEntity> wrapper);
   	
   	JiankangtixingView selectView(@Param("ew") Wrapper<JiankangtixingEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JiankangtixingEntity> wrapper);
   	
   
}

