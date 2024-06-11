<template>
  <div>
<!--    <div>-->
<!--      <el-input v-model="search" placeholder="请输入需要搜索的年份" style="width: 10%" clearable/>-->
<!--      <el-button type="primary" @click="load">搜索</el-button>-->
<!--    </div>-->
    <div class="home" style="display: flex; padding: 10px">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="year" label="适用年份"/>
        <el-table-column prop="grade" label="适用年级"/>
        <el-table-column prop="rule" label="规则详情"/>
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-popconfirm title="确定要删除吗?" @confirm="handleDelete(scope.row.id)">
              <template #reference>
                <el-button link type="primary" size="small">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="demo-pagination-block">
      <div class="demonstration"></div>
      <div style="margin: 10px; padding: 10px; margin: auto">
        <el-button type="primary" style="margin: 10px; margin: auto" @click="add">新增</el-button>
      </div>

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

    <el-dialog v-model="dialogVisible" title="新增规则" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="年份">
          <el-input-number v-model="form.year" :min="2024" :max="4000" @change="handleChange" value-on-clear="min"/>
        </el-form-item>
        <el-form-item label="年级">
          <el-radio v-model="form.grade" label="研一" size="large" @click="dialogvisible1 = true">研一</el-radio>
          <el-radio v-model="form.grade" label="研二" size="large" @click="dialogvisible2 = true">研二</el-radio>
          <el-radio v-model="form.grade" label="研三" size="large" @click="dialogvisible3 = true">研三</el-radio>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!--      研一 -->
<div>
    <el-dialog v-model="dialogvisible1" title="新增规则" width="30%">
      <el-form :model="form1" label-width="120px">
        <el-form-item label="年份">
          <el-input v-model="form.year" style="width: 80%; height: 80%" disabled />
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="form.grade" style="width: 80%; height: 80%" disabled />
        </el-form-item>
        <div style="text-align: center; font-size: 22px">学位成绩</div>
        <el-form-item label="学位成绩权重">
          <el-input-number v-model="form1.学位成绩.学位成绩权重" :precision="2" :step="0.01" :min="0" :max="10" />
        </el-form-item>
      </el-form>

      <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogvisible1 = false">取消</el-button>
                <el-button type="primary" @click="save1">确定</el-button>
              </span>
      </template>
    </el-dialog>
</div>

