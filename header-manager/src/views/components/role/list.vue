<template>
  <div style="padding: 10px">
    <div style="margin: 10px 0">
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="handleEdit(scope.row)">
            查看
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="name" label="名字"/>
      <el-table-column prop="group" label="用户组">

      </el-table-column>
      <el-table-column prop="isDeleted" label="状态">
        <template #default="scope">
          <span>{{ scope.row.isDeleted ? '已删除' : '正常' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column prop="updateTime" label="更新时间"/>
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
import {list} from "@/api/user/role.ts";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import type {SearchDTO} from "@/types/user.ts";

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
function handleEdit(row:any) {
  router.push('/role/detail/'+row.id)
}
function load() {
  // 构造SearchDTO格式的参数对象
  const searchParams: SearchDTO = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    search: search.value
  };
  // 传入对象格式的参数
  list(searchParams).then(
      (response) => {

        tableData.value = response.data.records
        total.value = response.data.total
      }
  )
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
