<template>
  <div style="width: 100%; height: 100vh; background-color: white; overflow: hidden">
    <div style="width: 400px; margin: 200px auto">

      <div style="color: dodgerblue; padding: 30px; font-size: 30px; text-align: center">
        奖学金管理系统
      </div>
<!--      //分隔用-->
      <div style="margin: 20px"/>
      <el-form
          ref="form"
          :model="form"
          :label-position="'top'"
          label-width="100px"
          style="max-width: 460px"
          :rules="rules"
      >
        <el-form-item label="学号"  prop="sno">
          <el-input v-model="form.sno">
            <template #prefix>
              <el-icon>
                <UserFilled/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="密码" prop="spassword" >
          <el-input v-model="form.spassword" show-password>
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-button style="width: 100%" type="primary" @click="login">登 入</el-button>
      </el-form>

      <div style="margin: 300px"/>
      <div style="text-align: center; color: aliceblue">
        <el-divider >DouyouknowMiemie</el-divider>
        <el-text size="small" tag="b">Present</el-text>
      </div>
    </div>
  </div>
</template>
<script>
import {UserFilled, Lock} from "@element-plus/icons";
import request from "@/utils/request";

export default {
  name: "login",
  components: {
    UserFilled, Lock,
  },
  data() {
    return {
      form: {},
      rules :({
        sno: [
          { required: true, message: '请输入学号', trigger: 'blur' },
        ],
        spassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
      }),
    }
  },
  methods: {
    login() {
      this.$refs['form'].validate((valid) => {
        if(valid){
          //此refs对应着表单中的ref='' 本条作用是 当验证rules合法后才会执行下面的request.post语句
          request.post("/student/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登陆成功"
              })
              sessionStorage.setItem("student",JSON.stringify(res.data))  //res.data返回来一整个表格数据
              this.$router.push("/board")
            } else {
              {
                this.$message({
                  type: "error",
                  message: "用户名或密码错误"
                })
              }
            }
          })
        }
      })

    }
  }
}
</script>

<style scoped>

</style>