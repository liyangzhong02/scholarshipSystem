<template>
  <div class="demo-image__lazy" style="height: 900px">
    <el-image :src="require('@/assets/1.jpg')" lazy/>
    <el-image :src="require('@/assets/2.jpg')" lazy/>
    <el-image :src="require('@/assets/3.jpg')" lazy/>
    <el-image :src="require('@/assets/4.jpg')" lazy/>
    <el-image :src="require('@/assets/5.jpg')" lazy/>
    <el-image :src="require('@/assets/6.jpg')" lazy/>
    <el-image :src="require('@/assets/7.jpg')" lazy/>
  </div>
</template>

<script>
export default {
  name: "Board",
  data(){
    return {
      urls: [
          '@/assets/1.jpg',
          '@/assets/2.jpg',
          '@/assets/3.jpg',
          '@/assets/4.jpg',
          '@/assets/5.jpg',
          '@/assets/6.jpg',
          '@/assets/7.jpg',
      ]
    }
  }
}
</script>
<style scoped>
.demo-image__lazy {
  height: 400px;
  overflow-y: auto;
}
.demo-image__lazy .el-image {
  display: block;
  min-height: 200px;
  margin-bottom: 10px;
}
.demo-image__lazy .el-image:last-child {
  margin-bottom: 0;
}
</style>