<template>
  <div class="emp-home-container">
    <el-card class="emp-info-card">
      <div class="emp-title">
        <el-icon style="margin-right: 8px; color: #409eff;">
          <UserFilled />
        </el-icon>
        员工信息
      </div>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="工号">{{ info.id }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ info.name }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ info.age }}</el-descriptions-item>
        <el-descriptions-item label="性别">
            {{ info.gender === 1 ? '男' : '女' }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { getEmpInfo } from '@/empApi/getEmpInfo'
import { ref, onMounted } from 'vue'
import { UserFilled } from '@element-plus/icons-vue'

let info = ref({})

onMounted(() => {
  getEmpInfo()
    .then((res) => {
      info.value = res.data.data
    })
    .catch((err) => {
      alert(err)
    })
})
</script>

<style scoped>
.emp-home-container {
  display: flex;
}
.emp-info-card {
  width: 400px;
  border-radius: 16px;
  box-shadow: 0 4px 24px rgba(0,0,0,0.08);
  padding: 32px 24px 24px 24px;
}
.emp-title {
  font-size: 20px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 24px;
  display: flex;
  align-items: center;
  letter-spacing: 2px;
}
</style>