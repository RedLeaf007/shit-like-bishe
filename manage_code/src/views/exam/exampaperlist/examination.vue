<template>
	<div class="exam_view">
		<div class="exam_top">
			<div class="examTopBox examTopLeft">
				{{examDetail.name}} (共{{questionList.length}}题)
			</div>
			<div class="examTopBox examTopCenter">
				剩余时间：{{times}}
			</div>
			<div class="examTopBox examTopRight">
				<el-button type="danger" @click="leaveExam">退出问卷</el-button>
			</div>
		</div>
		<div class="exam_top_kong"></div>
		<el-card v-if="endType" class="score_view">
            <div class="score_item">
              问卷已完成，感谢填写！
            </div>
            <div class="btn_view">
                <el-button class="end" type="success" @click="endExam">退出问卷</el-button>
            </div>
		</el-card>
		<el-card class="question_list" v-if="(!submitType&&!analysisType)||(submitType&&analysisType)">
			<div class="question" v-for="(item,index) in questionList" :key="index">
				<div class="questionTitle">
					{{index + 1}}、{{item.questionname}}
					<el-tag type="success" v-if="item.type==0">单选题</el-tag>
					<el-tag type="warning" v-if="item.type==1">多选题</el-tag>
				</div>
				<div class="optionList" v-if="item.type==0||item.type==1">
					<div class="option" v-for="(items,indexs) in item.optionList" :key="index">
						{{codeChange(indexs)}}：{{items.text}}
					</div>
				</div>
				<div class="answer" v-if="!submitType">
					<el-select v-if="item.type==2||item.type==0" v-model="item.myanswer"
						placeholder="输入答案">
						<el-option v-if="item.type==0" v-for="(items,indexs) in item.optionList"
							:label="codeChange(indexs)" :value="codeChange(indexs)"></el-option>
                        <template v-if="item.type==2">
                            <el-option label="对" value="A"></el-option>
                            <el-option label="错" value="B"></el-option>
                        </template>
					</el-select>
					<el-select v-else-if="item.type==1" v-model="item.myanswers"
						placeholder="输入答案" multiple @change="(e)=>type1Change(e,index)">
						<el-option v-for="(items,indexs) in item.optionList" :label="codeChange(indexs)"
							:value="codeChange(indexs)"></el-option>
					</el-select>
					<el-input v-else-if="item.type==4" class="answer_textarea" type="textarea"
							  v-model="item.myanswer" placeholder="请输入"></el-input>
					<el-input class="answer_inp" v-model="item.myanswer" placeholder="输入答案" v-else></el-input>
				</div>
				<div class="answer" v-if="submitType">
					<template v-if="item.type!=4">
						<el-tag type="success" v-if="item.answer==item.myanswer" size="large">答案正确</el-tag>
						<el-tag type="danger" v-if="item.answer!=item.myanswer" size="large">答案错误</el-tag>
					</template>
					我的答案：{{item.type!=2?item.myanswer:{'A':'对','B':'错'}[item.myanswer]}}
				</div>
				<el-collapse v-if="submitType" model-value="1" class="analysis_view">
					<el-collapse-item title="查看解析" name="1">
						<div class="right_view" v-if="item.type!=4">正确答案：{{item.type!=2?item.answer:{'A':'对','B':'错'}[item.answer]}}</div>
						<div class="analysis">解析：{{item.analysis}}</div>
					</el-collapse-item>
				</el-collapse>
				<el-divider v-if="questionList.length - 1!=index" />
			</div>
			<div class="btn_view" v-if="!submitType">
				<el-button class="submit" type="success" @click="save">立即交卷</el-button>
			</div>
		</el-card>
	</div>
</template>

