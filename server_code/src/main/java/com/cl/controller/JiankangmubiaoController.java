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

import com.cl.entity.JiankangmubiaoEntity;
import com.cl.entity.view.JiankangmubiaoView;

import com.cl.service.JiankangmubiaoService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 健康目标
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
@RestController
@RequestMapping("/jiankangmubiao")
public class JiankangmubiaoController {
    @Autowired
    private JiankangmubiaoService jiankangmubiaoService;







    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JiankangmubiaoEntity jiankangmubiao,
                                                                                                                                            HttpServletRequest request){
                    String tableName = request.getSession().getAttribute("tableName").toString();
                                                                                                                                                                                                                                                if(tableName.equals("yonghu")) {
                    jiankangmubiao.setZhanghao((String)request.getSession().getAttribute("username"));
                                    }
                                                                                            EntityWrapper<JiankangmubiaoEntity> ew = new EntityWrapper<JiankangmubiaoEntity>();
                                                                                                                                                                                                                        
        
        
        PageUtils page = jiankangmubiaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiankangmubiao), params), params));
        return R.ok().put("data", page);
    }
    
    
    
    
    
    
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JiankangmubiaoEntity jiankangmubiao, 
		HttpServletRequest request){
        EntityWrapper<JiankangmubiaoEntity> ew = new EntityWrapper<JiankangmubiaoEntity>();

		PageUtils page = jiankangmubiaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiankangmubiao), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JiankangmubiaoEntity jiankangmubiao){
       	EntityWrapper<JiankangmubiaoEntity> ew = new EntityWrapper<JiankangmubiaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jiankangmubiao, "jiankangmubiao")); 
        return R.ok().put("data", jiankangmubiaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JiankangmubiaoEntity jiankangmubiao){
        EntityWrapper< JiankangmubiaoEntity> ew = new EntityWrapper< JiankangmubiaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jiankangmubiao, "jiankangmubiao")); 
		JiankangmubiaoView jiankangmubiaoView =  jiankangmubiaoService.selectView(ew);
		return R.ok("查询健康目标成功").put("data", jiankangmubiaoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JiankangmubiaoEntity jiankangmubiao = jiankangmubiaoService.selectById(id);
		jiankangmubiao = jiankangmubiaoService.selectView(new EntityWrapper<JiankangmubiaoEntity>().eq("id", id));
        return R.ok().put("data", jiankangmubiao);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JiankangmubiaoEntity jiankangmubiao = jiankangmubiaoService.selectById(id);
		jiankangmubiao = jiankangmubiaoService.selectView(new EntityWrapper<JiankangmubiaoEntity>().eq("id", id));
        return R.ok().put("data", jiankangmubiao);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JiankangmubiaoEntity jiankangmubiao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(jiankangmubiao);
        jiankangmubiaoService.insert(jiankangmubiao);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody JiankangmubiaoEntity jiankangmubiao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(jiankangmubiao);
        jiankangmubiaoService.insert(jiankangmubiao);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody JiankangmubiaoEntity jiankangmubiao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jiankangmubiao);
        jiankangmubiaoService.updateById(jiankangmubiao);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        jiankangmubiaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	









}
