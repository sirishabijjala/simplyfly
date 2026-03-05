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
function showDashboard(container) {
    container.innerHTML = `
        <div class="mb-4">
            <h2 class="fw-bold">Welcome back, Admin!</h2>
            <p class="text-muted">Here is what's happening with SimplyFly today.</p>
        </div>

        <div class="row g-4">
            <div class="col-md-3">
                <div class="card border-0 shadow-sm bg-primary text-white h-100">
                    <div class="card-body">
                        <h6>Active Routes</h6>
                        <h2 id="stat-routes">--</h2>
                        <i class="fa-solid fa-route opacity-50 float-end" style="font-size: 2rem;"></i>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card border-0 shadow-sm bg-success text-white h-100">
                    <div class="card-body">
                        <h6>Total Users</h6>
                        <h2 id="stat-users">--</h2>
                        <i class="fa-solid fa-users opacity-50 float-end" style="font-size: 2rem;"></i>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card border-0 shadow-sm bg-info text-white h-100">
                    <div class="card-body">
                        <h6>Flight Owners</h6>
                        <h2 id="stat-owners">--</h2>
                        <i class="fa-solid fa-user-tie opacity-50 float-end" style="font-size: 2rem;"></i>
                    </div>
                </div>
            </div>
        <div class="mt-5 p-4 bg-white shadow-sm rounded">
            <h5><i class="fa-solid fa-chart-line me-2 text-primary"></i> Quick Actions</h5>
            <hr>
            <button class="btn btn-outline-primary me-2" onclick="showSection('routes', null)">Manage Routes</button>
            <button class="btn btn-outline-success" onclick="showSection('bookings', null)">Review Bookings</button>
        </div>
    `;
    
    fetchDashboardStats();
}

async function fetchDashboardStats() {
    try {
        const response = await fetch('/api/admin/dashboard-stats', { headers: getHeaders() });
        const data = await response.json();

        document.getElementById('stat-routes').innerText = data.totalRoutes;
        document.getElementById('stat-users').innerText = data.totalUsers;
        document.getElementById('stat-owners').innerText = data.totalOwners;
        document.getElementById('stat-revenue').innerText = "₹" + data.totalRevenue.toLocaleString();
    } catch (error) {
        console.error("Error loading stats", error);
    }
}
document.addEventListener('DOMContentLoaded', () => {
    // Initial load of stats
    fetchDashboardStats();
});

function logout() {
    localStorage.clear();
    window.location.href = '/auth/login.html';
}