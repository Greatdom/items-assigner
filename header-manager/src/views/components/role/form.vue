<template>
  <h3>角色明细:</h3>
  <div style="padding: 10px">
    {{ tableData }}

  </div>
</template>

<script lang="ts" setup>
import {onBeforeMount, reactive, ref} from "vue";
import {detail} from "@/api/user/role.ts";
import {useRoute} from "vue-router";

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

  detail(id)
      .then((response) => {
        tableData.value = response.data;
      })
      .catch((error) => {
        ElMessage.error("数据加载失败，请重试");
      });
}

</script>
