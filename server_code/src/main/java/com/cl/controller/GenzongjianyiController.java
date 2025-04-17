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

import com.cl.entity.GenzongjianyiEntity;
import com.cl.entity.view.GenzongjianyiView;

import com.cl.service.GenzongjianyiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 跟踪建议
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
@RestController
@RequestMapping("/genzongjianyi")
public class GenzongjianyiController {
    @Autowired
    private GenzongjianyiService genzongjianyiService;







    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,GenzongjianyiEntity genzongjianyi,
                                                                                                                            HttpServletRequest request){
                    String tableName = request.getSession().getAttribute("tableName").toString();
                                            if(tableName.equals("yonghu")) {
                    genzongjianyi.setZhanghao((String)request.getSession().getAttribute("username"));
                                    }
                                                                                                                                                                                                            EntityWrapper<GenzongjianyiEntity> ew = new EntityWrapper<GenzongjianyiEntity>();
                                                                                                                                                                                        
        
        
        PageUtils page = genzongjianyiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, genzongjianyi), params), params));
        return R.ok().put("data", page);
    }
    
    
    
    
    
    
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,GenzongjianyiEntity genzongjianyi, 
		HttpServletRequest request){
        EntityWrapper<GenzongjianyiEntity> ew = new EntityWrapper<GenzongjianyiEntity>();

		PageUtils page = genzongjianyiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, genzongjianyi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( GenzongjianyiEntity genzongjianyi){
       	EntityWrapper<GenzongjianyiEntity> ew = new EntityWrapper<GenzongjianyiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( genzongjianyi, "genzongjianyi")); 
        return R.ok().put("data", genzongjianyiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(GenzongjianyiEntity genzongjianyi){
        EntityWrapper< GenzongjianyiEntity> ew = new EntityWrapper< GenzongjianyiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( genzongjianyi, "genzongjianyi")); 
		GenzongjianyiView genzongjianyiView =  genzongjianyiService.selectView(ew);
		return R.ok("查询跟踪建议成功").put("data", genzongjianyiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        GenzongjianyiEntity genzongjianyi = genzongjianyiService.selectById(id);
		genzongjianyi = genzongjianyiService.selectView(new EntityWrapper<GenzongjianyiEntity>().eq("id", id));
        return R.ok().put("data", genzongjianyi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        GenzongjianyiEntity genzongjianyi = genzongjianyiService.selectById(id);
		genzongjianyi = genzongjianyiService.selectView(new EntityWrapper<GenzongjianyiEntity>().eq("id", id));
        return R.ok().put("data", genzongjianyi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody GenzongjianyiEntity genzongjianyi, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(genzongjianyi);
        genzongjianyiService.insert(genzongjianyi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody GenzongjianyiEntity genzongjianyi, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(genzongjianyi);
        genzongjianyiService.insert(genzongjianyi);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody GenzongjianyiEntity genzongjianyi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(genzongjianyi);
        genzongjianyiService.updateById(genzongjianyi);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        genzongjianyiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	









}
