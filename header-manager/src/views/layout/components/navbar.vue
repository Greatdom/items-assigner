<template>
  <nav class="navbar">

    <!-- 下拉触发区域（用户信息） -->
    <div class="user-info" role="button">
      <img :src="user.avatar" class="avatar">
      <span class="username">{{ user.nickName }}</span>
      <i class="el-icon-caret-bottom"></i>
    </div>

    <el-button @click="handlePerson">个人信息</el-button>
    <el-button @click="handleLogout">登出</el-button>

  </nav>
</template>

<script setup lang="ts">
import { useUserStore } from "@/store/modules/user.ts";
import {useRouter} from "vue-router";

// 获取用户信息
const userStore = useUserStore();
const user = userStore.currentUser;
const router = useRouter()

function handlePerson(){
  router.push('/home');
}

function handleLogout(){
  userStore.HandleLogout()
}
</script>

<style scoped>
.navbar {
  height: 60px;
  background-color: #2c3e50;
  color: white;
  padding: 0 20px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid rgba(255,255,255,0.2);
}


</style>