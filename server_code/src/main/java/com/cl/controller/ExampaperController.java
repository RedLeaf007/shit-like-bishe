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

import com.cl.entity.ExampaperEntity;
import com.cl.entity.view.ExampaperView;

import com.cl.service.ExampaperService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;
import com.cl.service.ExamquestionService;
import com.cl.service.ExamquestionbankService;
import com.cl.service.ExamrecordService;
import com.cl.entity.ExamquestionEntity;
import com.cl.entity.ExamquestionbankEntity;
import com.cl.entity.ExamrecordEntity;

/**
 * 健康小测
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-23 21:09:26
 */
@RestController
@RequestMapping("/exampaper")
public class ExampaperController {
    @Autowired
    private ExampaperService exampaperService;


    @Autowired
    private ExamquestionService examquestionService;

    @Autowired
    private ExamquestionbankService examquestionbankService;

    @Autowired
    private ExamrecordService examrecordService;





    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ExampaperEntity exampaper,
                                                                    HttpServletRequest request){
                                    EntityWrapper<ExampaperEntity> ew = new EntityWrapper<ExampaperEntity>();
                                                                                                
        
        
        PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));
        return R.ok().put("data", page);
    }
    
    
    
    
    
    
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ExampaperEntity exampaper, 
		HttpServletRequest request){
        EntityWrapper<ExampaperEntity> ew = new EntityWrapper<ExampaperEntity>();

		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExampaperEntity exampaper){
       	EntityWrapper<ExampaperEntity> ew = new EntityWrapper<ExampaperEntity>();
      	ew.allEq(MPUtil.allEQMapPre( exampaper, "exampaper")); 
        return R.ok().put("data", exampaperService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExampaperEntity exampaper){
        EntityWrapper< ExampaperEntity> ew = new EntityWrapper< ExampaperEntity>();
 		ew.allEq(MPUtil.allEQMapPre( exampaper, "exampaper")); 
		ExampaperView exampaperView =  exampaperService.selectView(ew);
		return R.ok("查询健康小测成功").put("data", exampaperView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExampaperEntity exampaper = exampaperService.selectById(id);
		exampaper = exampaperService.selectView(new EntityWrapper<ExampaperEntity>().eq("id", id));
        return R.ok().put("data", exampaper);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExampaperEntity exampaper = exampaperService.selectById(id);
		exampaper = exampaperService.selectView(new EntityWrapper<ExampaperEntity>().eq("id", id));
        return R.ok().put("data", exampaper);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExampaperEntity exampaper, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(exampaper);
        exampaperService.insert(exampaper);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ExampaperEntity exampaper, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(exampaper);
            exampaperService.insert(exampaper);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ExampaperEntity exampaper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(exampaper);
        exampaperService.updateById(exampaper);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        exampaperService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	








    /**
     * 组卷
     */
    @RequestMapping("/compose")
    public R compose(HttpServletRequest request,@RequestParam Long paperid, @RequestParam String papername, @RequestParam Integer radioNum,
        @RequestParam Integer multipleChoiceNum, @RequestParam Integer determineNum, @RequestParam Integer fillNum, @RequestParam Integer subjectivityNum){
    //如果已存在考试记录，不能进行重新组卷
    if(examrecordService.selectCount(new EntityWrapper<ExamrecordEntity>().eq("paperid", paperid))>0) {
        return R.error("已存在考试记录，无法重新组卷");
    }
    //组卷之前删除该试卷之前的所有题目
    examquestionService.deleteByMap(new MapUtils().put("paperid", paperid));
    List<ExamquestionbankEntity> questionList = new ArrayList<ExamquestionbankEntity>();
    String subject = request.getParameter("subject");
        //单选题
    if(radioNum>0) {
        Wrapper<ExamquestionbankEntity> countEw0 = new EntityWrapper<ExamquestionbankEntity>().eq("type", 0);
                                                                            Integer radioSize = examquestionbankService.selectCount(countEw0);
        if(radioSize<radioNum) {
            return R.error("单选题库不足");
        } else {
            Wrapper<ExamquestionbankEntity> ew = new EntityWrapper<ExamquestionbankEntity>();
                                                                                                            if (StringUtils.isNotBlank(subject)) {
                ew.eq("subject", subject);
            }
            ew.eq("type", 0).orderBy("RAND()").last("limit "+radioNum);
            List<ExamquestionbankEntity> radioList = examquestionbankService.selectList(ew);
            questionList.addAll(radioList);
        }
    }
    //多选题
    if(multipleChoiceNum>0) {
        Wrapper<ExamquestionbankEntity> countEw1 = new EntityWrapper<ExamquestionbankEntity>().eq("type", 1);
                                                                            Integer multipleChoiceSize = examquestionbankService.selectCount(countEw1);
        if(multipleChoiceSize<multipleChoiceNum) {
            return R.error("多选题库不足");
        } else {
            Wrapper<ExamquestionbankEntity> ew = new EntityWrapper<ExamquestionbankEntity>();
                                                                                                            if (StringUtils.isNotBlank(subject)) {
                ew.eq("subject", subject);
            }
            ew.eq("type", 1).orderBy("RAND()").last("limit "+multipleChoiceNum);
            List<ExamquestionbankEntity> multipleChoiceList = examquestionbankService.selectList(ew);
            questionList.addAll(multipleChoiceList);
        }
    }
    //判断题
    if(determineNum>0) {
        Wrapper<ExamquestionbankEntity> countEw2 = new EntityWrapper<ExamquestionbankEntity>().eq("type", 2);
                                                                            Integer determineSize = examquestionbankService.selectCount(countEw2);
        if(determineSize<determineNum) {
            return R.error("判断题库不足");
        } else {
            Wrapper<ExamquestionbankEntity> ew = new EntityWrapper<ExamquestionbankEntity>();
                                                                                                            if (StringUtils.isNotBlank(subject)) {
                ew.eq("subject", subject);
            }
            ew.eq("type", 2).orderBy("RAND()").last("limit "+determineNum);
            List<ExamquestionbankEntity> determineList = examquestionbankService.selectList(ew);
            questionList.addAll(determineList);
        }
    }
    //填空题
    if(fillNum>0) {
        Wrapper<ExamquestionbankEntity> countEw3 = new EntityWrapper<ExamquestionbankEntity>().eq("type", 3);
                                                                            Integer fillSize = examquestionbankService.selectCount(countEw3);
        if(fillSize<fillNum) {
            return R.error("填空题库不足");
        } else {
            Wrapper<ExamquestionbankEntity> ew = new EntityWrapper<ExamquestionbankEntity>();
                                                                                                            if (StringUtils.isNotBlank(subject)) {
                ew.eq("subject", subject);
            }
            ew.eq("type", 3).orderBy("RAND()").last("limit "+fillNum);
            List<ExamquestionbankEntity> fillList = examquestionbankService.selectList(ew);
            questionList.addAll(fillList);
        }
    }
    //主观题
    if(subjectivityNum>0) {
        Wrapper<ExamquestionbankEntity> countEw4 = new EntityWrapper<ExamquestionbankEntity>().eq("type", 4);
                                                                            Integer subjectivitySize = examquestionbankService.selectCount(countEw4);
        if(subjectivitySize<subjectivityNum) {
            return R.error("主观题库不足");
        } else {
            Wrapper<ExamquestionbankEntity> ew = new EntityWrapper<ExamquestionbankEntity>();
                                                                                                            if (StringUtils.isNotBlank(subject)) {
                ew.eq("subject", subject);
            }
            ew.eq("type", 4).orderBy("RAND()").last("limit "+subjectivityNum);
            List<ExamquestionbankEntity> subjectivityList = examquestionbankService.selectList(ew);
            questionList.addAll(subjectivityList);
        }
    }
    if(questionList!=null && questionList.size()>0) {
        long seq = 0;
        for(ExamquestionbankEntity q : questionList) {
            ExamquestionEntity examquestion = new ExamquestionEntity();
            examquestion.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
            examquestion.setPaperid(paperid);
            examquestion.setPapername(papername);
            examquestion.setQuestionname(q.getQuestionname());
            examquestion.setOptions(q.getOptions());
            examquestion.setScore(q.getScore());
            examquestion.setAnswer(q.getAnswer());
            examquestion.setAnalysis(q.getAnalysis());
            examquestion.setType(q.getType());
            examquestion.setSequence(++seq);
            examquestionService.insert(examquestion);
        }
    }
    return R.ok();
}

}
