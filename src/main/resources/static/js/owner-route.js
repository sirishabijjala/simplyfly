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