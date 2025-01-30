// checkProfile.js

function checkProfile() {
    const userProfile = localStorage.getItem('userProfile');
    const allowedProfiles = {
        'ADMIN': ['/index2.html'],
        'PROFESSOR': ['/config/PROFESSOR/index2.html'],
    };

    const currentPage = window.location.pathname; // Obtém o caminho absoluto da página atual

    if (userProfile && allowedProfiles[userProfile]) {
        if (!allowedProfiles[userProfile].includes(currentPage)) {
            // Redireciona para a página inicial do perfil
            window.location.href = allowedProfiles[userProfile][0];
        }
    } 
}

// Executa a verificação ao carregar a página
checkProfile();