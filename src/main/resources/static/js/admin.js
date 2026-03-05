// admin.js - Global Logic

function getHeaders() {
    return {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
    };
}

async function showSection(section, event) {
    // 1. UI: Only update active class if an actual click event was passed
    if (event && event.currentTarget) {
        event.preventDefault();
        document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
        event.currentTarget.classList.add('active');
    }

    const contentArea = document.getElementById('content-area');
    contentArea.innerHTML = '<div class="text-center mt-5"><div class="spinner-border text-primary"></div></div>';

    // 2. Routing Logic
    try {
        if (section === 'routes') {
            await loadRoutes(contentArea);
        } else if (section === 'users') {
            await loadUsers(contentArea);
        } else if (section === 'owners') {
            await loadOwners(contentArea);
        } else if (section === 'bookings') {
            await loadBookings(contentArea);
        }
    } catch (err) {
        contentArea.innerHTML = `<div class="alert alert-danger">Error: ${err.message}</div>`;
    }
}

function logout() {
    localStorage.clear();
    window.location.href = '/auth/login.html';
}