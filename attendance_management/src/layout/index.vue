<template>
  <div class="app-container">
    <el-container style="width: 100%; height: 100%">
      <el-aside width="200px" class="sidebar-container">
        <Admin v-if="admin == true" /><Emp v-else />
      </el-aside>
      <el-container class="containter">
        <el-header>
          <Header />
        </el-header>
        <el-main style="padding: 0">
          <router-view />
        </el-main>
        <!-- <el-footer>Footer</el-footer> -->
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { ref } from 'vue'
import Admin from './Menu/admin.vue'
import Emp from './Menu/emp.vue'
import Header from './Header'
import { isAdmin } from '@/utils/auth'

export default {
  components: {
    Admin,
    Emp,
    Header
  },
  setup() {
    const admin = ref(isAdmin(localStorage.getItem('token')))
    return { admin }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  position: relative;
  width: 100%;
  height: 100%;
}
.container {
  height: 100%;
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  transition: all 0.28s;
}
.el-header {
  padding: 0;
}
</style>
