package com.cl.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cl.utils.PageUtils;
import com.cl.utils.Query;


import com.cl.dao.JiankangtixingDao;
import com.cl.entity.JiankangtixingEntity;
import com.cl.service.JiankangtixingService;
import com.cl.entity.view.JiankangtixingView;

@Service("jiankangtixingService")
public class JiankangtixingServiceImpl extends ServiceImpl<JiankangtixingDao, JiankangtixingEntity> implements JiankangtixingService {

    	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JiankangtixingEntity> page = this.selectPage(
                new Query<JiankangtixingEntity>(params).getPage(),
                new EntityWrapper<JiankangtixingEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JiankangtixingEntity> wrapper) {
		  Page<JiankangtixingView> page =new Query<JiankangtixingView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<JiankangtixingView> selectListView(Wrapper<JiankangtixingEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JiankangtixingView selectView(Wrapper<JiankangtixingEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}
	
	


}
