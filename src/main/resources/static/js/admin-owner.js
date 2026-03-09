async function loadOwners(container) {
    try {
        const response = await fetch('/api/admin/owners', { headers: getHeaders() });
        const owners = await response.json();
        const totalFlights = owners.reduce((sum, o) => sum + (o.flightCount || 0), 0);

        let html = `
            <style>
                /* Interaction Styles */
                .stat-card-owner { border: none; border-radius: 16px; background: #fff; box-shadow: 0 4px 15px rgba(0,0,0,0.05); transition: 0.3s; border-bottom: 4px solid #0d6efd; }
                .stat-card-owner:hover { transform: translateY(-5px); box-shadow: 0 8px 25px rgba(0,0,0,0.1); }

                .owner-table { border-collapse: separate; border-spacing: 0 10px; width: 100%; }
                .owner-row { background: #fff; transition: all 0.25s ease; cursor: pointer; }
                .owner-row:hover { 
                    background: #f0f7ff !important; 
                    transform: scale(1.01);
                    box-shadow: 0 5px 15px rgba(13, 110, 253, 0.1);
                }
                .owner-row td { border: none !important; padding: 20px 0; }
                .owner-row td:first-child { border-radius: 12px 0 0 12px; }
                .owner-row td:last-child { border-radius: 0 12px 12px 0; }

                .flight-card-interactive {
                    border: 1px solid #edf2f7;
                    border-radius: 15px;
                    transition: all 0.3s ease;
                    background: #fff;
                }
                .flight-card-interactive:hover {
                    border-color: #0d6efd;
                    box-shadow: 0 10px 20px rgba(0,0,0,0.05);
                    transform: translateY(-4px);
                }
            </style>

            <div class="d-flex justify-content-between align-items-end mb-5">
                <div>
                    <h2 class="fw-bold mb-1"><i class="fa-solid fa-building-shield text-primary me-2"></i>Flight Owners</h2>
                    <p class="text-muted small mb-0">Manage airline owners and monitor their operational fleet.</p>
                </div>
                <button class="btn btn-primary rounded-pill px-4 py-2 fw-bold shadow" onclick="window.openOwnerModal()">
                    <i class="fa-solid fa-plus-circle me-1"></i> Add Owner
                </button>
            </div>

            <div class="row g-4 mb-5">
                <div class="col-md-6">
                    <div class="card stat-card-owner p-4">
                        <small class="text-muted fw-bold text-uppercase small">Total Owners</small>
                        <h1 class="fw-bold mb-0 mt-1">${owners.length}</h1>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card stat-card-owner p-4" style="border-bottom-color: #8b5cf6;">
                        <small class="text-muted fw-bold text-uppercase small">Global Fleet</small>
                        <h1 class="fw-bold mb-0 mt-1" style="color: #8b5cf6;">${totalFlights} <small class="fs-5">Planes</small></h1>
                    </div>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table owner-table align-middle">
                    <thead>
                        <tr class="text-muted small text-uppercase fw-bold">
                            <th class="ps-4">Owner</th>
                            <th>Metrics</th>
                            <th class="text-end pe-4">Actions</th>
                        </tr>
                    </thead>
                    <tbody>`;

        owners.forEach(o => {
            const ownerData = JSON.stringify(o).replace(/'/g, "\\'");
            html += `
                <tr class="owner-row shadow-sm">
                    <td class="ps-4">
                        <div class="fw-bold text-dark">${o.name}</div>
                        <div class="text-muted small">${o.email}</div>
                    </td>
                    <td>
                        <span class="badge bg-primary bg-opacity-10 text-primary px-3 rounded-pill me-2">
                            <i class="fa-solid fa-plane me-1"></i> ${o.flightCount || 0} Flights
                        </span>
                        <span class="badge bg-dark bg-opacity-10 text-dark px-3 rounded-pill">
                            <i class="fa-solid fa-clock me-1"></i> ${o.scheduleCount || 0} Schedules
                        </span>
                    </td>
                    <td class="text-end pe-4">
                        <button class="btn btn-sm btn-primary rounded-pill px-3 me-2 fw-bold" onclick="window.viewOwnerInventory(${o.id}, '${o.name}')">
                             Inventory
                        </button>
                        <button class="btn btn-sm btn-outline-secondary border-0 me-1" onclick='window.editOwner(${ownerData})'>
                            <i class="fa-solid fa-pen"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger border-0" onclick="window.deleteOwner(${o.id})">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>`;
        });
        container.innerHTML = html + `</tbody></table></div>`;
    } catch (e) { container.innerHTML = `<div class="alert alert-danger">Error: ${e.message}</div>`; }
}

// 2. GLOBAL ACTIONS (Explicitly attached to window)
window.openOwnerModal = function() {
    document.getElementById('modalTitle').innerText = "Register Flight Owner";
    document.getElementById('modalBody').innerHTML = getOwnerFormHTML();
    setupOwnerFormListener();
    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

window.editOwner = function(owner) {
    document.getElementById('modalTitle').innerText = "Edit Owner Details";
    document.getElementById('modalBody').innerHTML = getOwnerFormHTML();
    setupOwnerFormListener();

    document.getElementById('ownerId').value = owner.id;
    document.getElementById('ownerName').value = owner.name;
    document.getElementById('ownerEmail').value = owner.email;
    document.getElementById('ownerPassword').required = false;

    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

window.deleteOwner = async function(id) {
    if (!confirm("Are you sure?")) return;
    try {
        const response = await fetch(`/api/admin/owners/${id}`, { method: 'DELETE', headers: getHeaders() });
        const msg = await response.text();
        alert(msg);
        showSection('owners', null);
    } catch (e) { alert("Delete failed"); }
};

window.viewOwnerInventory = async function(ownerId, ownerName) {
    const contentArea = document.getElementById('content-area');
    contentArea.innerHTML = `<div class="text-center p-5"><div class="spinner-border text-primary"></div></div>`;

    try {
        const response = await fetch(`/api/admin/owners/${ownerId}/inventory`, { headers: getHeaders() });
        const inventory = await response.json();

        let html = `
            <div class="d-flex justify-content-between align-items-center mb-4 pb-3 border-bottom">
                <h4 class="fw-bold text-dark"><i class="fa-solid fa-warehouse me-2 text-primary"></i>${ownerName} Fleet</h4>
                <button class="btn btn-outline-dark btn-sm rounded-pill px-3" onclick="showSection('owners', null)">
                    <i class="fa-solid fa-arrow-left me-1"></i> Back
                </button>
            </div>
            <div class="row g-4">`;

        inventory.forEach(item => {
            html += `
                <div class="col-md-6">
                    <div class="card flight-card-interactive h-100 shadow-sm border-0">
                        <div class="card-body p-4">
                            <div class="d-flex justify-content-between align-items-start mb-3">
                                <div>
                                    <h5 class="fw-bold text-primary mb-0">${item.flightNumber}</h5>
                                    <small class="text-muted fw-bold">${item.model}</small>
                                </div>
                                <span class="badge bg-light text-primary border border-primary-subtle">${item.route}</span>
                            </div>
                            <div class="p-3 bg-light rounded-3">
                                <h6 class="small fw-bold text-muted text-uppercase mb-2">Live Schedules</h6>
                                ${item.schedules.map(s => `
                                    <div class="d-flex justify-content-between align-items-center mb-2 last-child-mb-0">
                                        <span class="small font-monospace">${new Date(s.departure).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}</span>
                                        <span class="badge ${s.status === 'Open' ? 'bg-success' : 'bg-danger'} rounded-pill" style="font-size: 0.65rem;">${s.status}</span>
                                    </div>
                                `).join('')}
                            </div>
                        </div>
                    </div>
                </div>`;
        });
        contentArea.innerHTML = html + `</div>`;
    } catch (e) { contentArea.innerHTML = `<div class="alert alert-danger">Error loading inventory.</div>`; }
};

function getOwnerFormHTML() {
    return `
        <form id="ownerForm" class="p-2">
            <input type="hidden" id="ownerId">
            <div class="mb-3">
                <label class="form-label fw-bold small text-muted">OWNER NAME</label>
                <input type="text" id="ownerName" class="form-control form-control-lg bg-light border-0" placeholder="e.g. Indigo" required>
            </div>
            <div class="mb-3">
                <label class="form-label fw-bold small text-muted">EMAIL ADDRESS</label>
                <input type="email" id="ownerEmail" class="form-control form-control-lg bg-light border-0" required>
            </div>
            <div class="mb-3">
                <label class="form-label fw-bold small text-muted">PASSWORD</label>
                <input type="password" id="ownerPassword" class="form-control form-control-lg bg-light border-0" required>
            </div>
            <button type="submit" class="btn btn-primary w-100 py-3 fw-bold rounded-3 shadow mt-3">Save Owner Details</button>
        </form>`;
}

function setupOwnerFormListener() {
    const form = document.getElementById('ownerForm');
    form.onsubmit = async (e) => {
        e.preventDefault();
        const id = document.getElementById('ownerId').value;
        const ownerData = {
            name: document.getElementById('ownerName').value,
            email: document.getElementById('ownerEmail').value,
            password: document.getElementById('ownerPassword').value
        };

        const isUpdate = id !== "";
        const url = isUpdate ? `/api/admin/owners/${id}` : '/api/admin/owners';
        const method = isUpdate ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: getHeaders(),
            body: JSON.stringify(ownerData)
        });

        if (response.ok) {
            alert(isUpdate ? "Updated!" : "Registered!");
            const modal = bootstrap.Modal.getInstance(document.getElementById('adminModal'));
            if (modal) modal.hide();
            showSection('owners', null);
        } else {
            alert("Error: " + await response.text());
        }
    };
}