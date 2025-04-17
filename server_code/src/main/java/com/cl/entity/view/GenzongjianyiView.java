package com.cl.entity.view;

import com.cl.entity.GenzongjianyiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.cl.utils.EncryptUtil;
 

/**
 * 跟踪建议
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
@TableName("genzongjianyi")
public class GenzongjianyiView  extends GenzongjianyiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public GenzongjianyiView(){
	}
 
 	public GenzongjianyiView(GenzongjianyiEntity genzongjianyiEntity){
 	try {
			BeanUtils.copyProperties(this, genzongjianyiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}



}
