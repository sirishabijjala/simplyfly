document
.getElementById("flightForm")
.addEventListener("submit",function(e){

e.preventDefault();

let ownerId = 1;

let data = {

flightName: document.getElementById("flightName").value,

flightNumber: document.getElementById("flightNumber").value,

checkInBaggage: document.getElementById("checkInBaggage").value,

cabinBaggage: document.getElementById("cabinBaggage").value

};

fetch(`/owner/flights/${ownerId}`,{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify(data)

})

.then(res=>res.json())

.then(()=>{

alert("Flight Added");

window.location.href="/owner/flights.html";

});

});