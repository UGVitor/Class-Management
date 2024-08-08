<template>
    <img :src="link_img" :alt="description" width="150">    
</template>
<script>
    export default {
        name: 'Picture',
        data() {
            return {
                link_img: "https://i.pinimg.com/474x/3f/cd/17/3fcd1785622d5eea86a236d9ad795fba.jpg",
                description: 'Perna longa'
            }
        }
    }
</script>

