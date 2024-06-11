<template>
<div style="width: 400px;height: 200px;display: flex">
  <el-card style="width: 30%; margin: 20px; margin: auto" shadow="always">
    <el-form ref="form" :model="form" label-width="80 px">
      <el-form-item label="学 号">
        <el-input v-model="form.sno" disabled></el-input>
      </el-form-item>
      <el-form-item label="姓 名">
        <el-input v-model="form.sname" disabled></el-input>
      </el-form-item>
      <el-form-item label="年 份">
        <el-input v-model="form.syear" disabled></el-input>
      </el-form-item>
      <el-form-item label="年 级  ">
        <el-input v-model="form.sgrade" disabled></el-input>
      </el-form-item>
      <el-form-item label="密 码  ">
        <el-input v-model="form.spassword" ></el-input>
      </el-form-item>

    </el-form>
    <div style="text-align: center">
      <el-button type="primary" @click="update">保存</el-button>
    </div>
  </el-card>
</div>
</template>

<script>
import request from "@/utils/request"
export default {
  name: "Person",
  data(){
    return{
      form:{}
    }
  },
  created(){
    let str = sessionStorage.getItem("student")||"{}"
    this.form = JSON.parse(str)
  },
  methods:{
    update(){
      request.put("/student",this.form).then(res => {
        console.log(res)
        if(res.code === "0"){
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("student", JSON.stringify(this.form)) //JSON.stringify() 方法用于将 JavaScript 值转换为 JSON 字符串
        }else {
          this.$message({
            type: "erroe",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>