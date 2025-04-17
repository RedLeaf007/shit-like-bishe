<template>
  <div class="center_view">
    <div class="list_search_view">
      <el-form :model="searchQuery" class="search_form">
        <div class="search_view">
          <div class="search_label">
            题目：
          </div>
          <div class="search_box">
            <el-input class="search_inp" v-model="searchQuery.questionname" placeholder="题目名称"
                      clearable>
            </el-input>
          </div>
        </div>
        <div class="search_btn_view">
          <el-button class="search_btn" type="primary" @click="searchClick()" size="small">搜索</el-button>
        </div>
      </el-form>
      <br>
      <div class="btn_view">
        <el-button class="add_btn" type="success" @click="addClick" v-if="btnAuth('examquestionbank','新增')">
            新增
        </el-button>
        <el-button class="del_btn" type="danger" :disabled="selRows.length?false:true" @click="delClick(null)">
            删除
        </el-button>
        <el-button class="other_btn" type="default" :disabled="selRows.length?false:true" @click="exportClick" v-if="btnAuth('examquestionbank','导出')">
            导出
        </el-button>
        <el-button class="other_btn" type="default" @click="printClick" v-if="btnAuth('examquestionbank','打印')">
            打印
        </el-button>
      </div>
    </div>
    <el-table
        v-loading="listLoading"
        border
        :stripe='true'
        @selection-change="handleSelectionChange"
        ref="table"
        v-if="btnAuth('examquestionbank','查看')"
        :data="list"
        @row-click="listChange">
      <el-table-column :resizable='true' align="left" header-align="left" type="selection" width="55" />
      <el-table-column
          :resizable='true'
          :sortable='true'
          align="left"
          header-align="left"
          prop="questionname"
          label="题目名称">
      </el-table-column>
      <el-table-column
          :resizable='true'
          :sortable='true'
          align="left"
          header-align="left"
          prop="type"
          label="类型">
        <template #default="scope">
            <el-tag type="success" v-if="scope.row.type==0">单选题</el-tag>
            <el-tag type="warning" v-if="scope.row.type==1">多选题</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300" :resizable='true' :sortable='true' align="left" header-align="left">
        <template #default="scope">
            <el-button class="edit_btn" type="primary" @click="editClick(scope.row.id)" v-if="btnAuth('examquestionbank','修改')">
                修改            </el-button>
            <el-button class="del_btn" type="danger" @click="delClick(scope.row.id)" v-if="btnAuth('examquestionbank','删除')">
                删除            </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        background
        :layout="layouts.join(',')"
        :total="total"
        :page-size="listQuery.limit"
        v-model:current-page="listQuery.page"
        prev-text="<"
        next-text=">"
        :hide-on-single-page="false"
        :style='{"width":"100%","padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"#333","fontWeight":"500"}'
        :page-sizes="[10, 20, 30, 40, 50, 100]"
        @size-change="sizeChange"
        @current-change="currentChange"  />
    <formModel ref="formRef" @formModelChange="formModelChange"></formModel>
  </div>
</template>
<script setup>
    import axios from 'axios'
    import {
      reactive,
      ref,
      getCurrentInstance,
      nextTick,
      onMounted,
      watch,
      computed,
    } from 'vue'
    import {
      useRoute,
      useRouter
    } from 'vue-router'
    import {
      ElMessageBox
    } from 'element-plus'
    const context = getCurrentInstance()?.appContext.config.globalProperties;
    import formModel from './formModel.vue'
    //列表数据
    const list = ref(null)
    const table = ref(null)
    const listQuery = ref({
      page: 1,
      limit: 20,
      sort: 'id',
      order: 'desc'
    })
    const searchQuery = ref({
      questionname:''
    })
    const selRows = ref([])
    const listLoading = ref(false)
    const listChange = (row) =>{
      nextTick(()=>{
        table.value.toggleRowSelection(row)
      })
    }
    const searchClick = () => {
      listQuery.value.page = 1
      getList()
    }
    const formRef = ref(null)
    const formModelChange=()=>{
      searchClick()
    }
    const addClick = ()=>{
      formRef.value.init()
    }
    const editClick = (id=null)=>{
      if(id){
        formRef.value.init(id,'edit')
        return
      }
      if(selRows.value.length){
        formRef.value.init(selRows.value[0].id,'edit')
      }
    }
    //列表
    const getList = () => {
      listLoading.value = true
      let params = {
        sort:'id',
        order:'desc'
      }
      if(searchQuery.value.questionname){
        params['questionname'] = '%' + searchQuery.value.questionname + '%'
      }
      context?.$http({
        url: `examquestionbank/page`,
        method: 'get',
        params: params
      }).then(res => {
        listLoading.value = false
        list.value = res.data.data.list
        total.value = Number(res.data.data.total)
      })
    }
    getList()
    //多选
    const handleSelectionChange = (e) => {
      selRows.value = e
    }
    //分页
    const total = ref(0)
    const layouts = ref(["total","prev","pager","next","sizes","jumper"])
    const sizeChange = (size) => {
      listQuery.value.limit = size
      getList()
    }
    const currentChange = (page) => {
      listQuery.value.page = page
      getList()
    }
    //权限验证
    const btnAuth = (e,a)=>{
      return context?.$toolUtil.isAuth(e,a)
    }
    //删
    const delClick = (id) => {
      let ids = ref([])
      if (id) {
        ids.value = [id]
      } else {
        if (selRows.value.length) {
          for (let x in selRows.value) {
            ids.value.push(selRows.value[x].id)
          }
        } else {
          return false
        }
      }
      ElMessageBox.confirm(`是否删除选中题目`, '提示', {
        confirmButtonText: '是',
        cancelButtonText: '否',
        type: 'warning',
      }).then(() => {
        context?.$http({
          url: `examquestionbank/delete`,
          method: 'post',
          data: ids.value
        }).then(res => {
            context.$message.success('删除成功')
            getList()
        })
      })
    }
    //导出选中数据
    const exportClick = () => {
      import('@/utils/Export2Excel').then(excel => {
        const tHeader = [
        ]
        const filterVal = [
        ]
        excel.export_json_to_excel2(tHeader, selRows.value, filterVal, formName)
      })
    }
    //打印
    const printClick = () => {
        let data = []
      list.value.forEach(item=>{
        let typeName = '';
        switch (item.type){
          case 0:typeName='单选题';break
          case 1:typeName='多选题';break
        }
        data.push({
          questionname: item.questionname,
          type: typeName,
          options: item.options,
        })
      })
      printJS({
          printable: data,
          properties: [
            {
              field: 'questionname',
              displayName: '题目名称',
              columnSize: 1
            },
            {
              field: 'type',
              displayName: '题目类型',
              columnSize: 1
            },
            {
              field: 'options',
              displayName: '选项',
              columnSize: 1
            },
          ],
          type: 'json',
          header: '试卷题目',
          gridStyle: 'border: 1px solid #3971A5; text-align: center; padding: 6px 10px',
          gridHeaderStyle: 'color: #000; border: 1px solid #3971A5; padding: 6px 10px'
      })
    }
</script>