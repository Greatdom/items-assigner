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
            访问
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="productSkuName" label="productSkuName"/>
      <el-table-column prop="productCategoryName" label="productCategoryName"/>
      <el-table-column prop="username" label="商家名"/>
      <el-table-column prop="productName" label="商品名"/>
      <el-table-column prop="price" label="price"/>
      <el-table-column prop="sales" label="销售量"/>
      <el-table-column prop="stock" label="库存"/>
      <el-table-column prop="positiveComment" label="好评量"/>
      <el-table-column prop="negativeComment" label="差评量"/>
      <el-table-column prop="userId" label="userId"/>
      <el-table-column prop="logo" label="logo"/>
      <el-table-column prop="status" label="状态"/>

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
import {feed} from "@/api/product/product.ts";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import type {SearchDTO} from "@/types/user.ts";
import type {ProductFeedDTO} from "@/types/product.ts";

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
  router.push('/product/visit/'+row.id)
}
function load() {
  const searchParams: ProductFeedDTO = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    search: search.value,
    sortColumn:'sales',
    sortOrder:'DESC'
  };
  // 传入对象格式的参数
  feed(searchParams).then(
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
