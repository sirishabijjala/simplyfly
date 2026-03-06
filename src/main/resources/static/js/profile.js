const token = localStorage.getItem("token");

document.addEventListener("DOMContentLoaded",()=>{

fetch("/auth/profile",{

headers:{
"Authorization":"Bearer "+token
}

})

.then(res=>res.json())

.then(data=>{

document.getElementById("profileId").innerText=data.id;

document.getElementById("profileName").innerText=data.name;
document.getElementById("profileName2").innerText=data.name;

document.getElementById("profileEmail").innerText=data.email;
document.getElementById("profileEmail2").innerText=data.email;

document.getElementById("profileRole").innerText=data.role;

document.getElementById("profileStatus").innerText=
data.active?"Active":"Inactive";

})

.catch(err=>console.log(err));

});

function showSection(section){

document.getElementById("detailsSection").style.display="none";
document.getElementById("bookingSection").style.display="none";
document.getElementById("manageSection").style.display="none";

if(section==="details"){
document.getElementById("detailsSection").style.display="block";
}

if(section==="bookings"){
document.getElementById("bookingSection").style.display="block";
loadBookings();
}

if(section==="manage"){
document.getElementById("manageSection").style.display="block";
}

}

function logout(){

localStorage.removeItem("token");

window.location.href="/auth/login.html";

}

function loadBookings(){

fetch("/api/bookings/my",{

headers:{
"Authorization":"Bearer "+token
}

})

.then(res=>res.json())

.then(data=>{

let rows="";

data.forEach(b=>{

rows+=`

<tr>

<td>${b.id}</td>
<td>${b.flightNumber}</td>
<td>${b.journeyDate}</td>
<td>${b.status}</td>

</tr>

`;

});

document.getElementById("bookingTable").innerHTML=rows;

});

}