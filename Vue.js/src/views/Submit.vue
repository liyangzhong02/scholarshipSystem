<template>
  <el-form :model="form" label-width="120px" style="margin: auto">
    <el-form-item label="年级">
      <el-select v-model="form.sgrade" placeholder="选择你的年级">
        <el-option label="2023研一" value="研一" @click="dialogVisible1=true"/>
        <el-option label="2023研二" value="研二" @click="dialogVisible2=true"/>
        <el-option label="2023研三" value="研三" @click="dialogVisible3=true"/>
      </el-select>
    </el-form-item>
  </el-form>

  <el-card style="width: 10%;height: 80%; margin-inside: auto" shadow="always">
    <el-form ref="form" :model="person" label-width="80 px">
      <el-form-item label="学 号">
        <el-input v-model="person.sno" disabled></el-input>
      </el-form-item>
      <el-form-item label="姓 名">
        <el-input v-model="person.sname" disabled></el-input>
      </el-form-item>
      <el-form-item label="年 份">
        <el-input v-model="person.syear" disabled></el-input>
      </el-form-item>
      <el-form-item label="年 级">
        <el-input v-model="person.sgrade" disabled></el-input>
      </el-form-item>
    </el-form>
  </el-card>
  <div>
<!-- 研一-->
    <el-dialog v-model="dialogVisible1" title="研一申报页面" width="30%">
      <el-form :model="form1" label-width="120px">
        <el-form-item label="学位课总成绩">
          <el-input  style="width: 220px; height: 80%" v-model="form1.学位课成绩总成绩"/>
        </el-form-item>
        <el-form-item label="学位课数量">
          <el-input-number v-model="form1.学位课数量" :min="1" :max="100" @change="handleChange" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取消</el-button>
        <el-button type="primary" @click="save1">确定</el-button>
      </span>
      </template>
    </el-dialog>
<!--研二-->
    <el-dialog
        v-model="dialogVisible2" title="研二申报页面" width="30%">
      <el-form :model="form2" label-width="120px" :rules="rules">
        <el-form-item label="学位课总成绩">
          <el-input  style="width: 220px; height: 80%" v-model="form2.学位课成绩总成绩"/>
        </el-form-item>
        <el-form-item label="学位课数量">
          <el-input-number v-model="form2.学位课数量" :min="1" :max="100" @change="handleChange" />
        </el-form-item>
        <div>思政表现</div>
        <el-form-item label="荣誉加分">
          <el-select v-model="form2.思政表现.荣誉加分" placeholder="荣誉加分">
            <el-option label="优秀" value="优秀" />
            <el-option label="良好" value="良好" />
            <el-option label="合格" value="合格" />
          </el-select>
        </el-form-item>
        <el-form-item label="导师组评价">
          <el-select v-model="form2.思政表现.导师组评价" placeholder="导师组评价">
            <el-option label="优秀" value="优秀"/>
            <el-option label="良好" value="良好"/>
          </el-select>
        </el-form-item>
        <el-form-item label="通报次数">
          <el-input-number v-model="form2.思政表现.通报次数" :min="0" :max="100" @change="handleChange" />
        </el-form-item>
        <div>科研能力</div>
        <el-form-item label="学术论文">

          <el-button>增加</el-button>
        </el-form-item>
        <el-form-item label="知识产权">
          <el-button>增加</el-button>
        </el-form-item>



      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="primary" @click="save2">确定</el-button>
      </span>
      </template>
    </el-dialog>
<!--    研三-->
    <el-dialog
        v-model="dialogVisible3" title="研三申报页面" width="30%" >
      <el-form :model="form3" label-width="120px">
        <div>思政表现</div>
        <el-form-item label="荣誉加分">
          <el-select v-model="form3.思政表现.荣誉加分" placeholder="荣誉加分">
            <el-option label="优秀" value="优秀" />
            <el-option label="良好" value="良好" />
            <el-option label="合格" value="合格" />
          </el-select>
        </el-form-item>
        <el-form-item label="导师组评价">
          <el-select v-model="form3.思政表现.导师组评价" placeholder="导师组评价">
            <el-option label="优秀" value="优秀"/>
            <el-option label="良好" value="良好"/>
          </el-select>
        </el-form-item>
        <el-form-item label="通报次数">
          <el-input-number v-model="form3.思政表现.通报次数" :min="0" :max="100" @change="handleChange" />
        </el-form-item>
        <div>科研能力</div>
        <el-form-item label="学术论文">
          <el-select v-model="form3.科研能力.学术论文" placeholder="学术论文">
            <el-option label="CCF推荐A类" value="CCF推荐A类"/>
            <el-option label="CCF推荐B类" value="CCF推荐B类"/>
            <el-option label="无" value="无"/>
          </el-select>
        </el-form-item>
        <el-form-item label="知识产权">
          <el-select v-model="form3.科研能力.知识产权" placeholder="知识产权">
            <el-option label="获得发明专利" value="获得发明专利"/>
            <el-option label="实质审查" value="实质审查"/>
            <el-option label="无" value="无"/>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible3 = false">取消</el-button>
        <el-button type="primary" @click="save3">确定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import Person from "@/views/Person";
import request from "@/utils/request";
export default {
  name: "submit",
  components: {Person},
  data(){
    return{
      grade:'',
      form:{},
      form1:{

      },
      form2:{
        "思政表现":{},
        "科研能力":{}
      },
      form3:{
        "思政表现":{},
        "科研能力":{}
      },
      person:{},
      dialogVisible1:false,
      dialogVisible2:false,
      dialogVisible3:false,

      }
  },
  created(){
    let str = sessionStorage.getItem("student")||"{}"
    this.person = JSON.parse(str)
    this.choose()
  },
  methods:{
    save1(){
      this.form1["姓名"] = this.person.sname;
      this.form1["学号"] = this.person.sno;
      this.form1["年份"] = this.person.syear;
      request.post("/submit/g1",this.form1).then(res =>{
        console.log(this.form1)
        console.log(this.res)
        this.$message({type: "success", message:"申报成功"})
      })
      this.dialogVisible1 = false
    },
    save2(){
      this.form2["姓名"] = this.person.sname;
      this.form2["学号"] = this.person.sno;
      this.form2["年份"] = this.person.syear;
      request.post("/submit/g2",this.form2).then(res =>{
        console.log(this.form2)
        console.log(this.res)
        this.$message({type: "success", message:"申报成功"})
      })
      this.dialogVisible2 = false
    },
    save3(){
      this.form3["姓名"] = this.person.sname;
      this.form3["学号"] = this.person.sno;
      this.form3["年份"] = this.person.syear;
      request.post("/submit/g3",this.form3).then(res =>{
        console.log(this.form3)
        console.log(this.res)
        this.$message({type: "success", message:"申报成功"})
      })
      this.dialogVisible3 = false
    },
    choose() {
      if (this.person.sgrade === "研一") {
        this.dialogVisible1 = true;
      } else if (this.person.sgrade === "研二") {
        this.dialogVisible2 = true;
      } else {
        this.dialogVisible3 = true;
      }
    },
  }
}

</script>

<style scoped>

</style>