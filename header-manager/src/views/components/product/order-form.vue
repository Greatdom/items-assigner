<template>
  <div class="code-container">
    <div class="synchronous">
      <h4>websocket异步回调:</h4>
    </div>
    <el-form
        ref="loginFormRef"
        class="login-form"
        auto-complete="on"
        label-position="left"
    >
      <h3 class="title">下单操作</h3>
      <el-form-item prop="username">
        <span class="svg-container">
          productId
        </span>
        <el-input
            v-model="productId"
            name="username"
            type="text"
            auto-complete="on"
            placeholder="productId"
        />
      </el-form-item>
      <el-form-item prop="username">
        <span class="svg-container">
          skuId
        </span>
        <el-input
            v-model="skuId"
            name="username"
            type="text"
            auto-complete="on"
            placeholder="skuId"
        />
      </el-form-item>
      <el-form-item prop="username">
        <span class="svg-container">
          quantity
        </span>
        <el-input
            v-model="quantity"
            name="username"
            type="text"
            auto-complete="on"
            placeholder="quantity"
        />
      </el-form-item>
      <el-form-item prop="username">
        <span class="svg-container">
          couponIds
        </span>
        <el-input
            v-model="couponIds"
            name="username"
            type="text"
            auto-complete="on"
            placeholder="couponIds(暂只支持消费一个优惠券)"
        />
      </el-form-item>
      <el-form-item>
        <el-button
            type="primary"
            style="width:100%;"
            @click="phoneCodeGetter"
        >
          下单
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
.code-container{
  background-color: #999999;
}
.synchronous{
  background-color: #4caf50;
  height: 100px;
}
</style>

<script setup lang="ts">
import {ref} from "vue";
import type {orderParams} from "@/types/order.ts";
import {add} from "@/api/order/order.ts";
import {ElMessage} from "element-plus";



let productId = ref<string>('');
let skuId = ref<string>('');
let quantity = ref<string>('');
let couponIds = ref<string>('');

function phoneCodeGetter() {
   const orderData: orderParams = {
    productId: productId.value,
    skuId: skuId.value,
    quantity: Number(quantity.value),
    couponIds: couponIds.value ? [couponIds.value] : []
  };

  add(orderData)
      .then((response) => {
        ElMessage({
          message: "正在生成订单",
          type: 'success',
          duration: 5 * 1000
        })
      })

}

</script>