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
	// save token
	localStorage.setItem("token",res.token);

	// show success message
	let msg = document.getElementById("loginMessage");
	msg.style.display = "block";
	msg.innerText = "Login successful! Redirecting...";

	// redirect after 2 seconds
	setTimeout(() => {

	if(res.role === "ADMIN"){
	window.location.href="/admin/admin-dashboard.html";
	}
	else if(res.role === "OWNER"){
	window.location.href="/owner/dashboard.html";
	}
	else{
	window.location.href="/user/loginprofile.html";
	}

	},2000);

	})

	.catch(err=>{
	console.log(err);

	let msg = document.getElementById("loginMessage");
	msg.style.display = "block";
	msg.classList.remove("alert-success");
	msg.classList.add("alert-danger");
	msg.innerText = "Login failed. Please check your credentials.";
	});
});