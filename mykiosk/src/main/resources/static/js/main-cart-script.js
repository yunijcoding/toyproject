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

//modal 창 처리
document.addEventListener("click", e => {
	const target = e.target;
	
	/*
	<div id="modal">
		<div class="modal-content">
			<div class="modal-title">Order
				<button class="close" id="close-modal">close</button>
			</div>
			<div id="content-div"></div>
		</div>
	</div>
	 */
	
	//body 태그 안에 <div id="modal"></div> 생성
	const div1 = document.createElement("div");
	div1.setAttribute("id", "modal");
	document.body.appendChild(div1);
	
	//div1 태그 안에 <div class="modal-content"> 생성
	const div2 = document.createElement("div");
	div2.setAttribute("class", "modal-content");
	//document.div1.appendChild(div2); => document 붙이면 안됨!
	div1.appendChild(div2);
	
	const textDiv = document.createElement("div");
	textDiv.setAttribute("class", "modal-title mb-5");
	textDiv.innerHTML = "Order";
	div2.appendChild(textDiv);
	
	const contentDiv = document.createElement("div");
	contentDiv.setAttribute("id", "content-div");
	contentDiv.setAttribute("class", "mb-5");
	div2.appendChild(contentDiv);
	
	const button = document.createElement("button");
	button.setAttribute("id", "close-modal");
	button.setAttribute("class", "close");
	button.innerHTML = "close";
	textDiv.appendChild(button);
	
	const openModalBtn = document.getElementById("openModalBtn");
	const modal = document.getElementById("modal");
	const closeModalBtn = document.getElementById("close-modal");
	
	if(target === openModalBtn){
		console.log("click");
		modal.style.display = "block";
		
		//모달창 목록
		//XMLHttpRequest 객체 생성
		const xhr = new XMLHttpRequest();
		//요청 준비
		xhr.open("POST", "/mykiosk/cart-modal");
		//요청 완료 시 처리
		xhr.onload = () => {
			if(xhr.status === 200){
				//삭제 후 목록이 바로 갱신될 수 있도록 처리
				const html = xhr.responseText;
				//console.log(html);
				
				//Order 버튼 클릭하면 modal 창 나오면서 기존 목록이 모달창에 나오는 목록으로 바뀌는 현상 발생
				//document.querySelector("#cart-list-main").innerHTML = html;
		        const cartList = new DOMParser().parseFromString(html, 'text/html').querySelector("div#cart-list2-wrapper").innerHTML;
		       	document.querySelector("#content-div").innerHTML = cartList;
		       	
		       	document.querySelector("#content-div").setAttribute("class", "modal-cart-list");
			}
		}
		xhr.send();
	}
	else if(target === closeModalBtn){
		modal.style.display = "none";
		
		//fetch에서 POST 방식으로 요청 시 이렇게 POST 명시해야 함!
		fetch("/mykiosk/cart-list", {
			method: "POST",
			headers: {
    			"Content-Type": "application/json",
  			},
		})
		.then(response => {
	        return response.text(); 
	    })
	    .then(html => {
	        //document.querySelector("#cart-list-main").innerHTML = html;
	        const cartList = new DOMParser().parseFromString(html, 'text/html').querySelector("#cart-list-wrapper").innerHTML;
	       	document.querySelector("#cart-list-main").innerHTML = cartList;
	    })
	}
})


//새로고침 할 때 cart 데이터를 비우고 싶다
window.addEventListener("beforeunload", () => {
	fetch("/mykiosk/clear-cart", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then(response => {
        return response.text(); 
    })
    .then(html => {
        document.querySelector("#cart-list-main").innerHTML = html;
        const cartList = new DOMParser().parseFromString(html, 'text/html').querySelector("#cart-list-wrapper").innerHTML;
       	document.querySelector("#cart-list-main").innerHTML = cartList;
    })
});

//tap menu 처리
function openTab(e){
	e.preventDefault();
	
	const tabs = document.querySelectorAll(".tab");
	
	tabs.forEach(tab => {
		
		tab.classList.remove("active");
		
		if(tab === e.target){
			
			tab.classList.add("active");
			
			fetch("/mykiosk/menu-tab?type=" + tab.id)
			.then(response => {
		        return response.text(); 
		    })
		    .then(html => {
		        const menuList = new DOMParser().parseFromString(html, 'text/html').querySelector("#menu-list-tab").innerHTML;
		       	document.querySelector("#menu-content-wrapper").innerHTML = menuList;
		    })
		}
	})
}































