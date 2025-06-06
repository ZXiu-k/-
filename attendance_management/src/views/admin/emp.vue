<template>
  <div class="emp-list-container">
    <div style="height: 80px; width: 100%; display: flex; align-items: center">
      <el-col :span="24">
        <el-button type="primary" @click="addEmp" >添加员工</el-button>
      </el-col>
      <el-dialog v-model="addEmpVisible" title="添加员工" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" type="number" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" :value="1" />
            <el-option label="女" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addEmpVisible = false">取消</el-button>
          <el-button type="primary" @click="submitAddForm">提交</el-button>
        </span>
      </template>
    </el-dialog>





<!-- 员工更改 -->
    <el-dialog v-model="updateEmpVisible" title="更改员工信息" width="30%">
      <el-form :model="updateForm" label-width="120px">
        <el-form-item label="姓名">
          <el-input v-model="updateForm.name" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="updateForm.age" type="number" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="updateForm.password" type="password" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="updateForm.gender" placeholder="请选择性别">
            <el-option label="男" :value="1" />
            <el-option label="女" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="updateEmpVisible = false">取消</el-button>
          <el-button type="primary" @click="submitUpdateForm">提交</el-button>
        </span>
      </template>
    </el-dialog>

    
    </div>
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column type="index" label="序号" width="120" />
        <el-table-column prop="id" label="工号" width="120" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="age" label="年龄" width="120" />
        <el-table-column prop="gender" label="性别">
          <template #default="scope">
            {{ scope.row.gender === 1 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240">
      <template #default="scope">
        <el-button
          size="small"
          type="primary"
          @click="handleUpdate(scope.row)"
          
        >
          更改
        </el-button>
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.row)"

        >
          删除
        </el-button>
      </template>
    </el-table-column>
      </el-table>
      <el-pagination
        :page-size="10"
        :pager-count="11"
        layout="prev, pager, next"
        :total="total"
        :current-page="page"
        hide-on-single-page
        @click="emps"
        @current-change="handlePageChange"
      />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAllEmps } from '@/adminApi/getAllEmps'
import { addEmp as addEmpApi } from '@/adminApi/addEmp'
import { deleteEmp as deleteEmpApi } from '@/adminApi/deleteEmp'
import { updateEmp as updateEmpApi } from '@/adminApi/updateEmp'

const tableData = ref([])

let page = ref('1')
let total = ref()


const handlePageChange = (newPage) => {
  page.value = newPage
}

const emps = () => {
  getAllEmps(page.value, 10)
    .then((res) => {
      tableData.value = res.data.data.rows
      total.value = res.data.data.total
    })
    .catch((err) => {
      alert(err)
    })
}
// const tableData = ref([])
const addEmpVisible = ref(false)
const updateEmpVisible = ref(false)
const form = ref({
  name: '',
  age: '',
  password: '',
  gender: ''
})
const updateForm = ref({
  id:'',
  name: '',
  age: '',
  password: '',
  gender: ''
})

onMounted(() => {
  emps()
})

const addEmp = () => {
  addEmpVisible.value = true
}

const handleUpdate = (row) =>{
  updateEmpVisible.value = true
  updateForm.value=row
}

const handleDelete = (row) => {
  deleteEmpApi(row)
  window.location.reload()
}

const submitUpdateForm = () =>{
  updateEmpApi(updateForm.value)
  updateForm.value.password = null
  updateEmpVisible.value = false
}

const submitAddForm = () => {
  addEmpApi(form.value)
  addEmpVisible.value = false
  window.location.reload()
}

</script>

<style scoped>

</style>