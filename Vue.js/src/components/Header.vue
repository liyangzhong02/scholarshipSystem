<template>
  <div style="height: 50px; line-height: 50px;border-bottom: 1px solid #cccccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">
      <el-icon><Cpu/></el-icon>
      奖学金管理系统
    </div>
    <div style="flex: 1"></div>
    <div style="width: 100px; padding-left: 30px; padding-top: 15px; font-weight: bold; color: blueviolet"><!--下拉菜单-->
      <el-dropdown size="large">
    <span class="el-dropdown-link" style="font-weight: bold">
      {{form.sname}}
      <el-icon class="is-loading"><Setting/></el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">
              <el-icon><Avatar/></el-icon>
              个人信息
            </el-dropdown-item>
            <el-dropdown-item divided @click="$router.push('/login')">
              <el-icon><CircleCloseFilled/></el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {Setting,Cpu,Avatar,CircleCloseFilled} from "@element-plus/icons";
export default {
  name: "Header",
  props: ['user'],
  components:{
    Setting,Cpu,Avatar,CircleCloseFilled
  },
  data() {
    return {
      form:{},
    }
  },
  created() {
    let str = sessionStorage.getItem("student")||"{}"
    this.form = JSON.parse(str);
  },
}
</script>

<style scoped>

</style>