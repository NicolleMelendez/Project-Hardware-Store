/*!
    * Start Bootstrap - SB Admin v7.0.7 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2023 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
    // 
// Scripts
// 

window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
            document.body.classList.toggle('sb-sidenav-toggled');
         }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});

<div th:fragment="scripts" xmlns:th="http://www.w3.org/1999/xhtml">
  <!-- Bootstrap Bundle with Popper -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Script para el sidebar responsive -->
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const sidebarToggle = document.getElementById('sidebarToggle');
      const sidebar = document.getElementById('layoutSidenav_nav');
      const content = document.getElementById('layoutSidenav_content');

      // Toggle sidebar en móviles
      sidebarToggle.addEventListener('click', function(e) {
        e.preventDefault();
        sidebar.classList.toggle('show');
      });

      // Cerrar sidebar al hacer clic fuera en móviles
      document.addEventListener('click', function(event) {
        if (window.innerWidth < 768 &&
            !sidebar.contains(event.target) &&
            event.target !== sidebarToggle) {
          sidebar.classList.remove('show');
        }
      });

      // Manejar el redimensionamiento
      function handleResize() {
        if (window.innerWidth >= 768) {
          sidebar.classList.remove('show');
        }
      }

      window.addEventListener('resize', handleResize);
      handleResize(); // Ejecutar al cargar
    });
  </script>
</div>
