package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;

import com.cl.entity.ExamquestionbankEntity;
import com.cl.entity.view.ExamquestionbankView;

import com.cl.service.ExamquestionbankService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 试题库管理
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
@RestController
@RequestMapping("/examquestionbank")
public class ExamquestionbankController {
    @Autowired
    private ExamquestionbankService examquestionbankService;







    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ExamquestionbankEntity examquestionbank,
                                                                                                                    HttpServletRequest request){
                                    EntityWrapper<ExamquestionbankEntity> ew = new EntityWrapper<ExamquestionbankEntity>();
                                                                                                                                                                                
        
        
        PageUtils page = examquestionbankService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestionbank), params), params));
        return R.ok().put("data", page);
    }
    
    
    
    
    
    
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ExamquestionbankEntity examquestionbank, 
		HttpServletRequest request){
        EntityWrapper<ExamquestionbankEntity> ew = new EntityWrapper<ExamquestionbankEntity>();

		PageUtils page = examquestionbankService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestionbank), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExamquestionbankEntity examquestionbank){
       	EntityWrapper<ExamquestionbankEntity> ew = new EntityWrapper<ExamquestionbankEntity>();
      	ew.allEq(MPUtil.allEQMapPre( examquestionbank, "examquestionbank")); 
        return R.ok().put("data", examquestionbankService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamquestionbankEntity examquestionbank){
        EntityWrapper< ExamquestionbankEntity> ew = new EntityWrapper< ExamquestionbankEntity>();
 		ew.allEq(MPUtil.allEQMapPre( examquestionbank, "examquestionbank")); 
		ExamquestionbankView examquestionbankView =  examquestionbankService.selectView(ew);
		return R.ok("查询试题库管理成功").put("data", examquestionbankView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExamquestionbankEntity examquestionbank = examquestionbankService.selectById(id);
		examquestionbank = examquestionbankService.selectView(new EntityWrapper<ExamquestionbankEntity>().eq("id", id));
        return R.ok().put("data", examquestionbank);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamquestionbankEntity examquestionbank = examquestionbankService.selectById(id);
		examquestionbank = examquestionbankService.selectView(new EntityWrapper<ExamquestionbankEntity>().eq("id", id));
        return R.ok().put("data", examquestionbank);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExamquestionbankEntity examquestionbank, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(examquestionbank);
        examquestionbankService.insert(examquestionbank);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ExamquestionbankEntity examquestionbank, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(examquestionbank);
            examquestionbankService.insert(examquestionbank);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ExamquestionbankEntity examquestionbank, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestionbank);
        examquestionbankService.updateById(examquestionbank);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        examquestionbankService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	









}
