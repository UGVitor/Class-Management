<template>
  <div>
    <primeiro-componente/>
    <lifecycle/>
    <pessoa/>
  </div>
</template>

<script>
  import Lifecycle from './components/Lifecycle.vue'
  import PrimeiroComponente from './components/PrimeiroComponente.vue'
  import Pessoa from './components/Pessoa.vue'
  
  export default {
    name: 'App',
    components: {
      PrimeiroComponente,
      Lifecycle,
      Pessoa,
    }
  }
</script>

<style >
body{
  background-color: #333 ;
  color: #FFF ;
  }
  a{
    color: red;
  }

</style>