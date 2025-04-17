<template>
	<div>
		<el-dialog class="edit_form" v-model="formVisible" :title="formTitle" width="70%" destroy-on-close :fullscreen='false'>
			<el-form class="formModel_form" ref="formRef" :model="form" :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="题目类型" prop="type">
							<el-select class="list_sel" :disabled="!isAdd" v-model="form.type" placeholder="题目类型" style="width:100%" @change="typeChange">
									<el-option v-for="item in [{name:'单选题',type:0},{name:'多选题',type:1}]"
                                               :key="item.type" :label="item.name" :value="item.type"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="题目" prop="questionname">
							<el-input :readonly="!isAdd" v-model="form.questionname" placeholder="题目" type="textarea"></el-input>
						</el-form-item>
					</el-col>
					<!-- 单选题 -->
					<el-col :span="24" v-if="form.type==0">
						<el-form-item label="选项" prop="optionList">
							<el-input class="list_inpGroup" :readonly="!isAdd" v-for="(item,index) in form.optionList" :key="index" v-model="item.text"
								style="margin-bottom: 6px;" :placeholder="`选项${codeChange(index)}`">
								<template #prepend>{{codeChange(index)}}</template>
								<template #append v-if="isAdd">
									<el-button :icon="Delete" @click.native="delOption(index)" />
								</template>
							</el-input>
							<div style="width: 100%;text-align: center;padding-top: 10px;"
								v-if="form.optionList.length<4&&isAdd"><el-button circle :icon="Plus" type="success"
									@click.native="addOption"></el-button></div>
						</el-form-item>
					</el-col>
					<!-- 单选题 -->
					<!-- 多选题 -->
					<el-col :span="12" v-if="form.type==1">
						<el-form-item label="选项" prop="optionList">
							<el-input class="list_inpGroup" :readonly="!isAdd" v-for="(item,index) in form.optionList" :key="index" v-model="item.text"
								style="margin-bottom: 6px;" :placeholder="`选项${codeChange(index)}`">
								<template #prepend>{{codeChange(index)}}</template>
								<template #append v-if="isAdd">
									<el-button :icon="Delete" @click.native="delOption(index)" />
								</template>
							</el-input>
							<div style="width: 100%;text-align: center;padding-top: 10px;"
								v-if="form.optionList.length<4&&isAdd"><el-button circle :icon="Plus" type="success"
									@click.native="addOption"></el-button></div>
						</el-form-item>
					</el-col>
					<!-- 多选题 -->
					<el-col :span="12">
						<el-form-item label="排序" prop="sequence">
							<el-input class="list_inp" :readonly="!isAdd" v-model="form.sequence" placeholder="排序" type="number" :min="1"
								:max="20"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer v-if="isAdd">
				<span class="formModel_btn_box">
					<el-button class="cancel_btn" @click="closeClick">取消</el-button>
					<el-button class="confirm_btn" type="primary" @click="save">
						提交
					</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>
<script setup>
	import {
		reactive,
		ref,
		getCurrentInstance,
		nextTick,
		computed,
		defineEmits
	} from 'vue'
	import {
		Delete,
		Plus
	} from '@element-plus/icons-vue'
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const emit = defineEmits(['formModelChange'])
	//基础信息
	const tableName = 'examquestionbank'
	const formName = '题目'
	//基础信息
	//form表单
	const form = ref({
		papername: '',
		paperid: '',
		questionname: '',
		sequence: '',
		type: '',
		optionList: [{
			text: '',
		},{
			text: '',
		}],
	})
	const disabledForm = ref({
		name: false,
		time: false,
		status: false,
	})
	const formVisible = ref(false)
	const formTitle = ref('')
	//表单验证
	//匹配数字
	const validateNumber = (rule, value, callback) => {
		if (!value) {
			callback();
		} else if (!context?.$toolUtil.isNumber(value)) {
			callback(new Error("请输入数字"));
		} else {
			callback();
		}
	}
	const rules = ref({
		type: [{
			required: true,
			message: '请选择题目类型',
			trigger: 'blur'
		}, ],
		questionname: [{
			required: true,
			message: '请输入题目',
			trigger: 'blur'
		}, ],
		sequence: [{
			required: true,
			message: '请输入排序',
			trigger: 'blur'
		}, ],
	})
	//表单验证
	const formRef = ref(null)
	const id = ref(0)
	//获取唯一标识
	const getUUID = () => {
		return new Date().getTime();
	}
	//重置
	const resetForm = () => {
		form.value = {
			papername: '',
			paperid: '',
			questionname: '',
			sequence: '',
			type: '',
			optionList: [{
				text: '',
			},{
				text: '',
			}],
		}
	}
	//获取info
	const getInfo = () => {
		context?.$http({
			url: `examquestionbank/info/${id.value}`,
			method: 'get'
		}).then(res => {
			if (res.data.data.options) {
				res.data.data.optionList = JSON.parse(res.data.data.options)
			} else {
				res.data.data.optionList = []
			}
			form.value = res.data.data
			formVisible.value = true
		})
	}
	//初始化
	const isAdd = ref(false)
	const init = (refid = null, type = null) => {
        if(!refid){
          formTitle.value = '添加'
          isAdd.value = true
        }else if(type=='edit'){
          formTitle.value = '编辑'
          isAdd.value = true
        }else{
          formTitle.value = '查看'
          isAdd.value = false
        }
		resetForm()
		if (refid) {
			id.value = refid
			getInfo()
		}
		formVisible.value = true
	}
	//初始化
	//题目类型
	const typeChange = (e) => {
		if (e == 3) {
			form.value.optionList = []
		}
        else {
			form.value.optionList = [{
				text: ''
			}, {
				text: ''
			}]
		}
	}
	const codeChange = (type) => {
		let arr = ['A', 'B', 'C', 'D']
		return arr[type]
	}
	//删除选项
	const delOption = (index) => {
		form.value.optionList.splice(index, 1)
	}
	//新增选项
	const addOption = () => {
		form.value.optionList.push({
			text: '',
		})
	}
	//声明父级调用
	defineExpose({
		init
	})
	//关闭
	const closeClick = () => {
		formVisible.value = false
	}
	//提交
	const save = () => {
		formRef.value.validate((valid) => {
			if (valid) {
				if (form.value.type == 0 || form.value.type == 1 || form.value.type == 2) {
					if (form.value.optionList.length > 1) {
						for(let x in form.value.optionList){
							form.value.optionList[x].code = codeChange(x)
						}
						form.value.options = JSON.stringify(form.value.optionList)
					} else {
						context?.$toolUtil.message('至少输入一个选项', 'error')
						return false
					}
				} else {
					form.value.options = ''
				}
				context?.$http({
					url: `examquestionbank/${!form.value.id ? "save" : "update"}`,
					method: 'post',
					data: form.value
				}).then(res => {
					context.$message.success(`操作成功`)
                    formVisible.value = false
                    emit('formModelChange')
				})
			}
		})
	}
</script>
<style lang="scss" scoped>
	// 表单
	.formModel_form{
		// form item
		:deep(.el-form-item) {
			// 内容盒子
			.el-form-item__content{

				// 输入框
				.list_inp {
				}
				// 下拉框
				.list_sel {
					//去掉默认样式
					.select-trigger{
						height: 100%;
						.el-input{
							height: 100%;
						}
					}
				}
				// 长文本
				.el-textarea__inner {
				}
				// 选项输入框
				.list_inpGroup {

					.el-input-group__prepend {
					}

					.el-input__wrapper {
					}

					.el-input-group__append {

						.el-input {
						}

						.el-button {
						}
					}
				}
			}
		}
	}

</style>