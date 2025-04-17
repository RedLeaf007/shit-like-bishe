package com.cl.entity.view;

import com.cl.entity.ExamquestionbankEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.cl.utils.EncryptUtil;
 

/**
 * 试题库管理
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
@TableName("examquestionbank")
public class ExamquestionbankView  extends ExamquestionbankEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ExamquestionbankView(){
	}
 
 	public ExamquestionbankView(ExamquestionbankEntity examquestionbankEntity){
 	try {
			BeanUtils.copyProperties(this, examquestionbankEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}



}
