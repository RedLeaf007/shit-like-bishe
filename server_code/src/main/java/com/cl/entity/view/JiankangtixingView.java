package com.cl.entity.view;

import com.cl.entity.JiankangtixingEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.cl.utils.EncryptUtil;
 

/**
 * 健康提醒
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2025-02-23 21:09:27
 */
@TableName("jiankangtixing")
public class JiankangtixingView  extends JiankangtixingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public JiankangtixingView(){
	}
 
 	public JiankangtixingView(JiankangtixingEntity jiankangtixingEntity){
 	try {
			BeanUtils.copyProperties(this, jiankangtixingEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}



}
