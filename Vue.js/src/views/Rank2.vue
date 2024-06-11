<template>
  <div>
    <div style="margin: auto">
      <el-input v-model="search" placeholder="请输入需要搜索的学号" style="width: 10%; margin: auto" clearable/>
      <el-button type="primary" style="margin: auto" @click="load">搜索</el-button>
    </div>
    <div class="home" style="display: flex; padding: 10px">
      <el-table :data="tableData.filter((v) => v.sno !== '1' && v.deleted !== 1)" style="width: 100%">
        <el-table-column prop="sno"  label="学号"/>
        <el-table-column prop="sname" label="姓名"/>
        <el-table-column prop="stotal" label="综合成绩" sortable/>
        <el-table-column prop="isprice" label="获得奖项"/>
        <el-table-column prop="syear" label="年份" sortable/>
      </el-table>
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
  </div>
</template>



<script>
import request from "@/utils/request";

export default {
  name: 'rank',
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
  created(){ //開始時調用，鈎子函數
    this.load()
  },
  methods: {

    load() {
      request.get("/student", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res);
        let filteredData = [];
        for (let i = 0; i < res.data.records.length; i++) {
          let grade = res.data.records[i].sgrade;
          if (grade === "研二") {
            filteredData.push(res.data.records[i]);
          }
        }
        this.tableData = filteredData;
        this.total = res.data.total;
      });
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
