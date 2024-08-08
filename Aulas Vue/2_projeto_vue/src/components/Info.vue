<template>
    <div>
        <p v-if="esta_trabalhando" id="estaTrabalhando">Estou trabalhando no momento.</p>
        <p v-else id="naoTrabalha">Estou em busca de novas oportunidades!</p>
        
        <p>Utilizo essas tecnologias para Back-End: </p>
        <ul >
            <li v-for="(tech, index) in backend_tech" :key="index">{{ tech }}</li>
        </ul>
        <p>Utilizo as seguintes Tecnologia para Front-End</p>
        <ul>
            <li v-for="tech in frontend_tech" :key="tech.id">
                {{ tech.language }}
            </li>
        </ul>
        <div>
            <button @click="showEmail">Mostrar e-mail</button>
        </div>
        <p v-show="mostrar_email">Mande uma mensagem para: {{ email }}</p>
        <p>Para acessar meu portfólio <a v-bind:href="meu_link" target="_blank">basta clicar aqui</a></p>
        <Picture/>
    </div>
</template>
<script>
    import Picture from './Picture.vue'
    export default {
        name: "Info",
        components: {
            Picture
        },
        data() {
            return {
                esta_trabalhando: false,
                mostrar_email: false,
                email: "kaua32475@gmail.com",
                meu_link: "https://www.google.com.br/?gws_rd=ssl",
                textoBotao : 'Mostrar e-mail',
                backend_tech: ['JavaScript','PHP','Python','Assembly', 'Java'],
                frontend_tech: [
                    {id: 1,language: 'HTML'},
                    {id: 2,language: 'React'},
                    {id: 3,language: 'CSS'},
                    {id: 4,language: 'Vue'}
                ]
            }
        },
        methods: {
            showEmail() {
                this.mostrar_email = !this.mostrar_email;
                if(!this.mostrar_email) { 
                    tris.textoBotao = 'Mostrar e-mail';
                     
            } else{
                this.textoBotao = 'Esconde e-mail'; 
            }
        }
    }
    }
</script>

<style>
    #estaTrabalhando {
        color: green;
        font-weight: bold;
    }
    #naoTrabalha {
        color: red;
        font-weight: bold;
    }
</style>