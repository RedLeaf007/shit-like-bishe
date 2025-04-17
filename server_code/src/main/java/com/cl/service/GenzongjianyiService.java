package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.GenzongjianyiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.GenzongjianyiView;


/**
 * 跟踪建议
 *
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
public interface GenzongjianyiService extends IService<GenzongjianyiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<GenzongjianyiView> selectListView(Wrapper<GenzongjianyiEntity> wrapper);
   	
   	GenzongjianyiView selectView(@Param("ew") Wrapper<GenzongjianyiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<GenzongjianyiEntity> wrapper);
   	
   
}

