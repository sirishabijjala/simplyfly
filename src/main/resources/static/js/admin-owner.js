/**
 * admin-owner.js
 * Handles Flight Owner management for the Admin.
 */

// 1. Fetch and Display Table
async function loadOwners(container) {
    try {
        const response = await fetch('/api/admin/owners', { headers: getHeaders() });
        const owners = await response.json();

        let html = `
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="text-primary"><i class="fa-solid fa-user-tie"></i> Flight Owners</h3>
                <button class="btn btn-primary" onclick="openOwnerModal()">
                    <i class="fa-solid fa-plus"></i> Add New Owner
                </button>
            </div>
            <div class="table-responsive">
                <table class="table table-hover shadow-sm bg-white">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Owner Name</th>
                            <th>Email Address</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>`;

        owners.forEach(o => {
            const ownerData = JSON.stringify(o).replace(/'/g, "\\'");
            html += `
                <tr>
                    <td>${o.id}</td>
                    <td>${o.name}</td>
                    <td>${o.email}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary me-2" onclick='editOwner(${ownerData})'>
                            <i class="fa-solid fa-pen"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteOwner(${o.id})">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>`;
        });

        html += `</tbody></table></div>`;
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger">Error loading owners: ${error.message}</div>`;
    }
}

// 2. Form Template (Simplified for Owners)
function getOwnerFormHTML() {
    return `
        <form id="ownerForm">
            <input type="hidden" id="ownerId">
            <div class="mb-3">
                <label class="form-label font-weight-bold">Company/Owner Name</label>
                <input type="text" id="ownerName" class="form-control" placeholder="e.g. Indigo Corp" required>
            </div>
            <div class="mb-3">
                <label class="form-label font-weight-bold">Email Address</label>
                <input type="email" id="ownerEmail" class="form-control" required>
            </div>
            <div class="mb-3" id="ownerPassSection">
                <label class="form-label font-weight-bold">Password</label>
                <input type="password" id="ownerPassword" class="form-control" required>
            </div>
            <hr>
            <button type="submit" class="btn btn-primary w-100 py-2">Save Flight Owner</button>
        </form>`;
}

// 3. Open Modal for Add
window.openOwnerModal = function() {
    document.getElementById('modalTitle').innerText = "Register New Flight Owner";
    const body = document.getElementById('modalBody');
    body.innerHTML = getOwnerFormHTML();
    setupOwnerFormListener();
    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

// 4. Open Modal for Edit
window.editOwner = function(owner) {
    document.getElementById('modalTitle').innerText = "Edit Flight Owner";
    const body = document.getElementById('modalBody');
    body.innerHTML = getOwnerFormHTML();
    setupOwnerFormListener();

    document.getElementById('ownerId').value = owner.id;
    document.getElementById('ownerName').value = owner.name;
    document.getElementById('ownerEmail').value = owner.email;
    
    // Password optional for edit
    document.getElementById('ownerPassword').required = false;
    document.getElementById('ownerPassword').placeholder = "Leave blank to keep current";

    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

// 5. Submit Logic (POST/PUT)
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
            alert(isUpdate ? "Owner updated!" : "Owner registered successfully!");
            bootstrap.Modal.getInstance(document.getElementById('adminModal')).hide();
            showSection('owners', null); 
        } else {
            const errorText = await response.text();
            alert("Error: " + errorText);
        }
    };
}

// 6. Delete Logic (Handles your custom dependency message)
window.deleteOwner = async function(id) {
    if (!confirm("Are you sure you want to remove this Flight Owner?")) return;

    try {
        const response = await fetch(`/api/admin/owners/${id}`, {
            method: 'DELETE',
            headers: getHeaders()
        });

        const msg = await response.text();

        if (response.ok) {
            alert("✓ " + msg);
            showSection('owners', null);
        } else {
            // This will catch your message: "Flight owner cannot be deleted because flights..."
            alert("⚠️ " + msg);
        }
    } catch (error) {
        alert("Connection error!");
    }
};