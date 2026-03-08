
/* ── DURATION INPUT HTML (reused in inline + modal) ──── */
function durationFieldsHTML(prefix) {
  return `
    <div class="form-group">
      <label>Duration</label>
      <div class="duration-row">
        <div class="duration-field">
          <input class="form-control text-center" id="${prefix}-hrs"
            type="number" min="0" max="23" placeholder="00"/>
          <span class="duration-label">hrs</span>
        </div>
        <span class="duration-sep">:</span>
        <div class="duration-field">
          <input class="form-control text-center" id="${prefix}-min"
            type="number" min="0" max="59" placeholder="00"/>
          <span class="duration-label">min</span>
        </div>
        <span class="duration-sep">:</span>
        <div class="duration-field">
          <input class="form-control text-center" id="${prefix}-sec"
            type="number" min="0" max="59" placeholder="00"/>
          <span class="duration-label">sec</span>
        </div>
      </div>
    </div>`;
}

/* ── BUILD "HH:mm:ss" from spinner values ────────────── */
function buildDuration(prefix) {
  const h = String(parseInt(document.getElementById(`${prefix}-hrs`).value) || 0).padStart(2,'0');
  const m = String(parseInt(document.getElementById(`${prefix}-min`).value) || 0).padStart(2,'0');
  const s = String(parseInt(document.getElementById(`${prefix}-sec`).value) || 0).padStart(2,'0');
  return `${h}:${m}:${s}`;
}

/* ── VALIDATE DURATION: at least one field > 0 ───────── */
function isDurationValid(prefix) {
  const h = parseInt(document.getElementById(`${prefix}-hrs`).value) || 0;
  const m = parseInt(document.getElementById(`${prefix}-min`).value) || 0;
  const s = parseInt(document.getElementById(`${prefix}-sec`).value) || 0;
  return (h + m + s) > 0;
}

/* ── LOAD ROUTES PAGE ────────────────────────────────── */
function loadRoutes(container) {
  container.innerHTML = `
    <div class="fade-in">
      <div class="topbar">
        <div>
          <div class="page-title">Routes</div>
          <div class="page-subtitle">Add new routes to the system</div>
        </div>
        <button class="btn btn-primary" onclick="openRouteModal()">＋ Add Route</button>
      </div>

      <div class="info-notice">
        <span class="info-icon">ℹ</span>
        <div>
          <strong>Owner route access:</strong> You can add new routes from this panel.
          To view, edit, or remove existing routes, please contact the administrator.
        </div>
      </div>

      <div class="table-card">
        <div class="table-card-header">
          <div class="table-card-title">Add a New Route</div>
        </div>
        <div style="padding: 28px;">
          <div class="form-row">
            <div class="form-group">
              <label>Source City</label>
              <input class="form-control" id="ri-source" placeholder="e.g. Mumbai"/>
            </div>
            <div class="form-group">
              <label>Destination City</label>
              <input class="form-control" id="ri-dest" placeholder="e.g. Delhi"/>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>Distance (km)</label>
              <input class="form-control" id="ri-dist" type="number" placeholder="1400"/>
            </div>
            ${durationFieldsHTML('ri')}
          </div>
          <div style="margin-top: 16px;">
            <button class="btn btn-primary" onclick="submitInlineRoute()">＋ Add Route</button>
          </div>
        </div>
      </div>

      <div id="route-log"></div>
    </div>`;
}

/* ── SUBMIT INLINE ROUTE FORM ────────────────────────── */
async function submitInlineRoute() {
  const source = document.getElementById('ri-source').value.trim();
  const dest   = document.getElementById('ri-dest').value.trim();
  const dist   = document.getElementById('ri-dist').value.trim();

  if (!source || !dest || !dist) {
    alert('Source, destination and distance are required.');
    return;
  }
  if (!isDurationValid('ri')) {
    alert('Please enter a valid duration (at least 1 minute).');
    return;
  }

  const estimatedDuration = buildDuration('ri');

  const data = {
    source,
    destination:       dest,
    distance:          parseInt(dist),
    estimatedDuration
  };

  try {
    const result = await apiFetch('/api/owner/addRoute', {
      method: 'POST',
      body: JSON.stringify(data)
    });

    // Clear all fields
    ['ri-source','ri-dest','ri-dist','ri-hrs','ri-min','ri-sec'].forEach(id => {
      const el = document.getElementById(id);
      if (el) el.value = '';
    });

    appendRouteLog(result || data);
  } catch (e) {
    alert(`Error: ${e.message}`);
  }
}

