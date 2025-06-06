<template>
  <div class="leave-form">
    <el-form :model="formData" :rules="rules" ref="formRef" label-width="100px">
      <el-form-item label="请假日期" prop="date">
        <el-date-picker
          v-model="formData.date"
          type="date"
          placeholder="选择日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          :size="size"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="请假原因" prop="reason">
        <el-input
        v-model="formData.reason"
        type="textarea"
        :rows="4"
        placeholder="请输入请假原因"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
    <el-table :data="tableData" border style="width: 100%">
    <el-table-column type="index" label="序号" width="120" />
    <el-table-column prop="empId" label="员工ID" width="120" />
    <el-table-column prop="date" label="请假时间" width="180" />
    <el-table-column prop="reason" label="请假原因" />
    <el-table-column prop="status" label="状态" width="120" >
      <template #default="scope">
        <el-tag :type="scope.row.status === 1 ? 'success' : 'warning'">
          {{ scope.row.status === 1 ? '已批准' : '未审批' }}
        </el-tag>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
    :page-size="10"
    :pager-count="11"
    layout="prev, pager, next"
    :total="total"
    :current-page="page"
    hide-on-single-page
    @click="getLeave"
    @current-change="handlePageChange"
  />
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { askForLeave } from '@/empApi/askForLeave'
import { getLeave as getLeaveApi  } from '@/empApi/getLeave'

const formRef = ref(null)
const formData = reactive({
  date: '',
  reason: ''
})

let page = ref('1')
let total = ref()

let tableData = ref([])

const handlePageChange = (newPage) => {
  page.value = newPage
}

onMounted(() => {
  getLeave()
})

const getLeave = () =>{
  getLeaveApi(page.value, 10)
    .then((res) => {
      tableData.value = res.data.data.rows
      total.value = res.data.data.total
    })
    .catch((err) => {
      alert(err)
    })
}

const rules = {
  date: [
    { required: true, message: '请选择请假日期', trigger: 'change' }
  ],
  reason: [
    { required: true, message: '请输入请假原因', trigger: 'blur' },
    { min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur' }
  ]
}

const submitForm = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    await askForLeave(formData)
    ElMessage.success('提交成功')
    getLeave() // 刷新请假记录
    } catch (error) {
    // ElMessage.error('请检查表单填写是否正确')
  }
}
const resetForm = () => {
  if (!formRef.value) return
  formRef.value.resetFields()
}


</script>

<style scoped>
.leave-form {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
}
</style>