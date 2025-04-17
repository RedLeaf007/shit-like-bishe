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


import com.cl.dao.GenzongjianyiDao;
import com.cl.entity.GenzongjianyiEntity;
import com.cl.service.GenzongjianyiService;
import com.cl.entity.view.GenzongjianyiView;

@Service("genzongjianyiService")
public class GenzongjianyiServiceImpl extends ServiceImpl<GenzongjianyiDao, GenzongjianyiEntity> implements GenzongjianyiService {

    	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<GenzongjianyiEntity> page = this.selectPage(
                new Query<GenzongjianyiEntity>(params).getPage(),
                new EntityWrapper<GenzongjianyiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<GenzongjianyiEntity> wrapper) {
		  Page<GenzongjianyiView> page =new Query<GenzongjianyiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<GenzongjianyiView> selectListView(Wrapper<GenzongjianyiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public GenzongjianyiView selectView(Wrapper<GenzongjianyiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}
	
	


}