<script setup>
	import {
		ref,
		nextTick,
		getCurrentInstance,
		computed
	} from 'vue';
	import {
		useRoute,
		useRouter
	} from 'vue-router'
	import {
		ElMessageBox
	} from 'element-plus'
	const route = useRoute()
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	//初始化
	const id = ref(0)
	const init = () => {
		if (route.query.id) {
			id.value = route.query.id
			getSession()
			getExamination()
			getQuestionList()
		}
	}
	//获取用户信息
	const user = ref({})
	const getSession = () => {
		context?.$http({
			url: `${context?.$toolUtil.storageGet('sessionTable')}/session`,
			method: 'get'
		}).then(res => {
			user.value = res.data.data
		})
	}
	//获取试卷详情
	const examDetail = ref({})
	const examTime = ref(0)
	const timeInter = ref(null)
	const getExamination = () => {
		context?.$http({
			url: `exampaper/info/${id.value}`,
			method: 'get'
		}).then(res => {
			examDetail.value = res.data.data
			examTime.value = Number(res.data.data.time * 60)
			timeInter.value = setInterval(() => {
				examTime.value--
			}, 1000)
		})
	}
    const times = computed(() => {
      if(!examTime.value)return "0秒"
      let time = examTime.value
      if (time) {
        if (time > 60 && time < 3600) {
          time =`${parseInt(time / 60)}分钟${parseInt(time % 60)}秒`
        } else if (time >= 3600 && time < 3600 * 24) {
          time = `${parseInt(time / 3600)}小时${parseInt((time%3600)/60)}分钟${parseInt(time % 60)}秒`
        } else if (time >= 3600 * 24) {
          time = `${parseInt(time / 3600*24)}天${parseInt( (time%3600*24)/3600 )}小时${ parseInt((time%3600)/60) }分${parseInt(time%60)}秒`
        } else {
          if (parseInt(time) <= 0) {
            time = "0秒";
          } else {
            time = parseInt(time) + "秒";
          }
        }
      }
      return time;
    })
	//交卷状态
	const submitType = ref(false)
	const endType = ref(false)
	//是否查看解析
	const analysisType = ref(false)
	//试题
	const questionList = ref([])
    const ismark = ref(1)
	const getQuestionList = () => {
		context?.$http({
			url: 'examquestion/page',
			method: 'get',
			params: {
				page: 1,
				limit: 1000,
				sort: 'sequence',
				paperid: id.value
			}
		}).then(res => {
			for (let x in res.data.data.list) {
				if (res.data.data.list[x].options) {
					res.data.data.list[x].optionList = JSON.parse(res.data.data.list[x].options)
				} else {
					res.data.data.list[x].optionList = []
				}
			}
			questionList.value = res.data.data.list
		})
	}
	//获取选项
	const codeChange = (type) => {
		let arr = ['A', 'B', 'C', 'D']
		return arr[type]
	}
	//多选题
	const type1Change = (e,index) => {
		questionList.value[index].myanswers = questionList.value[index].myanswers.sort()
		questionList.value[index].myanswer = e.sort().join(',')
	}

	//退出问卷
	const leaveExam = () => {
		ElMessageBox.confirm(`是否退出问卷？`, '提示', {
			confirmButtonText: '是',
			cancelButtonText: '否',
			type: 'warning',
		}).then(() => {
			history.back()
		})
	}
	//结束问卷
	const endExam = () => {
		history.back()
	}
	//分数统计
	const scoreChange = () => {
		let score = 0
		for (let x in questionList.value) {
			if (questionList.value[x].myanswer == questionList.value[x].answer && questionList.value[x].type!=4) {
				questionList.value[x].myscore = questionList.value[x].score
			} else {
				questionList.value[x].myscore = 0
			}
			score += questionList.value[x].myscore
		}
		return score
	}
	//提交问卷记录
	const saverecord = (row) => {
		let arr = {
			userid: user.value.id,
			paperid: examDetail.value.id,
			papername: examDetail.value.name,
			questionid: parseInt(row.id),
			questionname: row.questionname,
			options: row.options ? row.options : '[]',
			score: row.score,
			answer: row.answer,
			analysis: row.analysis,
			myanswer: row.myanswer,
			myscore: row.myanswer == row.answer ? row.score : 0,
            type:row.type,
            ismark:ismark.value
		}
		context?.$http({
			url: 'examrecord/save',
			method: 'POST',
			data: arr
		}).then(res => {})
	}
	//查看解析
	const viewAnalysis = () => {
		analysisType.value = true
	}
	//交卷
	const save = () => {
		ElMessageBox.confirm(`是否完成问卷？`, '提示', {
			confirmButtonText: '是',
			cancelButtonText: '否',
			type: 'warning',
		}).then(() => {
			submitType.value = true
			endType.value = true
			clearInterval(timeInter.value)
			for(let x in questionList.value){
				saverecord(questionList.value[x])
			}
		})
	}
	init()
</script>

