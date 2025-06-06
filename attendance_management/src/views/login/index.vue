<template>
  <div class="login-container">
    <el-form
      ref="fromRef"
      :model="from"
      class="login-form"
      style="
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 70vh;
      "
      :rules="rules"
    >
      <div class="title-container">
        <h1 class="title">登录</h1>
      </div>
      <el-form-item style="width: 300px" :rules="rules.name">
        <el-input v-model="from.name" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item style="width: 300px" :rules="rules.password">
        <el-input
          type="password"
          v-model="from.password"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm()">登录</el-button>
        <el-button type="primary" @click="resetForm()">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'

const store = useStore()

const from = ref({
  name: '张三',
  password: 'zhangsan'
})

const rules = ref({
  name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

const submitForm = () => {
  store.dispatch('app/login', from.value)
}

const resetForm = () => {
  from.value = {
    name: '',
    password: ''
  }
}
</script>

<style></style>
