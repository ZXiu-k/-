<template>
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
import { getAllRecords } from '@/adminApi/getAllRecords'
import { ref } from 'vue'
import { onMounted } from 'vue'

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
  getAllRecords(page.value, 10)
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