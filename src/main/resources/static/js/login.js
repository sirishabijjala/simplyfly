document
.getElementById("loginForm")
.addEventListener("submit", function(e){

e.preventDefault();

let data = {

email: document.getElementById("email").value,

password: document.getElementById("password").value

};

fetch("/auth/login",{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify(data)

})

.then(res=>res.json())

.then(res=>{

localStorage.setItem("token",res.token);
localStorage.setItem("role",res.role);

alert("Login Successful");

if(res.role === "ADMIN"){
window.location.href="/admin/dashboard.html";
}
else if(res.role === "OWNER"){
window.location.href="/owner/dashboard.html";
}
else {
window.location.href="/user/dashborad.html";
}

})

.catch(err=>console.log(err));

});