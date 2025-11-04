<template>
  <div style="padding: 10px">
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="nickName" label="昵称"/>
      <el-table-column prop="phone" label="手机"/>
      <el-table-column prop="email" label="邮箱"/>
      <el-table-column prop="isDeleted" label="状态">
      <template #default="scope">
        <span>{{ scope.row.isDeleted ? '已删除' : '正常' }}</span>
      </template>
      </el-table-column>
      <el-table-column prop="salt" label="头像"/>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column prop="updateTime" label="更新时间"/>

      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-popconfirm title="Delete this?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button link type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          v-model:current-page="pageNum"
          :page-sizes="[5,10,20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script lang="ts" setup>
import {onBeforeMount, reactive, ref} from "vue";
import {remove,selectAll} from "@/api/user/user.ts";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
  const router = useRouter()

  let search = ref<string>('');
  let pageNum = ref<number>(1);
  let pageSize = ref<number>(10);
  let total = ref<number>(0);
  // let tableData = reactive<any>([])
  let tableData = ref<any[]>([])

  onBeforeMount(() => {
    load();
  });

  function load() {
      selectAll(pageNum.value, pageSize.value, search.value).then(
          (response) => {
            tableData.value = response.data.records
            // Object.assign(tableData, response.data.records)
            total.value = response.data.total
          }
      )
  }
  function handleAdd(){
    router.push('/components/user/add')
  }
  function handleEdit(row:any) {
    router.push('/components/user/update/'+row.id)
  }
  function handleDelete(id:number) {
    remove(id).then(res=>{
      ElMessage({
        message: res.msg,
        type: 'success',
        duration: 5 * 1000
      })
      load()
    })
  }
  function handleSizeChange(size:number) {
    pageSize.value=size
    load()
  }
  function handleCurrentChange(num:number) {
    pageNum.value=num
    load()
  }
</script>