/* ── APPEND SUCCESS LOG ENTRY ────────────────────────── */
function appendRouteLog(route) {
  const log = document.getElementById('route-log');

  if (!log.querySelector('.table-card')) {
    log.innerHTML = `
      <div class="table-card">
        <div class="table-card-header">
          <div class="table-card-title">Added This Session</div>
        </div>
        <table>
          <thead>
            <tr>
              <th>Source</th><th>Destination</th>
              <th>Distance</th><th>Duration</th><th>Status</th>
            </tr>
          </thead>
          <tbody id="route-log-body"></tbody>
        </table>
      </div>`;
  }

  const tbody = document.getElementById('route-log-body');
  const tr = document.createElement('tr');
  tr.innerHTML = `
    <td><strong>${route.source}</strong></td>
    <td>${route.destination}</td>
    <td>${Number(route.distance).toLocaleString()} km</td>
    <td>${route.estimatedDuration}</td>
    <td><span class="badge badge-open">✓ Added</span></td>`;
  tbody.prepend(tr);
}

/* ── MODAL VERSION ───────────────────────────────────── */
function openRouteModal() {
  openModal('Add Route', `
    <div class="form-row">
      <div class="form-group">
        <label>Source City</label>
        <input class="form-control" id="rm-source" placeholder="e.g. Mumbai"/>
      </div>
      <div class="form-group">
        <label>Destination City</label>
        <input class="form-control" id="rm-dest" placeholder="e.g. Delhi"/>
      </div>
    </div>
    <div class="form-row">
      <div class="form-group">
        <label>Distance (km)</label>
        <input class="form-control" id="rm-dist" type="number" placeholder="1400"/>
      </div>
      ${durationFieldsHTML('rm')}
    </div>
    <div class="form-actions">
      <button class="btn btn-ghost" onclick="closeModal()">Cancel</button>
      <button class="btn btn-primary" onclick="saveRouteFromModal()">Add Route</button>
    </div>
  `);
}

/* ── SAVE FROM MODAL ─────────────────────────────────── */
async function saveRouteFromModal() {
  const source = document.getElementById('rm-source').value.trim();
  const dest   = document.getElementById('rm-dest').value.trim();
  const dist   = document.getElementById('rm-dist').value.trim();

  if (!source || !dest || !dist) {
    alert('Source, destination and distance are required.');
    return;
  }
  if (!isDurationValid('rm')) {
    alert('Please enter a valid duration (at least 1 minute).');
    return;
  }

  const estimatedDuration = buildDuration('rm');

  const data = {
    source,
    destination:       dest,
    distance:          parseInt(dist),
    estimatedDuration
  };

  try {
    const result = await apiFetch('/api/owner/addRoute', {
      method: 'POST',
      body: JSON.stringify(data)
    });
    closeModal();
    appendRouteLog(result || data);
  } catch (e) {
    alert(`Error: ${e.message}`);
  }

async function loadRoutes(container){

const res=await fetch("/api/admin/routes",{headers:getHeaders()})

const routes=await res.json()

let html=`<h4>Routes</h4>

<button class="btn btn-primary mb-3" onclick="openRouteModal()">
Add Route
</button>

<table class="table table-bordered">

<tr>
<th>ID</th>
<th>Source</th>
<th>Destination</th>
<th>Distance</th>
<th>Duration</th>
</tr>
`

routes.forEach(r=>{

html+=`

<tr>

<td>${r.id}</td>
<td>${r.source}</td>
<td>${r.destination}</td>
<td>${r.distance}</td>
<td>${r.estimatedDuration}</td>

</tr>
`

})

html+=`</table>`

container.innerHTML=html

}

function openRouteModal(){

document.getElementById("modalTitle").innerText="Add Route"

document.getElementById("modalBody").innerHTML=`

<input id="source" class="form-control mb-2" placeholder="Source">

<input id="destination" class="form-control mb-2" placeholder="Destination">

<input id="distance" class="form-control mb-2" placeholder="Distance">

<input id="duration" class="form-control mb-2" placeholder="Duration">

<button class="btn btn-primary w-100" onclick="saveRoute()">Save</button>
`

new bootstrap.Modal(document.getElementById("ownerModal")).show()

}

async function saveRoute(){

let data={

source:document.getElementById("source").value,
destination:document.getElementById("destination").value,
distance:document.getElementById("distance").value,
estimatedDuration:document.getElementById("duration").value

}

await fetch("/api/owner/addRoute",{

method:"POST",
headers:getHeaders(),
body:JSON.stringify(data)

})

alert("Route Added")

showSection("routes")
}
}