<template>
  <div class="code-container">
    <el-form
        ref="loginFormRef"
        class="login-form"
        auto-complete="on"
        label-position="left"
    >
      <h3 class="title">验证码获取</h3>
      <el-form-item prop="username">
        <span class="svg-container">
          手机号
        </span>
        <el-input
            v-model="phone"
            name="username"
            type="text"
            auto-complete="on"
            placeholder="username"
        />
      </el-form-item>
      <el-form-item>
        <el-button
            type="primary"
            style="width:100%;"
            @click="phoneCodeGetter"
        >
          获取手机验证码
        </el-button>
      </el-form-item>
      <br>
      <el-form-item prop="username">
        <span class="svg-container">
          邮箱
        </span>
        <el-input
            v-model="email"
            name="username"
            type="text"
            auto-complete="on"
            placeholder="username"
        />
      </el-form-item>
      <el-form-item>
        <el-button
            type="primary"
            style="width:100%;"
            @click="emailCodeGetter"
        >
          获取邮箱验证码
        </el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<style scoped>
.code-container{
  background-color: #999999;
}
</style>

<script setup lang="ts">
import {ref} from "vue";
import {ElMessage} from "element-plus";
import type {SearchDTO} from "@/types/user.ts";
import {list} from "@/api/user/user.ts";
import {emailCode, phoneCode} from "@/api/user/auth.ts";

let phone = ref<String>('');
let email = ref<String>('');

function phoneCodeGetter() {

  phoneCode(phone.value).then(
      (response) => {
        alert("已经发送手机验证码")
      }
  )
}

function emailCodeGetter() {

  emailCode(email.value).then(
      (response) => {
        alert("已经发送邮箱验证码")
      }
  )
}

</script>