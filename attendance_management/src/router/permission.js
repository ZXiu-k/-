import router from './index'
import { isTokenExpired } from '@/utils/auth'
import { isAdmin } from '@/utils/auth'

const whiteList = ['/login'] // 不用登录就可以访问的白名单
const adminList = [
  '/admin/emp',
  '/admin/leave',
  '/admin/record',
  '/admin/summary'
]
router.beforeEach((to, from, next) => {
  if (localStorage.getItem('token')) {
    if (adminList.includes(to.path)) {
      if (isAdmin(localStorage.getItem('token'))) {
        next()
        // console.log(111)
      } else {
        next('/emp/home')
      }
    }
    if (isTokenExpired(localStorage.getItem('token'))) {
      localStorage.removeItem('token')

      next('/login')
    } else if (to.path === '/login') {
      next('/')
    } else {
      next()
    }
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }
  }
})
