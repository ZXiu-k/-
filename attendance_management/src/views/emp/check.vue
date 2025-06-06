<template>
  <div style="height: 80px; display: flex; align-items: center">
    <el-col :span="12">
      <el-button type="primary" @click="checkIn">签到</el-button>
    </el-col>
    <el-col :span="12">
      <el-button type="primary" @click="checkOut">签退</el-button>
    </el-col>
  </div>
  <el-table :data="tableData" border style="width: 100%">
    <el-table-column type="index" label="序号" width="120" />
    <el-table-column prop="empId" label="员工ID" width="180" />
    <el-table-column prop="checkInTime" label="签到时间" width="240" />
    <el-table-column prop="checkOutTime" label="签退时间" />
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
import { getRecords } from '@/empApi/empGetRecord'
import { checkIn as checkInApi } from '@/empApi/checkIn'
import { checkOut as checkOutApi } from '@/empApi/checkOut'
import { ref } from 'vue'
import { onMounted } from 'vue'
import { ElMessage } from 'element-plus'

let tableData = ref([])

let page = ref('1')
let total = ref()

const handlePageChange = (newPage) => {
  page.value = newPage
}

onMounted(() => {
  record()
})

const checkIn = () => {
  checkInApi()
    .then((res) => {
      if (res.data.code !== 500) {
        ElMessage('签到成功')
      } else {
        ElMessage(res.data.msg)
      }
    })
    .catch((err) => {
      ElMessage(err)
    })
}
const checkOut = () => {
  checkOutApi()
    .then((res) => {
      if (res.data.code !== 500) {
        ElMessage('签退成功')
      } else {
        ElMessage(res.data.msg)
      }
    })
    .catch((err) => {
      alert(err)
    })
}
const record = () => {
  getRecords(page.value, 10)
    .then((res) => {
      tableData.value = res.data.data.rows
      total.value = res.data.data.total
    })
    .catch((err) => {
      alert(err)
    })
}
</script>
<style>
</style>