<style lang="scss" scoped>
	.exam_top {
		
		// 考试名称
		.examTopLeft {
		}
		//时间
		.examTopCenter {
		}
		// 退出考试盒子
		.examTopRight {
			// 按钮
			:deep(.el-button--danger) {
			}
			// 按钮悬浮
			:deep(.el-button--danger:hover) {
			}
		}
	}

	// 答题区域
	.question_list {
		// 答题盒子
		.question {
			// 分割线
			:deep(.el-divider) {
			}
			// 题目
			.questionTitle {
				// 单选题
				:deep(.el-tag--success) {
					background-color: #f0f9eb;
					color: #67c23a;
					border-color: #e1f3d8;
				}
				// 判断题
				:deep(.el-tag--danger) {
					background-color: #fef0f0;
					color: #f56c6c;
					border-color: #fde2e2;
				}
				// 填空题
				:deep(.el-tag--info) {
					background-color: #f4f4f5;
					color: #909399;
					border-color: #e9e9eb;
				}
				// 多选题
				:deep(.el-tag--warning) {
					background-color: #fdf6ec;
					color: #e6a23c;
					border-color: #faecd8;
				}
			}
			// 选项列表
			.optionList {

				.option {
				}
			}
			// 答案盒子
			.answer {
				// 输入框
				:deep(.answer_inp) {
				}
				// 下拉框
				:deep(.el-select) {
					//去掉默认样式
					.select-trigger {
						height: 100%;
						.el-input {
							height: 100%;
						}
					}
				}
				// 答案正确
				:deep(.el-tag--success) {
				}
				// 答案错误
				:deep(.el-tag--danger) {
				}
			}
			// 解析盒子
			:deep(.analysis_view) {
				// 标题
				.el-collapse-item__header {
					// 图标
					.el-icon {
					}
				}
				.el-collapse-item__wrap {
					border-bottom: none;
				}
				// 内容区
				.el-collapse-item__content {
					// 正确答案
					.right_view {
					}
					// 解析
					.analysis {
					}
				}
			}
		}
		// 按钮盒子
		.btn_view{
			// 提交
			:deep(.el-button--success) {
			}
			// 提交-悬浮
			:deep(.el-button--success:hover) {
			}
			// 下一题
			:deep(.el-button--warning) {
			}
			// 下一题-悬浮
			:deep(.el-button--warning:hover) {
			}
			// 查看解析
			:deep(.el-button--primary) {
			}
			// 查看解析-悬浮
			:deep(.el-button--primary:hover) {
			}
			// 结束考试
			:deep(.el-button--danger) {
			}
			// 结束考试-悬浮
			:deep(.el-button--danger:hover) {
			}
		}
	}
	// 成绩盒子
	.score_view {
		.score_item {
		}
		.score_num {
		}
	}
</style>
<style>
.exam_view {
      padding:30px 0;
}
/* 头部 总盒子*/
.exam_view .exam_top{
    width: 100%;
    height: 80px;
    padding: 0px 7%;
    display: flex;
    background: var(--theme);
    box-sizing: border-box;
    align-items: center;
    justify-content: space-between;
    position: fixed;
    top: 0px;
    left: 0px;
    color: #fff;
    z-index: 1000;
}
/* 试卷 名称 */
.exam_view .exam_top .examTopLeft{
    width: calc(100% / 3);
    text-align: left;
    font-size: 16px;
}
/* 时间 */
.exam_view .exam_top .examTopCenter{
    width: calc(100% / 3);
    text-align: center;
    font-size: 16px;
}
/* 按钮 盒子 */
.exam_view .exam_top .examTopRight{
    width: calc(100% / 3);
    text-align: right;
}
/* 按钮 */
.exam_view .exam_top .examTopRight .endBtn{
    margin: 0px 10px 0px 0px;
    padding: 0px 10px;
    width: auto;
    height: 32px;
    font-size: 14px;
    color: rgb(51, 51, 51);
    border-radius: 4px;
    border: 1px solid rgb(221, 221, 221);
    background: rgb(255, 255, 255);
    cursor: pointer;
}
/* 悬浮 */
.exam_view .exam_top .examTopRight .endBtn:hover{
}

/* 试卷内容 盒子 */
.exam_view .question_list{
    width: 86%;
    margin: 80px auto 0px;
    text-align: left;
    border: 0px solid rgb(238, 238, 238);
    box-shadow: none;
    border-radius: 6px;
    padding: 0px;
}

