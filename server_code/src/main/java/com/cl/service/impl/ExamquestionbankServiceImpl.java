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


import com.cl.dao.ExamquestionbankDao;
import com.cl.entity.ExamquestionbankEntity;
import com.cl.service.ExamquestionbankService;
import com.cl.entity.view.ExamquestionbankView;

@Service("examquestionbankService")
public class ExamquestionbankServiceImpl extends ServiceImpl<ExamquestionbankDao, ExamquestionbankEntity> implements ExamquestionbankService {

    	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamquestionbankEntity> page = this.selectPage(
                new Query<ExamquestionbankEntity>(params).getPage(),
                new EntityWrapper<ExamquestionbankEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ExamquestionbankEntity> wrapper) {
		  Page<ExamquestionbankView> page =new Query<ExamquestionbankView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<ExamquestionbankView> selectListView(Wrapper<ExamquestionbankEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamquestionbankView selectView(Wrapper<ExamquestionbankEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}
	
	


}
