<template>
  <!--无法通过path获取高亮 :default-active="path" -->
  <el-menu
      :default-active="path"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      style="width: 180px; min-height: 90vh"
      router >
<!--      router可以根据index来访问相对应的路由-->
<!--index 对应 index.js 里的 path:-->

      <el-menu-item index="board">
        <el-icon><Management /></el-icon>
        奖学金规则详情
      </el-menu-item>

    <el-menu-item index="submit">
      <el-icon><List /></el-icon>
      我要申报
    </el-menu-item>

    <el-sub-menu >
      <template #title>
      <el-icon><List /></el-icon>
      <span>奖学金公示</span>
      </template>
      <el-menu-item index="rank1">
        <el-icon><InfoFilled/></el-icon>
        研一
      </el-menu-item>
      <el-menu-item index="rank2">
        <el-icon><InfoFilled/></el-icon>
        研二
      </el-menu-item>
      <el-menu-item index="rank3">
        <el-icon><InfoFilled/></el-icon>
        研三
      </el-menu-item>
    </el-sub-menu>

    <el-sub-menu index="user" v-if="student.sno === 1" >
      <template #title>
        <el-icon><Tools /></el-icon>
        <span>系统管理</span>
      </template>
      <el-menu-item index="student">
        <el-icon><UserFilled/></el-icon>
        学生管理
      </el-menu-item>
      <el-menu-item index="rule">
        <el-icon><InfoFilled/></el-icon>
        规则一览
      </el-menu-item>
    </el-sub-menu>

  </el-menu>
</template>

<script>
import {Tools,Management,List,UserFilled,InfoFilled} from "@element-plus/icons";
export default {
  name: "Aside",
  components:{
    Tools,Management,List,UserFilled,InfoFilled
  },
  data(){
    return{
      student: {}
    }
  },
  created(){
    let studentStr = sessionStorage.getItem("student" )|| {}
    this.student = JSON.parse(studentStr) //转换成student对象
  }
}
</script>

<style scoped>

</style>