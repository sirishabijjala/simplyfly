// ── DATE SETUP ──────────────────────────────────────────────
document.addEventListener("DOMContentLoaded", function () {
    let today = new Date().toISOString().split("T")[0];
    document.getElementById("journeyDate").setAttribute("min", today);

    // Close passenger panel on outside click
    document.addEventListener("click", function (e) {
        const panel   = document.getElementById("passengerPanel");
        const trigger = document.getElementById("passengerTrigger");
        if (panel && trigger &&
            !panel.contains(e.target) &&
            !trigger.contains(e.target)) {
            panel.classList.remove("open");
            trigger.classList.remove("open");
        }
    });

    updatePassengerSummary();
});

// ── PASSENGER PANEL TOGGLE ───────────────────────────────────
function togglePassengerPanel() {
    const panel   = document.getElementById("passengerPanel");
    const trigger = document.getElementById("passengerTrigger");
    panel.classList.toggle("open");
    trigger.classList.toggle("open");
}

// ── PASSENGER COUNTERS ───────────────────────────────────────
let counts = { adult: 1, child: 0, infant: 0 };

function updatePassengerSummary() {
    const parts = [];
    if (counts.adult)  parts.push(`${counts.adult} Adult${counts.adult > 1 ? 's' : ''}`);
    if (counts.child)  parts.push(`${counts.child} Child${counts.child > 1 ? 'ren' : ''}`);
    if (counts.infant) parts.push(`${counts.infant} Infant${counts.infant > 1 ? 's' : ''}`);
    const summary = parts.length ? parts.join(', ') : '0';
    document.getElementById("passengerSummary").textContent = summary;
}

function increment(type) {
    counts[type]++;
    document.getElementById(type + "Count").textContent = counts[type];
    updatePassengerSummary();
}

function decrement(type) {
    if (counts[type] > 0 && !(type === 'adult' && counts[type] === 1)) {
        counts[type]--;
        document.getElementById(type + "Count").textContent = counts[type];
        updatePassengerSummary();
    }
}

// ── FLIGHT SEARCH (original redirect preserved) ──────────────
document.getElementById("flightSearchForm").addEventListener("submit", function (e) {
    e.preventDefault();

    let origin      = document.getElementById("origin").value;
    let destination = document.getElementById("destination").value;
    let journeyDate = document.getElementById("journeyDate").value;

    if (!origin || !destination) {
        showToast("Please select both From and To cities!", "warning");
        return;
    }
    if (origin === destination) {
        showToast("Origin and Destination cannot be the same!", "warning");
        return;
    }
    if (!journeyDate) {
        showToast("Please select a journey date!", "warning");
        return;
    }

    // ── Original redirect URL ──
    window.location.href = `/user/search-results.html?origin=${origin}&destination=${destination}&journeyDate=${journeyDate}&adult=${counts.adult}&child=${counts.child}&infant=${counts.infant}`;
});

// ── NAVIGATION (original functions) ─────────────────────────
function logout() {
    window.location.href = "/auth/login.html";
}

function goToBookings() {
    window.location.href = "/user/manage-booking.html";
}

// ── TOAST (replaces alert) ───────────────────────────────────
function showToast(message, type) {
    const existing = document.getElementById("sf-toast");
    if (existing) existing.remove();

    const colors = { warning: "#f5a623", error: "#ff5c5c", success: "#00c48c" };

    const toast = document.createElement("div");
    toast.id = "sf-toast";
    toast.innerHTML = `<i class="fa-solid fa-circle-exclamation me-2"></i>${message}`;
    Object.assign(toast.style, {
        position:     "fixed",
        bottom:       "30px",
        left:         "50%",
        transform:    "translateX(-50%)",
        background:   colors[type] || "#1a6bff",
        color:        "white",
        padding:      "14px 28px",
        borderRadius: "99px",
        fontFamily:   "'Plus Jakarta Sans', sans-serif",
        fontWeight:   "600",
        fontSize:     "14px",
        zIndex:       "9999",
        boxShadow:    "0 8px 32px rgba(0,0,0,0.18)",
        whiteSpace:   "nowrap"
    });
    document.body.appendChild(toast);
    setTimeout(() => toast.remove(), 3500);
}