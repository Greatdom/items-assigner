<template>
  <h3>商品明细:</h3>
  <div style="padding: 10px">
    ProductProfileVO:{{ tableData.productProfileVO }}
  </div>
  <div style="padding: 10px">
    UserProfileVO:{{ tableData.userProfileVO }}
  </div>
  <div style="padding: 10px">
    Coupon:{{ tableData.coupon }}
  </div>
  <div style="padding: 10px">
    ProductSkuVO:{{ tableData.productSkuVO }}
  </div>
  <router-view>
    <order-form></order-form>
  </router-view>
</template>

<script lang="ts" setup>
import {onBeforeMount, reactive, ref} from "vue";
import {visit} from "@/api/product/product.ts";
import {useRoute} from "vue-router";
import OrderForm from "@/views/components/product/order-form.vue";

const route = useRoute()

let tableData = ref<any[]>([])

onBeforeMount(() => {
  load();
});
function load() {
  const id:any = route.params.id;

  if (!id || Array.isArray(id)) {
    ElMessage.error("无效的角色ID，请检查路径参数");
    console.error("ID 格式错误：", id);
    return;
  }

  visit(id)
      .then((response) => {
        tableData.value = response.data;
      })
      .catch((error) => {
        ElMessage.error("数据加载失败，请重试");
      });
}

</script>
