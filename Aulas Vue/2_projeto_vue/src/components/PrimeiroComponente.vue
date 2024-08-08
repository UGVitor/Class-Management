<template>
    <div>
        <h1>Olá Vue!</h1>
        <p>Meu nome é <b>{{ nome }}</b> e trabalha como <b>{{ profissão }}</b></p>
    </div>
    
</template>

<script>
    export default {
        name: 'PrimeiroComponente',
        data() {
            return {
                nome: "Vitu",
                profissão: "Garoto de programa"
            }
        }
    }
</script>
