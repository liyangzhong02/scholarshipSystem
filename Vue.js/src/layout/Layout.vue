<template>
  <div>
    <Header/>
    <nav style="display: flex">
      <Aside/>
      <router-view style="flex : 1;"/>
    </nav>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";

export default {
  name: "Layout",
  components: {
    Aside, Header,

  }
}
</script>

<style scoped>

</style>