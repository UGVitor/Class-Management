<template>
    <input type="text" class="styled-input">
</template>

<script>
export default {
    name: 'InputText'
}
</script>

<style >
.styled-input {
    width: 20%;
    padding: 10px;
    margin: 8px 0;
    box-sizing: border-box;
    border: 2px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    transition: border-color 0.3s, box-shadow 0.3s;
}

.styled-input:focus {
    border-color: #66afe9;
    box-shadow: 0 0 8px rgba(102, 175, 233, 0.6);
    outline: none;
}
</style>
