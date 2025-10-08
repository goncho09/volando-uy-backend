

const form = document.getElementById('login-form');
const nameInput = document.getElementById('name');
const passwordInput = document.getElementById('password');

form.addEventListener('submit', (e)=> {

    e.preventDefault();

    try {
        if (nameInput.value.trim() === '' || passwordInput.value.trim() === '') {
            alert('Por favor, completa todos los campos.');
            return;
        }

        const usuarios = window.usuarios.slice(1,-1).split(", ").map(s => s.trim());

        console.log(usuarios)
        const usuarioEncontrado = usuarios.find(usuario =>
                usuario === nameInput.value
            // && usuario.password === passwordInput.value
        );

        if (!usuarioEncontrado) {
            alert('Credenciales inválidas. Por favor, intenta de nuevo.');
            return;
        }

        localStorage.setItem(
            'userData',
            JSON.stringify({
                nickname: nameInput.value,
            })
        );

        window.location.href = window.appContext + '/home'
    } catch (error) {
        console.error('Error during login:', error);
        window.location.href = window.appContext + '/errorServer';
    }
});

