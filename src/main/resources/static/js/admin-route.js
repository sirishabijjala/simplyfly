/**
 * admin-route.js
 * Handles all Route-related CRUD operations.
 */

// 1. MAIN FUNCTION: Load the table into the dashboard
async function loadRoutes(container) {
    try {
        const response = await fetch('/api/admin/routes', { headers: getHeaders() });
        const routes = await response.json();

        let html = `
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="text-primary"><i class="fa-solid fa-route"></i> Flight Routes</h3>
                <button class="btn btn-primary" onclick="openRouteModal()">
                    <i class="fa-solid fa-plus"></i> Add New Route
                </button>
            </div>
            <div class="table-responsive">
                <table class="table table-hover shadow-sm bg-white">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Source</th>
                            <th>Destination</th>
                            <th>Distance</th>
                            <th>Duration</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>`;
        
        routes.forEach(r => {
            const actualId = r.routeId || r.id;
            // Stringify the object so we can pass it to the edit function safely
            const routeData = JSON.stringify(r).replace(/'/g, "\\'");

            html += `
                <tr>
                    <td>${actualId}</td>
                    <td>${r.source}</td>
                    <td>${r.destination}</td>
                    <td>${r.distance} km</td>
                    <td>${r.estimatedDuration}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary me-2" onclick='editRoute(${routeData})'>
                            <i class="fa-solid fa-pen"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteRoute(${actualId})">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>`;
        });

        html += `</tbody></table></div>`;
        container.innerHTML = html;

    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger">Error loading routes: ${error.message}</div>`;
    }
}

// 2. FORM TEMPLATE: This keeps your dashboard.html clean
function getRouteFormHTML() {
    return `
        <form id="routeForm">
            <input type="hidden" id="routeId">
            <div class="mb-3">
                <label class="form-label font-weight-bold">Source City</label>
                <input type="text" id="source" class="form-control" placeholder="e.g. Mumbai" required>
            </div>
            <div class="mb-3">
                <label class="form-label font-weight-bold">Destination City</label>
                <input type="text" id="destination" class="form-control" placeholder="e.g. Delhi" required>
            </div>
            <div class="mb-3">
                <label class="form-label font-weight-bold">Distance (km)</label>
                <input type="number" id="distance" class="form-control" placeholder="e.g. 1400" required>
            </div>
            <div class="mb-3">
                <label class="form-label font-weight-bold">Estimated Duration</label>
                <input type="text" id="duration" class="form-control" placeholder="HH:mm:ss (e.g. 02:15:00)" required>
            </div>
            <hr>
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary w-100">Save Route</button>
                <button type="button" class="btn btn-secondary w-100" data-bs-dismiss="modal">Cancel</button>
            </div>
        </form>`;
}

// 3. OPEN MODAL: For Adding
window.openRouteModal = function() {
    document.getElementById('modalTitle').innerText = "Create New Flight Route";
    const body = document.getElementById('modalBody');
    body.innerHTML = getRouteFormHTML(); // Inject form
    
    setupRouteFormListener(); // Attach the "Save" logic
    
    const modal = new bootstrap.Modal(document.getElementById('adminModal'));
    modal.show();
};

// 4. OPEN MODAL: For Editing
window.editRoute = function(route) {
    document.getElementById('modalTitle').innerText = "Update Existing Route";
    const body = document.getElementById('modalBody');
    body.innerHTML = getRouteFormHTML(); // Inject form
    
    setupRouteFormListener(); // Attach the "Save" logic

    // Fill the injected form with existing data
    document.getElementById('routeId').value = route.routeId || route.id;
    document.getElementById('source').value = route.source;
    document.getElementById('destination').value = route.destination;
    document.getElementById('distance').value = route.distance;
    document.getElementById('duration').value = route.estimatedDuration;

    const modal = new bootstrap.Modal(document.getElementById('adminModal'));
    modal.show();
};

// 5. SAVE LOGIC: Handles both POST and PUT
function setupRouteFormListener() {
    const form = document.getElementById('routeForm');
    form.onsubmit = async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('routeId').value;
        const routeData = {
            source: document.getElementById('source').value,
            destination: document.getElementById('destination').value,
            distance: parseInt(document.getElementById('distance').value),
            estimatedDuration: document.getElementById('duration').value
        };

        const isUpdate = id !== "";
        const url = isUpdate ? `/api/admin/routes/${id}` : '/api/admin/routes';
        const method = isUpdate ? 'PUT' : 'POST';

        try {
            const response = await fetch(url, {
                method: method,
                headers: getHeaders(), // Function inside admin.js
                body: JSON.stringify(routeData)
            });

			if (response.ok) {
			                alert(isUpdate ? "Route updated successfully!" : "New route added!");
			                
			                // 1. Properly Close the Modal
			                const modalElement = document.getElementById('adminModal');
			                let modalInstance = bootstrap.Modal.getInstance(modalElement);
			                
			                // If the instance doesn't exist yet, create one to hide it
			                if (!modalInstance) {
			                    modalInstance = new bootstrap.Modal(modalElement);
			                }
			                modalInstance.hide();

			                // 2. Refresh the UI
			                // We pass null for the event so admin.js doesn't crash
			                showSection('routes', null); 
			                
			            
            } else {
                alert("Failed to save route. Check if data is valid.");
            }
        } catch (error) {
            console.error("Save error:", error);
            alert("Connection error!");
        }
    };
}

// 6. DELETE LOGIC
window.deleteRoute = async function(id) {
    if (!confirm("Are you sure you want to remove this route?")) return;

    try {
        const response = await fetch(`/api/admin/routes/${id}`, {
            method: 'DELETE',
            headers: getHeaders()
        });

        const msg = await response.text();
        alert(msg);
        showSection('routes'); // Refresh
    } catch (error) {
        alert("Could not delete route.");
    }
};