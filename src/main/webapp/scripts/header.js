function loadHeader() {
            setupDropdowns();

            const userInfo = document.getElementById('user-info');
            const nickname = document.getElementById('nickname');
            const sesionContainer = document.getElementById('sesion');
            const userData = JSON.parse(localStorage.getItem('userData'));

            if (userData && userData.nickname) {
                userInfo.style.display = 'flex';
                sesionContainer.style.display = 'none';
                nickname.textContent = userData.nickname;
            } else {
                sesionContainer.style.display = 'flex';
                userInfo.style.display = 'none';
            }

}

function setupDropdowns() {
    const allDetails = document.querySelectorAll('nav details');
    allDetails.forEach((detail) => {
        detail.addEventListener('toggle', () => {
            if (detail.open) {
                allDetails.forEach((other) => {
                    if (other !== detail) other.open = false;
                });
            }
        });
    });
}

function cerrarSesion() {
    localStorage.removeItem('userData');
    const contextPath = '<%= request.getContextPath() %>';

    window.location.href = contextPath + '/login';
}

document.addEventListener('DOMContentLoaded', loadHeader);