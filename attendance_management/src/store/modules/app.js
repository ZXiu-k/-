import { login as loginApi } from '@/empApi/login'
import router from '@/router'
import { isAdmin } from '@/utils/auth'
import { ElMessage } from 'element-plus'

export default {
  namespaced: true,
  state: () => ({
    token: localStorage.getItem('token') || '',
  }),
  mutations: {
    setToken(state, token) {
      state.token = token
      localStorage.setItem('token', token)
    },
  },
  actions: {
    login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        loginApi(userInfo)
          .then((res) => {
            // console.log(res)
            commit('setToken', res.data.data)
            if(res.data.code === 200){
              ElMessage.success('登录成功') 
            }else{
              ElMessage.error(res.data.msg)
            }
            if(isAdmin(res.data.data)){
              router.replace('/admin/emp')
            }else{
              router.replace('/emp/home')
            }
            resolve()
          })
          .catch((err) => {
            reject(err)
          })
      })
    },
  }
}
