<template>
  <div class="demo-type">
    <el-dropdown>
      <span class="el-dropdown-link">
        <div>
          <el-avatar :icon="UserFilled" shape="square"/>
        </div>
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <!-- <div>
      <el-avatar
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
      />
    </div> -->
  </div>
</template>

<script setup>
import router from '@/router';
import { UserFilled } from '@element-plus/icons-vue'
// import { ref } from 'vue'

// const avatarUrl = ref(
//   'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
// )

const logout = () =>{
  // console.log(111)
  localStorage.removeItem('token')
  router.replace("/login")
}

</script>

<style scoped>
.demo-type {
  display: flex;
}
.demo-type > div {
  flex: 1;
  text-align: center;
}

.demo-type > div:not(:last-child) {
  border-right: 1px solid var(--el-border-color);
}
</style>
