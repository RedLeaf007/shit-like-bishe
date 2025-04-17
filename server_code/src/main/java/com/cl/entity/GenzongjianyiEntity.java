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
 * 跟踪建议
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
@TableName("genzongjianyi")
public class GenzongjianyiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public GenzongjianyiEntity() {
		
	}
	
	public GenzongjianyiEntity(T t) {
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
	 * 账号
	 */
					
	private String zhanghao;
	
	/**
	 * 姓名
	 */
					
	private String xingming;
	
	/**
	 * 运动目标
	 */
					
	private String yundongmubiao;
	
	/**
	 * 调整目标
	 */
					
	private String diaozhengmubiao;
	
	/**
	 * 调整建议
	 */
					
	private String diaozhengjianyi;
	
	/**
	 * 更新时间
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date gengxinshijian;
	

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
	 * 设置：调整目标
	 */
	public void setDiaozhengmubiao(String diaozhengmubiao) {
		this.diaozhengmubiao = diaozhengmubiao;
	}
	/**
	 * 获取：调整目标
	 */
	public String getDiaozhengmubiao() {
		return diaozhengmubiao;
	}
	/**
	 * 设置：调整建议
	 */
	public void setDiaozhengjianyi(String diaozhengjianyi) {
		this.diaozhengjianyi = diaozhengjianyi;
	}
	/**
	 * 获取：调整建议
	 */
	public String getDiaozhengjianyi() {
		return diaozhengjianyi;
	}
	/**
	 * 设置：更新时间
	 */
	public void setGengxinshijian(Date gengxinshijian) {
		this.gengxinshijian = gengxinshijian;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getGengxinshijian() {
		return gengxinshijian;
	}

}
