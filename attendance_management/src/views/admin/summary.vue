<template>
  <div class="emp-list-container">
    <div style="height: 80px; width: 100%; display: flex; align-items: center; justify-content: space-between">
      <el-col style="text-align: center">
        <el-date-picker
          class="picker"
          v-model="yearMonth"
          type="month"
          placeholder="选择月份"
          format="YYYY-MM"
          value-format="YYYY-MM"
        />
        <el-button type="primary" style="margin-left: 10px" @click="getSummary(yearMonth)">查询</el-button>
      </el-col>
    </div>

    <!-- 月度考勤统计表格 -->
    <div v-if="showSummary" style="margin-top: 20px">
      <h3>{{ yearMonth }} 月度考勤统计</h3>
      <el-table :data="summaryData" border style="width: 100%; margin-top: 20px">
        <el-table-column type="index" label="序号" width="80" />
        <el-table-column prop="empId" label="工号" width="120" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="workDaysTotal" label="应出勤天数" width="120" />
        <el-table-column prop="normalDays" label="正常出勤" width="120" />
        <el-table-column prop="lateDays" label="迟到次数" width="120" />
        <el-table-column prop="earlyDays" label="早退次数" width="120" />
        <el-table-column prop="lateEarlyDays" label="迟到早退" width="120" />
        <el-table-column prop="incompleteDays" label="缺卡次数" width="120" />
        <el-table-column prop="absentDays" label="缺勤天数" width="120" />
        <el-table-column prop="leaveDays" label="请假天数" />
      </el-table>
    </div>
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getSummary as getSummaryApi} from '@/adminApi/getSummary'


const yearMonth = ref('')
const showSummary = ref(false)
const summaryData = ref([])
const page = ref(1)
const total = ref(0)

const handlePageChange = (newPage) => {
  page.value = newPage
  getSummary(yearMonth.value)
}

const getSummary = async (yearMonth) => {
  if (!yearMonth) {
    showSummary.value = false
    return
  }
  try {
    const res = await getSummaryApi(yearMonth,page.value,10)
    summaryData.value = res.data.data.rows
    total.value = res.data.data.total
    showSummary.value = true
  } catch (err) {
    ElMessage.error('获取月度统计失败')
  }
}

onMounted(()=>{
})

</script>

<style scoped>
</style>