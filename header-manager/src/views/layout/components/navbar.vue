<script setup lang="ts">
import {useUserStore} from "@/store/modules/user.ts";
const userStore = useUserStore()
const user = userStore.currentUser
function logout() {
  userStore.logout().then(() => {
    location.reload()
  })
}
</script>

<template>
  <el-menu class="navbar" mode="horizontal">
    <el-dropdown class="avatar-container" trigger="click">

      <div class="avatar-wrapper">
        <img :src="user.salt" class="user-avatar">
        <div class="user-name">{{user.nickName}}</div>
        <i class="el-icon-caret-bottom"/>
      </div>

      <el-dropdown-menu slot="dropdown" class="user-dropdown">
        <router-link class="inlineBlock" to="/">
          <el-dropdown-item>
            个人信息(暂为home)
          </el-dropdown-item>
        </router-link>
        <el-dropdown-item divided>
          <span style="display:block;" @click="logout">登出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-menu>
</template>

<style scoped>
.navbar {
  background-color: #35495e;
  color: white;
  height: 50px;
  line-height: 50px;
  margin: 0;
  padding: 0;
}

.avatar-container {
  float: right;
  display: flex;
  align-items: center;
  height: 100%;
  cursor: pointer;
  padding: 0 10px;
  margin: 0;
}

.avatar-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
}

.user-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid rgba(255,255,255,0.2);
}

.user-name {
  font-size: 14px;
}
</style>