<!--    研二-->
    <div>
      <el-dialog v-model="dialogvisible2" title="新增规则" width="30%">
        <el-form :model="form2" label-width="120px">
          <el-form-item label="年份">
            <el-input v-model="form.year" style="width: 80%; height: 80%" disabled />
          </el-form-item>
          <el-form-item label="年级">
            <el-input v-model="form.grade" style="width: 80%; height: 80%" disabled />
          </el-form-item>
          <div style="text-align: center; font-size: 22px">学位成绩</div>
          <el-form-item label="学位成绩权重">
            <el-input-number v-model="form2.学位成绩.学位成绩权重" :precision="2" :step="0.01" :min="0" :max="10" />
          </el-form-item>

          <div style="text-align: center; font-size: 22px">思政表现</div>
          <div style="text-align: center; font-size: 14px">荣誉加分</div>
            <el-form-item label="优秀">
            <el-input-number v-model="form2.思政表现.荣誉加分.优秀" :min="0" :max="100"/>
            </el-form-item>
            <el-form-item label="良好">
            <el-input-number v-model="form2.思政表现.荣誉加分.良好" :min="0" :max="100"/>
            </el-form-item>
            <el-form-item label="合格">
            <el-input-number v-model="form2.思政表现.荣誉加分.合格" :min="0" :max="100"/>
            </el-form-item>
          <div style="text-align: center; font-size: 14px">导师组评价</div>
          <el-form-item label="优秀">
            <el-input-number v-model="form2.思政表现.导师组评价.优秀" :min="0" :max="100"/>
          </el-form-item>
          <el-form-item label="良好">
            <el-input-number v-model="form2.思政表现.导师组评价.良好" :min="0" :max="100"/>
          </el-form-item>
          <div style="text-align: center; font-size: 14px">通报</div>
          <el-form-item label="通报每次扣分">
            <el-input-number v-model="form2.思政表现.通报每次所扣分数" :min="0" :max="100"/>
          </el-form-item>

          <div style="text-align: center; font-size: 22px">科研能力</div>
          <div style="text-align: center; font-size: 14px">学术论文</div>
          <el-form-item label="无">
            <el-input-number v-model="form2.科研能力.学术论文.无" :min="0" :max="0" />
          </el-form-item>
          <el-form-item label="CCF推荐A类">
            <el-input-number v-model="form2.科研能力.学术论文.CCF推荐A类" :min="0" :max="100"/>
          </el-form-item>
          <el-form-item label="CCF推荐B类">
            <el-input-number v-model="form2.科研能力.学术论文.CCF推荐B类" :min="0" :max="100"/>
          </el-form-item>
          <div style="text-align: center; font-size: 14px">知识产权</div>
          <el-form-item label="无">
            <el-input-number v-model="form2.科研能力.知识产权.无" :min="0" :max="0" />
          </el-form-item>
          <el-form-item label="实质审查">
            <el-input-number v-model="form2.科研能力.知识产权.实质审查" :min="0" :max="100"/>
          </el-form-item>
          <el-form-item label="获得发明专利">
            <el-input-number v-model="form2.科研能力.知识产权.获得发明专利" :min="0" :max="100"/>
          </el-form-item>
        </el-form>

        <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogvisible2 = false">取消</el-button>
                <el-button type="primary" @click="save2">确定</el-button>
              </span>
        </template>
      </el-dialog>
    </div>
<!--研三-->

    <div>
      <el-dialog v-model="dialogvisible3" title="新增规则" width="30%">
        <el-form :model="form3" label-width="120px">
          <el-form-item label="年份">
            <el-input v-model="form.year" style="width: 80%; height: 80%" disabled />
          </el-form-item>
          <el-form-item label="年级">
            <el-input v-model="form.grade" style="width: 80%; height: 80%" disabled />
          </el-form-item>

          <div style="text-align: center; font-size: 22px">思政表现</div>
          <div style="text-align: center; font-size: 14px">荣誉加分</div>
          <el-form-item label="优秀">
            <el-input-number v-model="form3.思政表现.荣誉加分.优秀" :min="0" :max="100"/>
          </el-form-item>
          <el-form-item label="良好">
            <el-input-number v-model="form3.思政表现.荣誉加分.良好" :min="0" :max="100"/>
          </el-form-item>
          <el-form-item label="合格">
            <el-input-number v-model="form3.思政表现.荣誉加分.合格" :min="0" :max="100"/>
          </el-form-item>
          <div style="text-align: center; font-size: 14px">导师组评价</div>
          <el-form-item label="优秀">
            <el-input-number v-model="form3.思政表现.导师组评价.优秀" :min="0" :max="100"/>
          </el-form-item>
          <el-form-item label="良好">
            <el-input-number v-model="form3.思政表现.导师组评价.良好" :min="0" :max="100"/>
          </el-form-item>
          <div style="text-align: center; font-size: 14px">通报</div>
          <el-form-item label="通报每次扣分">
            <el-input-number v-model="form3.思政表现.通报每次所扣分数" :min="0" :max="100"/>
          </el-form-item>

          <div style="text-align: center; font-size: 22px">科研能力</div>
          <div style="text-align: center; font-size: 14px">学术论文</div>
          <el-form-item label="无">
            <el-input-number v-model="form3.科研能力.学术论文.无" :min="0" :max="0" />
          </el-form-item>
          <el-form-item label="CCF推荐A类">
            <el-input-number v-model="form3.科研能力.学术论文.CCF推荐A类" :min="0" :max="100"/>
          </el-form-item>
          <el-form-item label="CCF推荐B类">
            <el-input-number v-model="form3.科研能力.学术论文.CCF推荐B类" :min="0" :max="100"/>
          </el-form-item>
          <div style="text-align: center; font-size: 14px">知识产权</div>
          <el-form-item label="无">
            <el-input-number v-model="form3.科研能力.知识产权.无" :min="0" :max="0" />
          </el-form-item>
          <el-form-item label="实质审查">
            <el-input-number v-model="form3.科研能力.知识产权.实质审查" :min="0" :max="100"/>
          </el-form-item>
          <el-form-item label="获得发明专利">
            <el-input-number v-model="form3.科研能力.知识产权.获得发明专利" :min="0" :max="100"/>
          </el-form-item>
        </el-form>

        <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogvisible2 = false">取消</el-button>
                <el-button type="primary" @click="save3">确定</el-button>
              </span>
        </template>
      </el-dialog>
    </div>



  </div>
