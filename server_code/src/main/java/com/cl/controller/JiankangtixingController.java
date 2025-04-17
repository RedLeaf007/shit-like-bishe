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

import com.cl.entity.JiankangtixingEntity;
import com.cl.entity.view.JiankangtixingView;

import com.cl.service.JiankangtixingService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 健康提醒
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-23 21:09:27
 */
@RestController
@RequestMapping("/jiankangtixing")
public class JiankangtixingController {
    @Autowired
    private JiankangtixingService jiankangtixingService;







    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JiankangtixingEntity jiankangtixing,
                                                                                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date tixingshijianstart,
                    @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date tixingshijianend,
                                HttpServletRequest request){
                    String tableName = request.getSession().getAttribute("tableName").toString();
                                            if(tableName.equals("yonghu")) {
                    jiankangtixing.setZhanghao((String)request.getSession().getAttribute("username"));
                                    }
                                                                                                                                                    EntityWrapper<JiankangtixingEntity> ew = new EntityWrapper<JiankangtixingEntity>();
                                                                                                                    if(tixingshijianstart!=null) ew.ge("tixingshijian", tixingshijianstart);
                    if(tixingshijianend!=null) ew.le("tixingshijian", tixingshijianend);
                                    
        
        
        PageUtils page = jiankangtixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiankangtixing), params), params));
        return R.ok().put("data", page);
    }
    
    
    
    
    
    
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JiankangtixingEntity jiankangtixing, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date tixingshijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date tixingshijianend,
		HttpServletRequest request){
        EntityWrapper<JiankangtixingEntity> ew = new EntityWrapper<JiankangtixingEntity>();
                if(tixingshijianstart!=null) ew.ge("tixingshijian", tixingshijianstart);
                if(tixingshijianend!=null) ew.le("tixingshijian", tixingshijianend);

		PageUtils page = jiankangtixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiankangtixing), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JiankangtixingEntity jiankangtixing){
       	EntityWrapper<JiankangtixingEntity> ew = new EntityWrapper<JiankangtixingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jiankangtixing, "jiankangtixing")); 
        return R.ok().put("data", jiankangtixingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JiankangtixingEntity jiankangtixing){
        EntityWrapper< JiankangtixingEntity> ew = new EntityWrapper< JiankangtixingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jiankangtixing, "jiankangtixing")); 
		JiankangtixingView jiankangtixingView =  jiankangtixingService.selectView(ew);
		return R.ok("查询健康提醒成功").put("data", jiankangtixingView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JiankangtixingEntity jiankangtixing = jiankangtixingService.selectById(id);
		jiankangtixing = jiankangtixingService.selectView(new EntityWrapper<JiankangtixingEntity>().eq("id", id));
        return R.ok().put("data", jiankangtixing);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JiankangtixingEntity jiankangtixing = jiankangtixingService.selectById(id);
		jiankangtixing = jiankangtixingService.selectView(new EntityWrapper<JiankangtixingEntity>().eq("id", id));
        return R.ok().put("data", jiankangtixing);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JiankangtixingEntity jiankangtixing, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(jiankangtixing);
        jiankangtixingService.insert(jiankangtixing);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody JiankangtixingEntity jiankangtixing, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(jiankangtixing);
        jiankangtixingService.insert(jiankangtixing);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody JiankangtixingEntity jiankangtixing, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jiankangtixing);
        jiankangtixingService.updateById(jiankangtixing);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        jiankangtixingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	









}
