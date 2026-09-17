// Alterna a visibilidade dos campos de senha (botão "mostrar/ocultar").
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.toggle-password').forEach(function (button) {
        button.addEventListener('click', function () {
            var targetId = button.getAttribute('data-target');
            var input = document.getElementById(targetId);
            if (!input) {
                return;
            }
            var isHidden = input.getAttribute('type') === 'password';
            input.setAttribute('type', isHidden ? 'text' : 'password');
            button.textContent = isHidden ? 'Ocultar' : 'Mostrar';
        });
    });
});
