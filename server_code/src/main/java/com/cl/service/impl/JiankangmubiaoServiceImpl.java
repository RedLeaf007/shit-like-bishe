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


import com.cl.dao.JiankangmubiaoDao;
import com.cl.entity.JiankangmubiaoEntity;
import com.cl.service.JiankangmubiaoService;
import com.cl.entity.view.JiankangmubiaoView;

@Service("jiankangmubiaoService")
public class JiankangmubiaoServiceImpl extends ServiceImpl<JiankangmubiaoDao, JiankangmubiaoEntity> implements JiankangmubiaoService {

    	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JiankangmubiaoEntity> page = this.selectPage(
                new Query<JiankangmubiaoEntity>(params).getPage(),
                new EntityWrapper<JiankangmubiaoEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JiankangmubiaoEntity> wrapper) {
		  Page<JiankangmubiaoView> page =new Query<JiankangmubiaoView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<JiankangmubiaoView> selectListView(Wrapper<JiankangmubiaoEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JiankangmubiaoView selectView(Wrapper<JiankangmubiaoEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}
	
	


}
