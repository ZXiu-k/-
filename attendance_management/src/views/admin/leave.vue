<template>
  <el-table :data="tableData" border style="width: 100%">
    <el-table-column type="index" label="序号" width="80" />
    <el-table-column prop="empId" label="员工ID" width="80" />
    <el-table-column prop="date" label="请假时间" width="180" />
    <el-table-column prop="reason" label="请假原因" />
    <el-table-column prop="status" label="状态" width="120" >
      <template #default="scope">
        <el-tag :type="scope.row.status === 1 ? 'success' : 'warning'">
          {{ scope.row.status === 1 ? '已批准' : '未审批' }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column label="操作" width="180">
      <template #default="scope">
        <el-button type="primary" size="small" @click="approve(scope.row)">同意</el-button>
        <el-button type="primary" size="small" @click="disApprove(scope.row)">不同意</el-button>
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
    @click="record"
    @current-change="handlePageChange"
  />
</template>

<script setup>
import { getLeave } from '@/adminApi/getLeave'
import { ElMessage } from 'element-plus'
import { ref } from 'vue'
import { onMounted } from 'vue'
import { approve as approveApi, disApprove as disApproveApi } from '@/adminApi/reviewLeave'

let tableData = ref([])

let page = ref('1')
let total = ref()

const handlePageChange = (newPage) => {
  page.value = newPage
}

onMounted(() => {
  record()
})

const record = () => {
  getLeave(page.value, 10)
    .then((res) => {
      tableData.value = res.data.data.rows
      total.value = res.data.data.total
    })
    .catch((err) => {
      alert(err)
    })
}

const approve = (row) => {
  approveApi(row)
    .then((res) => {
      ElMessage.success(res.data.data)
      record()
    })
    .catch((err) => {
      alert(err)
    })
  }
  
  
const disApprove = (row) => {
  disApproveApi(row)
    .then((res) => {
      ElMessage.success(res.data.data)
      record()
    })
    .catch((err) => {
      alert(err)
    })
}
</script>


<style>

</style>