</template>


<script>


import request from "@/utils/request";
import { ref } from 'vue'
export default {
  name: 'rule',
  components: {},
  data() {
    return {
      form: {

      },
      form1: {
        "学位成绩":{}
      },
      form2: {
        "学位成绩":{},
        "思政表现":{
          "荣誉加分":{},
          "导师组评价":{},
        },
        "科研能力":{
          "学术论文":{},
          "知识产权":{}
        }
      },
      form3: {
        "思政表现":{
          "荣誉加分":{},
          "导师组评价":{},

        },
        "科研能力":{
          "学术论文":{},
          "知识产权":{}
        }
      },
      search: "",
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible : false,
      dialogvisible1 : false,
      dialogvisible2 : false,
      dialogvisible3 : false,
    }
  },
  created(){ //開始時調用，鈎子函數
    this.load()
    this.form.year = 2024
  },
  methods: {
    load() {
      request.get("/rule", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    save1(){

      console.log(this.form1);
      const requestBody = {
        year: this.form.year,
        grade: this.form.grade,
        rule: JSON.stringify(this.form1)
      };
      request.post("/rule", requestBody).then(res => {
        console.log(res);
        if (res.code === '0') {
          this.$message({type: "success", message: "增加成功"});
        } else {
          this.$message({type: "error", message: "增加失败"});
        }
      });
    },

    save2(){

      console.log(this.form2);
      const requestBody = {
        year: this.form.year,
        grade: this.form.grade,
        rule: JSON.stringify(this.form2)
      };
      request.post("/rule", requestBody).then(res => {
        console.log(res);
        if (res.code === '0') {
          this.$message({type: "success", message: "增加成功"});
        } else {
          this.$message({type: "error", message: "增加失败"});
        }
      });
    },

    save3(){

      console.log(this.form3);
      const requestBody = {
        year: this.form.year,
        grade: this.form.grade,
        rule: JSON.stringify(this.form3)
      };
      request.post("/rule", requestBody).then(res => {
        console.log(res);
        if (res.code === '0') {
          this.$message({type: "success", message: "增加成功"});
        } else {
          this.$message({type: "error", message: "增加失败"});
        }
      });
    },

    handleSizeChange(){
      this.load() //重载表格
    },
    handleCurrentChange(){
      this.load() //重载表格
    },
    add(){
      this.dialogVisible = true
      this.form = {}
      this.load()
    },
    handleDelete(id){
      console.log(id)
      //下面request的url中参数的id就会通过UserController中的@PathVariable传入”/{id}“占位符中
      request.delete("/rule/" + id).then(res => {
        if(res.code === '0'){
          this.$message({type: "success", message:"删除成功"})
        }
        else {
          this.$message({type: "error", message:"删除失败"})
        }
        this.load() //重载表格
      })
    },

  }
}

</script>
