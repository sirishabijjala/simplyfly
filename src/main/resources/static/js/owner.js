// --- GLOBAL CONFIGURATION ---
const BASE_URL = "http://localhost:8080/api/owner";
const token = localStorage.getItem('token');

// 1. INITIALIZE DASHBOARD ON LOAD
document.addEventListener('DOMContentLoaded', () => {
    if (!token) {
        window.location.href = "login.html"; // Guard: No token, no entry
    }
    loadOwnerProfile();
    // Default section to show
    showOwnerSection('flights'); 
});

// 2. FETCH OWNER PROFILE (Dynamic Name in Header)
async function loadOwnerProfile() {
    try {
        const response = await fetch(`${BASE_URL}/profile`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        
        if (response.ok) {
            const owner = await response.json();
            // Update the UI with actual Owner Name (e.g., Air India)
            document.getElementById('owner-display-name').innerText = owner.name + " | Owner Portal";
            document.getElementById('welcome-name').innerText = owner.name;
        } else {
            console.error("Failed to load profile. Token might be expired.");
            logout();
        }
    } catch (error) {
        console.error("Error fetching profile:", error);
    }
}

// 3. SECTION SWITCHER (The SPA Logic)
function showOwnerSection(section) {
    const contentArea = document.getElementById('content-area');
    
    // Clear the current view
    contentArea.innerHTML = '<div class="text-center mt-5"><div class="spinner-border text-primary"></div></div>';

    switch(section) {
        case 'flights':
            // Logic inside owner-flight.js
            renderFlightManagement(contentArea);
            break;
            
        case 'schedules':
            // Logic inside owner-schedule.js
            renderScheduleManagement(contentArea);
            break;
            
        case 'bookings':
            // Logic inside owner-booking.js
            renderBookingReports(contentArea);
            break;
            
        default:
            contentArea.innerHTML = "<h2>Section Under Development</h2>";
    }
}

// 4. LOGOUT LOGIC
function logout() {
    if(confirm("Are you sure you want to logout?")) {
        localStorage.clear();
        window.location.href = "/auth/login.html";
    }
}

// 5. HELPER: GLOBAL ERROR HANDLER
function handleApiError(response) {
    if (response.status === 403) {
        alert("Access Denied: You do not have Owner permissions.");
        logout();
    } else if (response.status === 401) {
        alert("Session Expired. Please login again.");
        logout();
    }
}