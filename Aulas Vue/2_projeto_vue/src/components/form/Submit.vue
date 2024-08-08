<template>
    <input type="submit" value="Enviar" class="styled-submit">
</template>

<script>
export default {
    name: 'Submit'
}
</script>

<style scoped>
.styled-submit {
    background-color: #66afe9; /* Cor de fundo */
    border: none; /* Remove a borda padrão */
    color: white; /* Cor do texto */
    padding: 10px 20px; /* Espaçamento interno */
    text-align: center; /* Alinhamento do texto */
    text-decoration: none; /* Remove sublinhado */
    display: inline-block; /* Permite definir altura e largura */
    font-size: 16px; /* Tamanho da fonte */
    margin: 4px 2px; /* Margem ao redor do botão */
    transition-duration: 0.4s; /* Duração da transição */
    cursor: pointer; /* Cursor de mãozinha */
    border-radius: 12px; /* Borda arredondada */
}

.styled-submit:hover {
    background-color: white; /* Cor de fundo ao passar o mouse */
    color: #66afe9; /* Cor do texto ao passar o mouse */
    border: 2px solid #66afe9; /* Adiciona uma borda ao passar o mouse */
}
</style>
