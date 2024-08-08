<template>
    <h1>Meu nome é: {{ nome }}</h1>
</template>

<script>
    export default {
        name: "Lifecycle",
        data() {
            return {
                nome: 'Ainda não sei'
            }
          
        },
        created(){
            setTimeout(() => {
                this.nome = 'Kauã'
            }, 1000)

              this.lifeCycle()
        },
        mounted() {
            setTimeout(() => {
                this.nome = 'Gabriel'
            }, 2000)

              this.lifeCycle()
        },
        methods:{
            lifeCycle(){
                console.log("Executou");
                
        }
        }
        
    }
</script>
