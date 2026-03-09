/**
 * admin-route.js - FIXED: Premium UI + Unchanged Original Logic
 */

// 1. MAIN TABLE LOADER
async function loadRoutes(container) {
    try {
        const response = await fetch('/api/admin/routes', { headers: getHeaders() });
        const routes = await response.json();
        const totalKm = routes.reduce((sum, r) => sum + r.distance, 0);

        let html = `
            <style>
                .stat-card-clean { border: none; border-radius: 12px; background: #fff; box-shadow: 0 2px 10px rgba(0,0,0,0.05); transition: 0.3s; border-top: 4px solid #dee2e6; }
                .stat-card-clean:hover { transform: translateY(-5px); border-top-color: #0d6efd; }
                .city-tag { font-weight: 700; color: #2d3436; }
                .dist-pill { background: #f0f7ff; color: #0d6efd; padding: 4px 12px; border-radius: 50px; font-weight: 700; font-size: 0.8rem; border: 1px solid #cce3ff; }
                
                /* Premium Form Styles */
                .form-premium-label { font-size: 0.7rem; font-weight: 800; color: #64748b; letter-spacing: 0.5px; margin-bottom: 5px; display: block; }
                .form-control-premium { background: #f8fafd; border: 1px solid #e2e8f0; border-radius: 10px; padding: 12px; transition: all 0.2s; }
                .form-control-premium:focus { background: #fff; border-color: #0d6efd; box-shadow: 0 0 0 4px rgba(13, 110, 253, 0.1); outline: none; }
                .input-group-text-premium { background: #f8fafd; border: 1px solid #e2e8f0; border-right: none; border-radius: 10px 0 0 10px; color: #94a3b8; }
                .border-start-0 { border-top-left-radius: 0 !important; border-bottom-left-radius: 0 !important; }
            </style>

            <div class="d-flex justify-content-between align-items-end mb-4">
                <div>
                    <h3 class="fw-bold mb-1"><i class="fa-solid fa-map-location-dot text-primary me-2"></i>Network Logistics</h3>
                    <p class="text-muted mb-0">Manage global flight segments and coverage.</p>
                </div>
                <button class="btn btn-primary rounded-pill px-4 shadow-sm fw-bold" onclick="window.openRouteModal()">
                    <i class="fa-solid fa-plus me-2"></i>Add Route
                </button>
            </div>

            <div class="row g-4 mb-5 text-center">
                <div class="col-md-6">
                    <div class="card stat-card-clean p-4" style="border-top-color: #0d6efd;">
                        <small class="text-muted fw-bold">TOTAL SECTORS</small>
                        <h2 class="fw-bold mb-0">${routes.length}</h2>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card stat-card-clean p-4" style="border-top-color: #6610f2;">
                        <small class="text-muted fw-bold">NETWORK COVERAGE</small>
                        <h2 class="fw-bold mb-0">${totalKm.toLocaleString()} <small class="text-muted fs-6">KM</small></h2>
                    </div>
                </div>
            </div>

            <div class="table-responsive rounded-4 shadow-sm bg-white border">
                <table class="table align-middle mb-0">
                    <thead class="bg-light text-uppercase small fw-bold text-muted">
                        <tr>
                            <th class="ps-4 py-3">ID</th>
                            <th>Departure & Arrival</th>
                            <th>Distance</th>
                            <th>Duration</th>
                            <th class="text-end pe-4">Actions</th>
                        </tr>
                    </thead>
                    <tbody>`;
        
        routes.forEach(r => {
            const actualId = r.routeId || r.id;
            const routeData = JSON.stringify(r).replace(/'/g, "\\'");
            html += `
                <tr class="interactive-row">
                    <td class="ps-4 text-muted small">#${actualId}</td>
                    <td>
                        <div class="d-flex align-items-center gap-2">
                            <span class="city-tag">${r.source}</span>
                            <i class="fa-solid fa-arrow-right-long text-muted small"></i>
                            <span class="city-tag">${r.destination}</span>
                        </div>
                    </td>
                    <td><span class="dist-pill">${r.distance} KM</span></td>
                    <td><i class="fa-regular fa-clock me-1 text-primary"></i> ${r.estimatedDuration}</td>
                    <td class="text-end pe-4">
                        <button class="btn btn-sm btn-outline-primary rounded-pill px-3 me-1" onclick='window.editRoute(${routeData})'>
                            <i class="fa-solid fa-pen"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger rounded-pill px-3" onclick="window.deleteRoute(${actualId})">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>`;
        });
        container.innerHTML = html + `</tbody></table></div>`;
    } catch (e) { container.innerHTML = `<div class="alert alert-danger mx-4">Error loading the network map.</div>`; }
}

// 2. PREMIUM FORM UI (The "Aesthetic" part)
function getRouteFormHTML() {
    return `
        <form id="routeForm" class="p-2">
            <input type="hidden" id="routeId">
            <div class="row g-3">
                <div class="col-md-6">
                    <label class="form-premium-label text-uppercase">Source City</label>
                    <div class="input-group">
                        <span class="input-group-text input-group-text-premium"><i class="fa-solid fa-plane-departure"></i></span>
                        <input type="text" id="source" class="form-control form-control-premium border-start-0" placeholder="Mumbai" required>
                    </div>
                </div>
                <div class="col-md-6">
                    <label class="form-premium-label text-uppercase">Destination City</label>
                    <div class="input-group">
                        <span class="input-group-text input-group-text-premium"><i class="fa-solid fa-plane-arrival"></i></span>
                        <input type="text" id="destination" class="form-control form-control-premium border-start-0" placeholder="Delhi" required>
                    </div>
                </div>
                <div class="col-md-6">
                    <label class="form-premium-label text-uppercase">Distance (KM)</label>
                    <input type="number" id="distance" class="form-control form-control-premium" placeholder="1400" required>
                </div>
                <div class="col-md-6">
                    <label class="form-premium-label text-uppercase">Duration</label>
                    <input type="text" id="duration" class="form-control form-control-premium" placeholder="HH:mm:ss" required>
                </div>
            </div>
            <div class="mt-4 pt-3 border-top">
                <button type="submit" class="btn btn-primary w-100 py-3 fw-bold rounded-3 shadow">
                    <i class="fa-solid fa-cloud-arrow-up me-2"></i> Save Sector Details
                </button>
            </div>
        </form>`;
}

// 3. GLOBAL CRUD ACTIONS (Your original logic, untouched)
window.openRouteModal = function() {
    document.getElementById('modalTitle').innerText = "Register New Path";
    document.getElementById('modalBody').innerHTML = getRouteFormHTML();
    setupRouteFormListener(); // Attach the logic
    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

window.editRoute = function(route) {
    document.getElementById('modalTitle').innerText = "Update Path Vector";
    document.getElementById('modalBody').innerHTML = getRouteFormHTML();
    setupRouteFormListener(); // Attach the logic
    
    document.getElementById('routeId').value = route.routeId || route.id;
    document.getElementById('source').value = route.source;
    document.getElementById('destination').value = route.destination;
    document.getElementById('distance').value = route.distance;
    document.getElementById('duration').value = route.estimatedDuration;

    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

function setupRouteFormListener() {
    const form = document.getElementById('routeForm');
    form.onsubmit = async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('routeId').value;
        const routeData = {
            source: document.getElementById('source').value.trim(),
            destination: document.getElementById('destination').value.trim(),
            distance: parseInt(document.getElementById('distance').value) || 0,
            estimatedDuration: document.getElementById('duration').value
        };

        const isUpdate = (id !== "");
        const url = isUpdate ? `/api/admin/routes/${id}` : '/api/admin/routes';
        const method = isUpdate ? 'PUT' : 'POST';

        try {
            const response = await fetch(url, {
                method: method,
                headers: getHeaders(),
                body: JSON.stringify(routeData)
            });

            if (response.ok) {
                alert(isUpdate ? "Route updated!" : "Route added!");
                const modalInstance = bootstrap.Modal.getInstance(document.getElementById('adminModal'));
                if (modalInstance) modalInstance.hide();
                showSection('routes', null); 
            } else {
                alert(await response.text()); 
            }
        } catch (error) { alert("Network error occurred."); }
    };
}

window.deleteRoute = async function(id) {
    if (!confirm("Are you sure you want to remove this route?")) return;
    try {
        const response = await fetch(`/api/admin/routes/${id}`, { method: 'DELETE', headers: getHeaders() });
        const msg = await response.text();
        alert(msg);
        showSection('routes', null); 
    } catch (e) { alert("Could not delete route."); }
};