/* 题目 盒子 */
.exam_view .question_list .question{
    width: 100%;
    background: rgb(255, 255, 255);
    padding: 20px;
    margin: 0px 0px 20px;
    border-radius: 0px;
    border-width: 1px;
    border-style: solid;
    border-color: #000;
}
/* 标题 */
.exam_view .question_list .question .questionTitle{
    width: 100%;
}
/* 题目类型 单选题 */
.exam_view .question_list .question .questionTitle .el-tag--light{
    background-color: rgb(240, 249, 235);
    border-color: rgb(225, 243, 216);
    color: rgb(103, 194, 58);
}
/* 选项 盒子 */
.exam_view .question_list .question .optionList{
    padding: 10px 0px;
    text-indent: 2rem;
}
/* 选项 */
.exam_view .question_list .question .optionList .option{
    padding: 0px 0px 10px;
}
/* 答案 盒子 */
.exam_view .question_list .question .answer{
    padding: 10px 0px;
}
.exam_view .question_list .question .answer input{
    height: 36px;
    line-height: 36px;
    border: 0px solid rgb(238, 238, 238);
    padding: 0px 10px;
    width: calc(100% - 120px);
    box-sizing: border-box;
    border-radius: 0px;
    background: rgb(255, 255, 255);
}
.exam_view .question_list .question .answer .el-select{
    line-height: 36px;
    border: 1px solid #000;
    box-sizing: border-box;
    width: calc(100% - 120px);
    padding: 0px 10px;
    border-radius: 0px;
    background: rgb(255, 255, 255);
}
/* 正确答案 */
.exam_view .question_list .question .answer .el-tag--success{
    background-color: rgb(240, 249, 235);
    border-color: rgb(225, 243, 216);
    color: rgb(103, 194, 58);
}
/* 答案错误 */
.exam_view .question_list .question .answer .el-tag--danger{
    background-color: rgb(254, 240, 240);
    border-color: rgb(253, 226, 226);
    color: rgb(245, 108, 108);
}
/* 解析 盒子 */
.exam_view .question_list .question .analysis_view{
    border: 1px solid #000;
    border-radius: 0px;
    background: rgb(252, 252, 252);
    height: auto;
}
/* 标题 */
.exam_view .question_list .question .analysis_view .el-collapse-item__header{
    padding: 0px 10px;
    background: none;
    border: 0px;
    height: 40px;
}
/* 图标 */
.exam_view .question_list .question .analysis_view .el-collapse-item__header .el-collapse-item__arrow{
    color: rgb(153, 153, 153);
}
/* item */
.exam_view .question_list .question .analysis_view .el-collapse-item__content{
    background: rgb(255, 255, 255);
    padding: 0px 0px 10px;
    border-width: 0 0 1px;
    border-style: solid;
    border-color: #000;
}
/* 正确答案 */
.exam_view .question_list .question .analysis_view .el-collapse-item__content .right_view{
    text-indent: 2rem;
    color: rgb(0, 0, 0);
    font-size: 14px;
}
/* 解析 */
.exam_view .question_list .question .analysis_view .el-collapse-item__content .analysis{
    text-indent: 2rem;
    color: rgb(0, 0, 0);
    font-size: 14px;
}
/*分割线 */
.exam_view .question_list .question .el-divider--horizontal{
    display: block;
    height: 0px;
    width: 100%;
    margin: 24px 0px;
}

/*按钮盒子 */
.exam_view .btn_view{
    width: 100%;
    text-align: center;
}
/*查看解析 */
.exam_view .btn_view .analysis{
    margin: 0px 10px 0px 0px;
    padding: 0px 24px;
    width: auto;
    height: 36px;
    font-size: 14px;
    color: rgb(255, 255, 255);
    border-radius: 2px;
    border: 0px;
    background: var(--theme);
    cursor: pointer;
}
/* 下一题 */
.exam_view .btn_view .next{
    margin: 0px 10px 0px 0px;
    padding: 0px 24px;
    width: auto;
    height: 36px;
    font-size: 14px;
    color: rgb(255, 255, 255);
    border-radius: 2px;
    border: 0px;
    background: rgba(255, 123, 16, 1);
    cursor: pointer;
}
/* 提交 */
.exam_view .btn_view .submit{
    margin: 0px 10px 0px 0px;
    padding: 0px 24px;
    width: auto;
    height: 40px;
    font-size: 14px;
    color: rgb(255, 255, 255);
    border-radius: 4px;
    border: 0px;
    background: rgba(68, 68, 68, 1);
    cursor: pointer;
    outline: none;
}
/* 结束考试 */
.exam_view .btn_view .end{
    margin: 0px 10px 0px 0px;
    padding: 0px 24px;
    width: auto;
    height: 36px;
    font-size: 14px;
    color: rgb(255, 255, 255);
    border-radius: 2px;
    border: 0px;
    background: rgba(235, 177, 105, 1);
    cursor: pointer;
}

/* 成绩盒子 */
.exam_view .score_view{
    width: 86%;
    margin: 80px auto 0px;
    border: 1px solid rgb(221, 221, 221);
    box-shadow: none;
    border-radius: 8px;
    background: rgb(250 248 238);
}
/* item */
.exam_view .score_view .score_item{
    padding: 0px 0px 10px;
    text-align: center;
    font-size: 30px;
    color: rgb(51, 51, 51);
}
/* 分数 */
.exam_view .score_view .score_num{
    font-size: 26px;
    color: rgb(255, 0, 0);
}

</style>