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

// src/main/resources/static/js/admin.js

// 1. Function to fetch and show the table
async function renderRoutes(container) {
    // ... (the code I gave you to build the table) ...
}

// 2. INCLUDE IT HERE: This function is called when the button is clicked
async function deleteRoute(id) {
    if (!confirm("Are you sure you want to delete this route?")) return;

    // Notice the /api/admin prefix matches your Controller
    const response = await fetch(`/api/admin/routes/${id}`, {
        method: 'DELETE',
        headers: { 
            'Authorization': `Bearer ${localStorage.getItem('token')}` 
        }
    });

    const message = await response.text(); 

    if (response.ok && message.includes("successfully")) {
        alert("Success: " + message);
        // Refresh the specific section
        showSection('routes'); 
    } else {
        // This displays your custom error from the Service (e.g., "Cannot delete...")
        alert("Error: " + message);
    }
}