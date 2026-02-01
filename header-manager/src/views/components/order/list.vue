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
            订单操作
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="buyerId" label="buyerId"/>
      <el-table-column prop="merchantId" label="merchantId"/>
      <el-table-column prop="productId" label="productId"/>
      <el-table-column prop="skuId" label="skuId"/>
      <el-table-column prop="quantity" label="quantity"/>
      <el-table-column prop="productName" label="productName"/>
      <el-table-column prop="skuSpecs" label="skuSpecs"/>
      <el-table-column prop="logo" label="logo"/>
      <el-table-column prop="totalPrice" label="totalPrice"/>
      <el-table-column prop="payPrice" label="payPrice"/>
      <el-table-column prop="status" label="名字"/>
      <el-table-column prop="userProfileVO" label="userProfileVO">
        <template #default="scope">
          <span>username:{{ scope.row.userProfileVO.username }}</span>
          <span>nickName:{{ scope.row.userProfileVO.nickName }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="isDeleted" label="状态">
        <template #default="scope">
          <span>{{ scope.row.isDeleted ? '已删除' : '正常' }}</span>
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
import {listUser} from "@/api/order/order.ts";
import {useRouter} from "vue-router";
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
  router.push('/order/operate/'+row.id)
}
function load() {
  // 构造SearchDTO格式的参数对象
  const searchParams: SearchDTO = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    search: search.value
  };
  // 传入对象格式的参数
  listUser(searchParams).then(
      (response) => {
        console.log(response.data.records)
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
