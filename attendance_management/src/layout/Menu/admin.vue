<template>
  <el-menu
    active-text-color="#ffd04b"
    background-color="#545c64"
    class="el-menu-vertical-demo"
    :default-active="defaultActive"
    text-color="#fff"
    router
    style="height: 100%;"
  >
  <el-menu-item v-for="item in menuList" :index="item.path" :key="item.path" @Click="savePath">
      <span style="width: 100%">{{ item.name }}</span>
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { ref } from 'vue'

const menuList = ref([
  {
    path: '/admin/emp',
    name: '员工管理',
  },
  {
    path: '/admin/record',
    name: '考勤记录',
  },
  {
    path: '/admin/leave',
    name: '请假审批',
  },
  {
    path: '/admin/summary',
    name: '考勤总结',
  },
])
const savePath = (path) => {
  sessionStorage.setItem('path', path)
}
const defaultActive = ref(sessionStorage.getItem('path')||'/emp/home')

</script>

<style>
</style>
