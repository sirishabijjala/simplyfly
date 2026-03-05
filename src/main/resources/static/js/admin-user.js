/**
 * admin-user.js
 * Handles User CRUD using RegisterRequest DTO
 */

// 1. Fetch and Display Table
async function loadUsers(container) {
    try {
        const response = await fetch('/api/admin/users', { headers: getHeaders() });
        const users = await response.json();

        let html = `
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="text-primary"><i class="fa-solid fa-users"></i> User Management</h3>
                <button class="btn btn-primary" onclick="openUserModal()">
                    <i class="fa-solid fa-user-plus"></i> Add New User
                </button>
            </div>
            <div class="table-responsive">
                <table class="table table-hover shadow-sm bg-white">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>`;

        users.forEach(u => {
            const userData = JSON.stringify(u).replace(/'/g, "\\'");
            html += `
                <tr>
                    <td>${u.id}</td>
                    <td>${u.name}</td>
                    <td>${u.email}</td>
                    <td>${u.phone || 'N/A'}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary me-2" onclick='editUser(${userData})'>
                            <i class="fa-solid fa-user-pen"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteUser(${u.id})">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>`;
        });

        html += `</tbody></table></div>`;
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger">Error: ${error.message}</div>`;
    }
}

function getUserFormHTML() {
    return `
        <form id="userForm">
            <input type="hidden" id="userId">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label font-weight-bold">Full Name</label>
                    <input type="text" id="userName" class="form-control" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label font-weight-bold">Gender</label>
                    <select id="userGender" class="form-select" required>
                        <option value="">Select</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label font-weight-bold">Email Address</label>
                    <input type="email" id="userEmail" class="form-control" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label font-weight-bold">Date of Birth</label>
                    <input type="date" id="userDOB" class="form-control" required>
                </div>
            </div>
            <div class="mb-3" id="passwordSection">
                <label class="form-label font-weight-bold">Password</label>
                <input type="password" id="userPassword" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label font-weight-bold">Phone Number</label>
                <input type="text" id="userPhone" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label font-weight-bold">Address</label>
                <textarea id="userAddress" class="form-control" rows="2"></textarea>
            </div>
            <hr>
            <button type="submit" class="btn btn-primary w-100 py-2">Save User Details</button>
        </form>`;
}

// 3. Open Modal for Add
window.openUserModal = function() {
    document.getElementById('modalTitle').innerText = "Add New User";
    const body = document.getElementById('modalBody');
    body.innerHTML = getUserFormHTML();
    setupUserFormListener();
    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

window.editUser = function(user) {
    document.getElementById('modalTitle').innerText = "Update User Info";
    const body = document.getElementById('modalBody');
    body.innerHTML = getUserFormHTML();
    setupUserFormListener();

    // Fill data
    document.getElementById('userId').value = user.id;
    document.getElementById('userName').value = user.name;
    document.getElementById('userEmail').value = user.email;
    document.getElementById('userPhone').value = user.phone || '';
    document.getElementById('userAddress').value = user.address || '';
    
    // NEW FIELDS
    document.getElementById('userGender').value = user.gender || '';
    document.getElementById('userDOB').value = user.dateOfBirth || '';

    // Make password optional for updates
    document.getElementById('userPassword').required = false;
    document.getElementById('userPassword').placeholder = "Leave blank to keep current";

    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

function setupUserFormListener() {
    const form = document.getElementById('userForm');
    form.onsubmit = async (e) => {
        e.preventDefault();
        const id = document.getElementById('userId').value;
        
        // Match these keys EXACTLY to your RegisterRequest DTO fields
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
            alert(isUpdate ? "User updated successfully!" : "User added successfully!");
            bootstrap.Modal.getInstance(document.getElementById('adminModal')).hide();
            showSection('users', null); 
        } else {
            const errorText = await response.text();
            alert("Error: " + errorText);
        }
    };
}

window.deleteUser = async function(id) {
    // Step 1: Confirm with Admin
    if (!confirm("Are you sure you want to delete this user? This action is permanent.")) return;

    try {
        const response = await fetch(`/api/admin/users/${id}`, {
            method: 'DELETE',
            headers: getHeaders() // From admin.js
        });

        if (response.ok) {
            // Case: 200 OK
            alert("✓ User deleted successfully.");
            showSection('users', null); // Refresh table
        } 
        else if (response.status === 409) {
            // Case: 409 Conflict (Has Bookings)
            const errorMsg = await response.text();
            alert("⚠️ " + errorMsg);
        } 
        else {
            // Case: 500, 404, etc.
            alert("✕ Error: Could not delete user. Server returned an error.");
        }
    } catch (error) {
        console.error("Delete error:", error);
        alert("Network error: Could not connect to server.");
    }
};