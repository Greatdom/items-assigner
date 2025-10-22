<template>
  <div class="login-container">
    <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
        auto-complete="on"
        label-position="left"
    >
      <h3 class="title">计划经济分配机</h3>
      <el-form-item prop="username">
        <span class="svg-container">
          用户名
        </span>
        <el-input
            v-model="loginForm.username"
            name="username"
            type="text"
            auto-complete="on"
            placeholder="username"
        />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          密码
        </span>
        <el-input
            :type="pwdType"
            v-model="loginForm.password"
            name="password"
            auto-complete="on"
            placeholder="password"
            @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          显示密码
        </span>
      </el-form-item>
      <el-form-item>
        <el-button
            :loading="loading"
            type="primary"
            style="width:100%;"
            @click="handleLogin"
        >
          登录
        </el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref,  } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {isValidPassword, isValidUsername} from '@/utils/validate.ts'
import type {UserInfo} from "@/types/user.ts";
import {useUserStore} from "@/store/modules/user.ts";
import {ElMessage} from "element-plus";
const userStore = useUserStore()

//TODO 可以监听路由实现登录后跳转到登录前的页面

// 密码显示
const pwdType = ref<string>('password')
const showPwd = () => {
  pwdType.value = pwdType.value === 'password' ? '' : 'password'
}

// 响应式数据
const loginForm = ref<UserInfo>({
  username: '',
  password: ''
})


// 路由和状态管理
const router = useRouter()

// 表单验证规则
const loginRules = ref({
  username: [
    {
      required: true,
      trigger: 'blur',
      validator: (rule:any, value: string, callback: any) => {
        if (!isValidUsername(value)) {
          callback(new Error('请输入正确的用户名'))
        } else {
          callback()
        }
      }
    }
  ],
  password: [
    {
      required: true,
      trigger: 'blur',
      validator: (rule:any, value:string, callback:any) => {
        if (!isValidPassword(value)) {
          callback(new Error('密码不能小于6位'))
        } else {
          callback()
        }
      }
    }
  ]
})



// 表单引用
const loading = ref<boolean>(false)
const loginFormRef = ref<InstanceType<typeof ElForm> | null>(null)
const handleLogin = () => {

  loginFormRef.value.validate(async (valid:any) => {
    if (valid) {
      loading.value = true

      try {

        await userStore.Login(loginForm.value).then(response => {
          ElMessage({
            message: response.msg,
            type: 'success',
            duration: 5 * 1000
          })
        })

        await router.push({path: '/'})
      } catch (error) {
        console.error('登录失败:', error)
      } finally {
        loading.value = false
      }
    } else {
      console.log('error submit!!')
      return false
    }
  })
}
</script>

