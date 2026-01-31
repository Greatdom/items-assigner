<script setup lang="ts">

</script>

<template>
  <aside class="sidebar">
    <el-scrollbar>
      <el-menu
          :default-active="$route.path"
          mode="vertical"
          background-color="#34495e"
          text-color="#ecf0f1"
          active-text-color="#3498db"
          class="sidebar-menu"
      >
        <el-menu-item >
          <router-link to="/home">个人界面</router-link>
        </el-menu-item>
        <el-menu-item >
          <router-link to="/user/list">用户管理</router-link>
        </el-menu-item>
        <el-menu-item >
          <router-link to="/role/list">角色管理</router-link>
        </el-menu-item>
        <el-menu-item >
          <router-link to="/product/list">商品推送</router-link>
        </el-menu-item>
        <el-menu-item >
          <router-link to="/coupon/list">抢优惠券</router-link>
        </el-menu-item>
        <el-menu-item >
          <router-link to="/order/list">我的订单</router-link>
        </el-menu-item>
        <el-menu-item >
          <router-link to="/file/operate">大文件操作</router-link>
        </el-menu-item>
      </el-menu>
    </el-scrollbar>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 220px;
  background-color: #34495e;
  color: white;
  height: 100vh;
  box-shadow: 2px 0 5px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
}

.sidebar-menu {
  border-right: none;
  height: 100%;
  padding-top: 20px;
}

.el-menu-item {
  height: 50px;
  line-height: 50px;
  margin-bottom: 5px;
  border-radius: 0 25px 25px 0;
}
</style>