const ownerId = 1;

function loadFlights(){

fetch("/owner/flights/"+ownerId,{
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})

.then(res=>res.json())

.then(data=>{

let table = document.getElementById("flightTable");

table.innerHTML="";

data.forEach(f=>{

table.innerHTML += `
<tr>
<td>${f.id}</td>
<td>${f.flightName}</td>
<td>${f.flightNumber}</td>
<td>${f.cabinBaggage}</td>
<td>${f.checkInBaggage}</td>
</tr>
`;

});

});

}

function addFlight(){

let data = {

flightName: document.getElementById("flightName").value,
flightNumber: document.getElementById("flightNumber").value,
cabinBaggage: document.getElementById("cabinBaggage").value,
checkInBaggage: document.getElementById("checkInBaggage").value

};

fetch("/owner/flights/"+ownerId,{

method:"POST",

headers:{
"Content-Type":"application/json",
"Authorization":"Bearer "+localStorage.getItem("token")
},

body:JSON.stringify(data)

})

.then(res=>res.json())

.then(data=>{
alert("Flight Added");
loadFlights();
});

}