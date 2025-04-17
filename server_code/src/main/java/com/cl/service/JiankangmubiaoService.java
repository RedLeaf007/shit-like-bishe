package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.JiankangmubiaoEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiankangmubiaoView;


/**
 * 健康目标
 *
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
public interface JiankangmubiaoService extends IService<JiankangmubiaoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JiankangmubiaoView> selectListView(Wrapper<JiankangmubiaoEntity> wrapper);
   	
   	JiankangmubiaoView selectView(@Param("ew") Wrapper<JiankangmubiaoEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JiankangmubiaoEntity> wrapper);
   	
   
}

