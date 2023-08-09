
function addCartBtn(menu_id){
	console.log(menu_id);
	
	fetch("/mykiosk/add?menu_id=" + parseInt(menu_id))
	.then(response => {
        if (!response.ok) {
            console.log("Add Error 발생");
            throw new Error("Add Error 발생");
        }
        return response.text(); // 서버에서 보낸 문자열을 받음
    })
    .then(html => {
        console.log("Response from server:", html);
        // 서버에서 보낸 문자열을 처리
        
        document.querySelector("#cart-list-main").innerHTML = html;
        const cartList = new DOMParser().parseFromString(html, 'text/html').querySelector("#cart-list-wrapper").innerHTML;
       	document.querySelector("#cart-list-main").innerHTML = cartList;
        
    })
    .catch(error => {
        console.error("Error:", error);
    });
}
