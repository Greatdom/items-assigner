<script setup lang="ts">
import {useUserStore} from "@/store/modules/user.ts";
import {useRouter} from "vue-router";

const userStore = useUserStore()
const currentUser = userStore.currentUser
const router = useRouter()
function handleEdit(id:number) {
  router.push('/user/update/'+id)
}
</script>

<template>
  <div>
    <span>ID :{{ currentUser.id }}</span>
    <br><br/>
    <span>NICKNAME :{{ currentUser.nickName }}</span>
    <br><br/>
    <span>USERNAME : {{ currentUser.username}}</span>
    <br><br/>
    <span>PERMISSION : {{ currentUser.permissionValueList }}</span>
    <br><br/>
    <span>ROLES : {{ currentUser.roles }}</span>
  </div>
  <el-button link type="primary" size="small" @click="handleEdit(currentUser.id)">
    编辑
  </el-button>
</template>

<style scoped>

</style>