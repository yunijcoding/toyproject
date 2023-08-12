function addCartBtn(menu_id){
	console.log(menu_id);
	
	fetch("/mykiosk/add?menu_id=" + parseInt(menu_id))
	// 서버에서 보낸 문자열을 받음
	.then(response => {
        if (!response.ok) {
            console.log("Add Error 발생");
            throw new Error("Add Error 발생");
        }
        return response.text(); 
    })
    // 서버에서 보낸 문자열을 처리
    .then(html => {
        //console.log("Response from server:", html);
        document.querySelector("#cart-list-main").innerHTML = html;
        const cartList = new DOMParser().parseFromString(html, 'text/html').querySelector("#cart-list-wrapper").innerHTML;
       	document.querySelector("#cart-list-main").innerHTML = cartList;
        
    })
    .catch(error => {
        console.error("Error:", error);
    });
}

function deleteCartBtn(menu_id){
	console.log(menu_id);
	
	//XMLHttpRequest 객체 생성
	const xhr = new XMLHttpRequest();
	//요청 준비
	xhr.open("GET", "/mykiosk/delete?menu_id=" + parseInt(menu_id));
	//요청 완료 시 처리
	xhr.onload = () => {
		if(xhr.status === 200){
			//console.log(xhr.responseText);
			
			//삭제 후 목록이 바로 갱신될 수 있도록 처리			
			const html = xhr.responseText;
			document.querySelector("#cart-list-main").innerHTML = html;
	        const cartList = new DOMParser().parseFromString(html, 'text/html').querySelector("#cart-list-wrapper").innerHTML;
	       	document.querySelector("#cart-list-main").innerHTML = cartList;
		}
	}
	xhr.send();
}

function minusBtn(menu_id){
	//XMLHttpRequest 객체 생성
	const xhr = new XMLHttpRequest();
	//요청 준비
	xhr.open("GET", "/mykiosk/minus?menu_id=" + parseInt(menu_id));
	//요청 완료 시 처리
	xhr.onload = () => {
		if(xhr.status === 200){
			//삭제 후 목록이 바로 갱신될 수 있도록 처리			
			const html = xhr.responseText;
			document.querySelector("#cart-list-main").innerHTML = html;
	        const cartList = new DOMParser().parseFromString(html, 'text/html').querySelector("#cart-list-wrapper").innerHTML;
	       	document.querySelector("#cart-list-main").innerHTML = cartList;
		}
	}
	xhr.send();
}




/*document.addEventListener("click", e =>  {
    const target = e.target;
    
    const openModalBtn = document.getElementById("openModalBtn");
    const modal = document.getElementById("myModal");

    if(target === openModalBtn){
		alert("order button click");
		modal.style.display = "block";		
    }
    else if(target === modal){
		alert("modal click")
	}
})*/



































