import { createRouter, createWebHistory } from 'vue-router'
import { isAdmin } from '@/utils/auth'

const defaultPath = isAdmin(localStorage.getItem('token'))
  ? '/admin/emp'
  : '/emp/home'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login/index.vue')
  },
  {
    path: '/',
    name: '/',
    component: () => import('../layout'),
    redirect: defaultPath,
    children: [
      {
        path: 'admin/emp',
        name: 'Emps',
        component: () => import('../views/admin/emp.vue'),
        meta: {
          requiresAuth: true,
          allowedRoles: ['admin']
        }
      },
      {
        path: 'admin/leave',
        name: 'Leave',
        component: () => import('../views/admin/leave.vue'),
        meta: {
          requiresAuth: true,
          allowedRoles: ['admin']
        }
      },
      {
        path: 'admin/record',
        name: 'Record',
        component: () => import('../views/admin/record.vue'),
        meta: {
          requiresAuth: true,
          allowedRoles: ['admin']
        }
      },
      {
        path: 'admin/summary',
        name: 'Summary',
        component: () => import('../views/admin/summary.vue'),
        meta: {
          requiresAuth: true,
          allowedRoles: ['admin']
        }
      },
      {
        path: 'emp/home',
        name: 'Home',
        component: () => import('../views/emp/home.vue')
      },
      {
        path: '/emp/check',
        name: 'Check',
        component: () => import('../views/emp/check.vue')
      },
      {
        path: '/emp/leave',
        name: 'EmpLeave',
        component: () => import('../views/emp/leave.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
