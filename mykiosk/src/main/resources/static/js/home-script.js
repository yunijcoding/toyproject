
/*home.html에서 button 이벤트 처리*/
const listBtn = document.querySelector("#listBtn");
listBtn.addEventListener("click", () => {
	window.location.href = "http://localhost:8080/mykiosk/menu-list";
});