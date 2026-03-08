/* ═══════════════════════════════════════════════════════
   owner.js  —  Shared utilities, modal, navigation, init
══════════════════════════════════════════════════════ */

/* ── AUTH HEADER ─────────────────────────────────────── */
function getHeaders() {
  return {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
  };
}

/* ── SAFE FETCH ──────────────────────────────────────── */
async function apiFetch(url, options = {}) {
  const res = await fetch(url, { headers: getHeaders(), ...options });
  const text = await res.text();
  if (!res.ok) throw new Error(text || `HTTP ${res.status}`);
  try { return JSON.parse(text); }
  catch { return text; }
}

/* ── ERROR DISPLAY ───────────────────────────────────── */
function showError(container, msg) {
  container.innerHTML = `
    <div class="alert-error">
      <span>⚠</span>
      <span>${msg}</span>
    </div>`;
}

/* ── LOGOUT ──────────────────────────────────────────── */
function logout() {
  localStorage.clear();
  window.location.href = '/auth/login.html';
}

/* ── DATE FORMAT ─────────────────────────────────────── */
function formatDate(dt) {
  if (!dt) return '—';
  return new Date(dt).toLocaleString('en-IN', {
    day: '2-digit', month: 'short', year: 'numeric',
    hour: '2-digit', minute: '2-digit'
  });
}

/* ── MODAL ───────────────────────────────────────────── */
function openModal(title, bodyHTML) {
  document.getElementById('modal-title').textContent = title;
  document.getElementById('modal-body').innerHTML = bodyHTML;
  document.getElementById('modal').classList.add('open');
}

function closeModal() {
  document.getElementById('modal').classList.remove('open');
}

function handleModalBackdropClick(e) {
  if (e.target === document.getElementById('modal')) closeModal();
}

/* ── NAVIGATION ──────────────────────────────────────── */
function navigate(section) {
  // Highlight active nav item
  document.querySelectorAll('.nav-item').forEach(el => el.classList.remove('active'));
  const navEl = document.getElementById(`nav-${section}`);
  if (navEl) navEl.classList.add('active');

  // Show spinner
  const main = document.getElementById('main-content');
  main.innerHTML = `<div class="spinner-wrap"><div class="spinner"></div></div>`;

  // Resolve loaders lazily so all scripts are guaranteed loaded
  const loaderMap = {
    flights:   typeof loadFlights   === 'function' ? loadFlights   : null,
    schedules: typeof loadSchedules === 'function' ? loadSchedules : null,
    routes:    typeof loadRoutes    === 'function' ? loadRoutes    : null
  };

  const loader = loaderMap[section];
  if (loader) {
    loader(main);
  } else {
    main.innerHTML = `<div class="alert-error"><span>⚠</span><span>Section "${section}" could not be loaded.</span></div>`;
  }
}

/* ── INIT ────────────────────────────────────────────────
   Called from dashboard.html AFTER all other scripts load,
   so loadFlights / loadSchedules / loadRoutes are defined.
──────────────────────────────────────────────────────── */
function initOwnerPanel() {
  const name = localStorage.getItem('ownerName')
            || localStorage.getItem('username')
            || 'Owner';
  document.getElementById('ownerName').textContent = name;
  navigate('flights');
}