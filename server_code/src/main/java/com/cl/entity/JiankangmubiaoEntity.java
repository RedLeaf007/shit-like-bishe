package com.cl.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 健康目标
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
@TableName("jiankangmubiao")
public class JiankangmubiaoEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public JiankangmubiaoEntity() {
		
	}
	
	public JiankangmubiaoEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 目标名称
	 */
					
	private String mubiaomingcheng;
	
	/**
	 * 制定时间
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date zhidingshijian;
	
	/**
	 * 锻炼目标
	 */
					
	private String duanlianmubiao;
	
	/**
	 * 运动目标
	 */
					
	private String yundongmubiao;
	
	/**
	 * 饮食目标
	 */
					
	private String yinshimubiao;
	
	/**
	 * 计划开始
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat 		
	private Date jihuakaishi;
	
	/**
	 * 计划结束
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat 		
	private Date jihuajieshu;
	
	/**
	 * 账号
	 */
					
	private String zhanghao;
	
	/**
	 * 姓名
	 */
					
	private String xingming;
	

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 设置：目标名称
	 */
	public void setMubiaomingcheng(String mubiaomingcheng) {
		this.mubiaomingcheng = mubiaomingcheng;
	}
	/**
	 * 获取：目标名称
	 */
	public String getMubiaomingcheng() {
		return mubiaomingcheng;
	}
	/**
	 * 设置：制定时间
	 */
	public void setZhidingshijian(Date zhidingshijian) {
		this.zhidingshijian = zhidingshijian;
	}
	/**
	 * 获取：制定时间
	 */
	public Date getZhidingshijian() {
		return zhidingshijian;
	}
	/**
	 * 设置：锻炼目标
	 */
	public void setDuanlianmubiao(String duanlianmubiao) {
		this.duanlianmubiao = duanlianmubiao;
	}
	/**
	 * 获取：锻炼目标
	 */
	public String getDuanlianmubiao() {
		return duanlianmubiao;
	}
	/**
	 * 设置：运动目标
	 */
	public void setYundongmubiao(String yundongmubiao) {
		this.yundongmubiao = yundongmubiao;
	}
	/**
	 * 获取：运动目标
	 */
	public String getYundongmubiao() {
		return yundongmubiao;
	}
	/**
	 * 设置：饮食目标
	 */
	public void setYinshimubiao(String yinshimubiao) {
		this.yinshimubiao = yinshimubiao;
	}
	/**
	 * 获取：饮食目标
	 */
	public String getYinshimubiao() {
		return yinshimubiao;
	}
	/**
	 * 设置：计划开始
	 */
	public void setJihuakaishi(Date jihuakaishi) {
		this.jihuakaishi = jihuakaishi;
	}
	/**
	 * 获取：计划开始
	 */
	public Date getJihuakaishi() {
		return jihuakaishi;
	}
	/**
	 * 设置：计划结束
	 */
	public void setJihuajieshu(Date jihuajieshu) {
		this.jihuajieshu = jihuajieshu;
	}
	/**
	 * 获取：计划结束
	 */
	public Date getJihuajieshu() {
		return jihuajieshu;
	}
	/**
	 * 设置：账号
	 */
	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}
	/**
	 * 获取：账号
	 */
	public String getZhanghao() {
		return zhanghao;
	}
	/**
	 * 设置：姓名
	 */
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	/**
	 * 获取：姓名
	 */
	public String getXingming() {
		return xingming;
	}

}
