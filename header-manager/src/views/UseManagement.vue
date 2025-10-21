<template>
  <div class="user-management">
    <h1>用户管理</h1>

    <div class="actions">
      <button v-permission="'user:add'" @click="addUser">添加用户</button>
      <button v-permission="'user:edit'" @click="editUser">编辑用户</button>
      <button v-permission="'user:delete'" @click="deleteUser">删除用户</button>
    </div>

    <div v-permission="'user:view'">
      <p>用户列表内容...</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore();

const addUser = () => {
  if (authStore.hasPermission('user:add')) {
    alert('添加用户');
  }
};

const editUser = () => {
  if (authStore.hasPermission('user:edit')) {
    alert('编辑用户');
  }
};

const deleteUser = () => {
  if (authStore.hasPermission('user:delete')) {
    alert('删除用户');
  }
};
</script>