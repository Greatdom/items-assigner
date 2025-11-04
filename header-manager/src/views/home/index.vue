<script setup lang="ts">
import {useUserStore} from "@/store/modules/user.ts";

const userStore = useUserStore()
const currentUser = userStore.currentUser
</script>

<template>
  <div>
    <span>NICKNAME :{{ currentUser.nickName }}</span>
    <br><br/>
    <span>USERNAME : {{ currentUser.username}}</span>
    <br><br/>
    <span>PERMISSION : {{ currentUser.permissionValueList }}</span>
    <br><br/>
    <span>ROLES : {{ currentUser.roles }}</span>
  </div>
</template>

<style scoped>

</style>