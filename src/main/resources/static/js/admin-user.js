
async function loadUsers(container) {
    try {
        const response = await fetch('/api/admin/users', { headers: getHeaders() });
        const users = await response.json();

        let html = `
            <style>
                /* Metrics Cards */
                .stat-card-user { 
                    border: none; border-radius: 16px; background: #fff; 
                    box-shadow: 0 4px 15px rgba(0,0,0,0.05); transition: 0.3s ease;
                    border-bottom: 4px solid #0d6efd;
                }
                .stat-card-user:hover { transform: translateY(-5px); box-shadow: 0 8px 25px rgba(0,0,0,0.1); }

                /* Interactive Table */
                .user-table { border-collapse: separate; border-spacing: 0 10px; width: 100%; }
                .user-row { background: #fff; transition: all 0.2s ease; cursor: pointer; }
                .user-row:hover { 
                    background: #f0f7ff !important; 
                    transform: scale(1.005);
                    box-shadow: 0 5px 15px rgba(13, 110, 253, 0.1);
                }
                .user-row td { border: none !important; padding: 18px 0; }
                .user-row td:first-child { border-radius: 12px 0 0 12px; }
                .user-row td:last-child { border-radius: 0 12px 12px 0; }

                .btn-action-user {
                    width: 35px; height: 35px; border-radius: 8px;
                    display: inline-flex; align-items: center; justify-content: center;
                    transition: 0.2s; border: 1px solid #e2e8f0; background: #fff;
                }
                .btn-action-user:hover { background: #f8fafc; transform: translateY(-2px); }
            </style>

            <div class="d-flex justify-content-between align-items-center mb-5">
                <div>
                    <h2 class="fw-bold mb-1"><i class="fa-solid fa-users-gear text-primary me-2"></i>User Directory</h2>
                    <p class="text-muted small mb-0">Manage customer accounts and profile information.</p>
                </div>
                <button class="btn btn-primary rounded-pill px-4 py-2 fw-bold shadow-sm" onclick="window.openUserModal()">
                    <i class="fa-solid fa-user-plus me-1"></i> Add New User
                </button>
            </div>

            <div class="row g-4 mb-5">
                <div class="col-md-6">
                    <div class="card stat-card-user p-4">
                        <small class="text-muted fw-bold text-uppercase">Total Customers</small>
                        <h2 class="fw-bold mb-0 mt-1">${users.length} <small class="fs-6 text-muted">Members</small></h2>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card stat-card-user p-4" style="border-bottom-color: #10b981;">
                        <small class="text-muted fw-bold text-uppercase">Verified Accounts</small>
                        <h2 class="fw-bold mb-0 mt-1" style="color: #10b981;">${users.length} <small class="fs-6 text-muted">Active</small></h2>
                    </div>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table user-table align-middle">
                    <thead>
                        <tr class="text-muted small text-uppercase fw-bold">
                            <th class="ps-4">User Profile</th>
                            <th>Contact Info</th>
                            <th>Demographics</th>
                            <th class="text-end pe-4">Actions</th>
                        </tr>
                    </thead>
                    <tbody>`;

        users.forEach(u => {
            const userData = JSON.stringify(u).replace(/'/g, "\\'");
            const genderBadge = u.gender === 'Male' ? 'bg-primary' : (u.gender === 'Female' ? 'bg-danger' : 'bg-secondary');
            
            html += `
                <tr class="user-row shadow-sm">
                    <td class="ps-4">
                        <div class="d-flex align-items-center">
                            <div class="rounded-circle bg-light d-flex align-items-center justify-content-center me-3" style="width: 40px; height: 40px;">
                                <i class="fa-solid fa-user text-muted"></i>
                            </div>
                            <div>
                                <div class="fw-bold text-dark">${u.name}</div>
                                <div class="text-muted small">ID: #${u.id}</div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="small"><i class="fa-regular fa-envelope me-1 text-primary"></i> ${u.email}</div>
                        <div class="small text-muted"><i class="fa-solid fa-phone me-1"></i> ${u.phone || 'N/A'}</div>
                    </td>
                    <td>
                        <span class="badge ${genderBadge} bg-opacity-10 text-${genderBadge.replace('bg-', '')} px-3 rounded-pill mb-1">
                            ${u.gender || 'Unknown'}
                        </span>
                        <div class="small text-muted"><i class="fa-solid fa-cake-candles me-1"></i> ${u.dateOfBirth || 'N/A'}</div>
                    </td>
                    <td class="text-end pe-4">
                        <button class="btn-action-user text-primary me-1" onclick='window.editUser(${userData})' title="Edit User">
                            <i class="fa-solid fa-user-pen"></i>
                        </button>
                        <button class="btn-action-user text-danger" onclick="window.deleteUser(${u.id})" title="Delete User">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>`;
        });

        html += `</tbody></table></div>`;
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger mx-4">Error loading users: ${error.message}</div>`;
    }
}

// ── GLOBAL MODAL HANDLERS ──

window.openUserModal = function() {
    document.getElementById('modalTitle').innerText = "Create Customer Account";
    document.getElementById('modalBody').innerHTML = getUserFormHTML();
    setupUserFormListener();
    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

window.editUser = function(user) {
    document.getElementById('modalTitle').innerText = "Update Profile: " + user.name;
    document.getElementById('modalBody').innerHTML = getUserFormHTML();
    setupUserFormListener();

    document.getElementById('userId').value = user.id;
    document.getElementById('userName').value = user.name;
    document.getElementById('userEmail').value = user.email;
    document.getElementById('userPhone').value = user.phone || '';
    document.getElementById('userAddress').value = user.address || '';
    document.getElementById('userGender').value = user.gender || '';
    document.getElementById('userDOB').value = user.dateOfBirth || '';

    // Password logic
    const passInput = document.getElementById('userPassword');
    passInput.required = false;
    passInput.placeholder = "Enter new password to reset";

    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

window.deleteUser = async function(id) {
    if (!confirm("Are you sure? This will permanently remove the user and their access.")) return;

    try {
        const response = await fetch(`/api/admin/users/${id}`, { method: 'DELETE', headers: getHeaders() });
        const msg = await response.text();

        if (response.ok) {
            alert("✓ Success: " + msg);
            showSection('users', null);
        } else if (response.status === 409) {
            alert(msg);
        } else {
            alert("✕ Error: " + msg);
        }
    } catch (e) {
        alert("Network Error: Could not reach the server.");
    }
};

function getUserFormHTML() {
    return `
        <form id="userForm" class="p-2">
            <input type="hidden" id="userId">
            <div class="row g-3">
                <div class="col-md-8">
                    <label class="form-label fw-bold small text-muted">FULL NAME</label>
                    <input type="text" id="userName" class="form-control bg-light border-0" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label fw-bold small text-muted">GENDER</label>
                    <select id="userGender" class="form-select bg-light border-0" required>
                        <option value="">Select</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <label class="form-label fw-bold small text-muted">EMAIL ADDRESS</label>
                    <input type="email" id="userEmail" class="form-control bg-light border-0" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label fw-bold small text-muted">DATE OF BIRTH</label>
                    <input type="date" id="userDOB" class="form-control bg-light border-0" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label fw-bold small text-muted">PHONE NUMBER</label>
                    <input type="text" id="userPhone" class="form-control bg-light border-0" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label fw-bold small text-muted">PASSWORD</label>
                    <input type="password" id="userPassword" class="form-control bg-light border-0" required>
                </div>
                <div class="col-12">
                    <label class="form-label fw-bold small text-muted">RESIDENTIAL ADDRESS</label>
                    <textarea id="userAddress" class="form-control bg-light border-0" rows="2"></textarea>
                </div>
            </div>
            <button type="submit" class="btn btn-primary w-100 py-3 fw-bold rounded-3 shadow mt-4">Save Profile Changes</button>
        </form>`;
}

function setupUserFormListener() {
    const form = document.getElementById('userForm');
    form.onsubmit = async (e) => {
        e.preventDefault();
        const id = document.getElementById('userId').value;
        const userData = {
            name: document.getElementById('userName').value,
            email: document.getElementById('userEmail').value,
            password: document.getElementById('userPassword').value,
            phone: document.getElementById('userPhone').value,
            address: document.getElementById('userAddress').value,
            gender: document.getElementById('userGender').value,
            dateOfBirth: document.getElementById('userDOB').value
        };

        const isUpdate = id !== "";
        const url = isUpdate ? `/api/admin/users/${id}` : '/api/admin/users';
        const method = isUpdate ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: getHeaders(),
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            alert(isUpdate ? "User Updated!" : "User Registered!");
            const modalEl = document.getElementById('adminModal');
            bootstrap.Modal.getInstance(modalEl).hide();
            showSection('users', null); 
        } else {
            alert("Error: " + await response.text());
        }
    };
}