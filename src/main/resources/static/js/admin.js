// admin.js

function showSection(section) {
    const content = document.getElementById('content-area');
    
    if (section === 'routes') {
        renderRoutes(content);
    } else if (section === 'owners') {
        renderOwners(content);
    }
}

async function renderRoutes(container) {
    container.innerHTML = '<h3>Manage Routes</h3><button class="btn btn-primary mb-3">Add New Route</button>';
    
    const response = await fetch('/api/admin/routes', {
        headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
    });
    const routes = await response.json();

    let table = `
        <table class="table table-striped">
            <thead>
                <tr><th>ID</th><th>Source</th><th>Destination</th><th>Actions</th></tr>
            </thead>
            <tbody>
                ${routes.map(r => `
                    <tr>
                        <td>${r.id}</td>
                        <td>${r.source}</td>
                        <td>${r.destination}</td>
                        <td>
                            <button class="btn btn-danger btn-sm" onclick="deleteRoute(${r.id})">Delete</button>
                        </td>
                    </tr>
                `).join('')}
            </tbody>
        </table>`;
    container.innerHTML += table;
}


async function renderRoutes(container) {
}

async function deleteRoute(id) {
    if (!confirm("Are you sure you want to delete this route?")) return;

    const response = await fetch(`/api/admin/routes/${id}`, {
        method: 'DELETE',
        headers: { 
            'Authorization': `Bearer ${localStorage.getItem('token')}` 
        }
    });

    const message = await response.text(); 

    if (response.ok && message.includes("successfully")) {
        alert("Success: " + message);
        showSection('routes'); 
    } else {
        alert("Error: " + message);
    }
}
function loadSection(section) {
    const area = document.getElementById('content-area');
    
    document.querySelectorAll('.sidebar a').forEach(a => a.classList.remove('active'));
    event.target.classList.add('active');

    if (section === 'routes') {
        renderRoutes(area);
    } else if (section === 'users') {
        area.innerHTML = `<h3>Users List</h3><p>Fetching users...</p>`;
    }
   
}

function getHeaders() {
    const token = localStorage.getItem('token');
    return {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };
}

async function showSection(section, event) {
    if (event) {
        event.preventDefault();
        document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
        event.currentTarget.classList.add('active');
    }

    const contentArea = document.getElementById('content-area');
    contentArea.innerHTML = '<div class="text-center mt-5"><div class="spinner-border text-primary"></div></div>';

    try {
        switch(section) {
            case 'users': await loadUsers(contentArea); break;
            case 'owners': await loadOwners(contentArea); break;
            case 'routes': await loadRoutes(contentArea); break;
            case 'bookings': await loadBookings(contentArea); break;
        }
    } catch (error) {
        contentArea.innerHTML = `<div class="alert alert-danger">Failed to load data: ${error.message}</div>`;
    }
}

async function loadUsers(container) {
    const response = await fetch('/api/admin/users', { headers: getHeaders() });
    const users = await response.json();

    let html = `<div class="d-flex justify-content-between mb-3">
                    <h3>Manage Users</h3>
                </div>
                <table class="table table-hover shadow-sm">
                    <thead class="table-primary">
                        <tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Actions</th></tr>
                    </thead>
                    <tbody>`;
    users.forEach(u => {
        html += `<tr>
                    <td>${u.id}</td>
                    <td>${u.name}</td>
                    <td>${u.email}</td>
                    <td>${u.phone || 'N/A'}</td>
                    <td>
                        <button class="btn btn-danger btn-sm" onclick="deleteUser(${u.id})">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>`;
    });
    container.innerHTML = html + `</tbody></table>`;
}

async function deleteUser(id) {
    if (!confirm("Are you sure you want to delete this user?")) return;

    const response = await fetch(`/api/admin/users/${id}`, {
        method: 'DELETE',
        headers: getHeaders()
    });

    const msg = await response.text();
    if (response.ok) {
        alert(msg);
        showSection('users');
    } else {
        alert("Error: " + msg);
    }
}

async function loadRoutes(container) {
    const response = await fetch('/api/admin/routes', { headers: getHeaders() });
    const routes = await response.json();

    let html = `<h3>Flight Routes</h3>
                <table class="table table-hover shadow-sm mt-3">
                    <thead class="table-primary">
                        <tr><th>Source</th><th>Destination</th><th>Distance</th><th>Duration</th><th>Actions</th></tr>
                    </thead>
                    <tbody>`;
    routes.forEach(r => {
        html += `<tr>
                    <td>${r.source}</td>
                    <td>${r.destination}</td>
                    <td>${r.distance} km</td>
                    <td>${r.estimatedDuration}</td>
                    <td>
                        <button class="btn btn-danger btn-sm" onclick="deleteRoute(${r.id})">Delete</button>
                    </td>
                </tr>`;
    });
    container.innerHTML = html + `</tbody></table>`;
}

async function deleteRoute(id) {
    const response = await fetch(`/api/admin/routes/${id}`, {
        method: 'DELETE',
        headers: getHeaders()
    });
    const msg = await response.text();
    alert(msg);
    showSection('routes');
}