<template>
    <form action="" @submit="enviarFormulario($event)">
        <div>
            <InputText/>
        </div>
        <div>
            <input type="text" v-model="name">
        </div>
        <div>
            <Submit/>
        </div>
    </form>
</template>
<script>
    import InputText from './form/InputText.vue'
    import Submit from './form/Submit.vue'
    export default {
        name: 'Form',
        components: {
            InputText,
            Submit
        },
        data(){
            return{
                name: 'YANKA',}
        },
        methods: {  
            enviarFormulario(e){
                e.preventDefault();
                const name = this.name;
                console.log("Formulario enviado!")
                console.log("O nome é : " + this.name)
            }

        }
    }
</script>
