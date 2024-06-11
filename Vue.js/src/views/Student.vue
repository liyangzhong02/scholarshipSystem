<template>
  <div>
    <div style="margin: auto">
      <el-input v-model="search" placeholder="请输入需要搜索的学号" style="width: 10%; margin: auto" clearable/>
      <el-button type="primary" style="margin: auto" @click="load">搜索</el-button>
    </div>
    <div class="home" style="display: flex; padding: 10px">
      <el-table :data="tableData.filter((v) => v.sno !== 1 && v.deleted !== 1)" style="width: 100%" >
        <el-table-column prop="sno" label="学号" />
        <el-table-column prop="syear" label="年份" />
        <el-table-column prop="sname" label="姓名"/>
        <el-table-column prop="sgrade" label="年级"/>
        <el-table-column prop="stotal" label="综合成绩"/>
        <el-table-column prop="isprice" label="获得奖项"/>
        <el-table-column prop="sinfo" label="个人信息"/>
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定要删除吗?" @confirm="handleDelete(scope.row.id)">
              <template #reference>
                <el-button link type="primary" size="small">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>


    </div>
    <div style="margin: 10px; padding: 10px; margin: auto">
      <el-button type="primary" style="margin: 10px; margin: auto" @click="add">新增</el-button>
    </div>

    <div class="demo-pagination-block">
      <div class="demonstration"></div>
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="pageSize"
          :small="small"
          layout="total, sizes, prev, pager, next, jumper"
          :total=total
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>

    <el-dialog
        v-model="dialogVisible" title="" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="学号">
          <el-input v-model="form.sno" style="width: 80%; height: 80%" ></el-input>
        </el-form-item>
        <el-form-item label="年份">
          <el-input v-model="form.syear" style="width: 80%; height: 80%" ></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="form.sname" style="width: 80%; height: 80%"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="form.sgrade" style="width: 80%; height: 80%"></el-input>
        </el-form-item>
        <el-form-item label="综合成绩">
          <el-input v-model="form.stotal" style="width: 80%; height: 80%" disabled></el-input>
        </el-form-item>
        <el-form-item label="获得奖项">
          <div class="mb-2 flex items-center text-sm">
            <el-radio v-model="form.isprice" label="无" size="large">无</el-radio>
            <el-radio v-model="form.isprice" label="国家级奖学金" size="large">国家级奖学金</el-radio>
            </div>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>




<script>


import request from "@/utils/request";

export default {
  name: 'student',
  components: {},
  data() {
    return {
      dialogVisible: false,
      form: {},
      search: "",
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
    }
  },
  created(){
    this.load()
  },
  methods: {
    add(){
      this.dialogVisible = true
      this.form = {}
      this.load()
    },

    save() { //
      if (this.form.id) {//form里有id就执行更新
        const userInfo = sessionStorage.getItem('student');
        const operator = userInfo ? JSON.parse(userInfo).sname : null;
        console.log(operator); // 确保获取到了正确的用户名
        this.form.operator = operator;
        const currentTime = new Date();

        this.form.operator = operator;
        this.form.operatetime = currentTime;

        request.put("/student", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({type: "success", message: "更新成功"})
          } else {
            this.$message({type: "error", message: "更新失败"})
          }
        })
      } else {
        const userInfo = sessionStorage.getItem('student');
        const operator = userInfo ? JSON.parse(userInfo).sname : null;
        console.log(operator); // 确保获取到了正确的用户名
        this.form.operator = operator;
        const currentTime = new Date();

        this.form.operator = operator;
        this.form.operatetime = currentTime;

        request.post("/student", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({type: "success", message: "增加成功"})
          } else {
            this.$message({type: "error", message: "增加失败"})
          }
        })
      }
    },

    load(){
      request.get("/student",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res =>{console.log(res)
      this.tableData = res.data.records
      this.total = res.data.total
      })
    },

    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row)) //深拷贝
      this.dialogVisible = true
    },

    handleDelete(id){
      console.log(id)
      //下面request的url中参数的id就会通过UserController中的@PathVariable传入”/{id}“占位符中
      request.delete("/student/" + id).then(res => {
        if(res.code === '0'){
          this.$message({type: "success", message:"删除成功"})
        }
        else {
          this.$message({type: "error", message:"删除失败"})
        }
        this.load() //重载表格
      })
    },

    handleSizeChange(){
      this.load() //重载表格
    },
    handleCurrentChange(){
      this.load() //重载表格
    },

  }
}

</script>
