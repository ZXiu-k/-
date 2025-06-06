<template>
  <el-menu
    active-text-color="#ffd04b"
    background-color="#545c64"
    class="el-menu-vertical-demo"
    :default-active="defaultActive"
    text-color="#fff"
    router
    style="height: 100%"
  >
    <el-menu-item
      v-for="item in menuList"
      :index="item.path"
      :key="item.path"
      @click="savePath(item.path)"
    >
      <span style="width: 100%">{{ item.name }}</span>
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { ref } from 'vue'

const savePath = (path) => {
  sessionStorage.setItem('path', path)
}
const defaultActive = ref(sessionStorage.getItem('path')||'/emp/home')

const menuList = ref([
  {
    path: '/emp/home',
    name: '首页'
  },
  {
    path: '/emp/check',
    name: '考勤'
  },
  {
    path: '/emp/leave',
    name: '请假'
  }
])
</script>

<style></style>
