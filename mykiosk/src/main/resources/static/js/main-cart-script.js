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

function checkStampBtn(){
	//alert("click");
	
	//적립 버튼 클릭 
	// => 핸드폰 번호, 이름 입력 시 일치하는 user 목록 보여줌(css로 show / hide)
	// => 건너뛰기 클릭 시 다시 적립 / 결제 버튼 나타나기
	
	const modalBtnList = document.getElementById("modal-btn-list");
	const modalUserDiv = document.getElementById("modal-user-wrapper");
	
	modalBtnList.classList.remove("show");
	modalBtnList.classList.add("hide");
	
	modalUserDiv.classList.remove("hide");
	modalUserDiv.classList.add("show");
	
}

document.addEventListener("click", e => {
	const target = e.target;
	const userChkBtn = document.getElementById("user_check_btn");
	const user_number = document.getElementById("user_number");
	
	const userListDiv = document.querySelector("#user-list");
	
	const userJoin = document.querySelector("#user-join");
	const userChk = document.querySelector("#user-check");
	const paymentBtn = document.querySelector("#payment-btn");
	
	const backBtn = document.getElementById("back_btn");
	
	if(target === userChkBtn){
		//alert("click");
		console.log(user_number.value);
		
		var formData = new FormData();
		formData.append("user_number", user_number.value);
		
		const xhr = new XMLHttpRequest();
		xhr.open("POST", "/mykiosk/userCheck");
		xhr.onload = () => {
			if(xhr.status === 200){
				
				const datas = JSON.parse(xhr.response);
				
				let data = "";
				
				if(datas.length !== 0){
					for(let i = 0; i < datas.length; i++){
						data += "<ul><li>" + datas[i]["user_name"];
						data += " / " + datas[i]["user_number"];
						data += " / " + datas[i]["user_stamp"];
						data += "&nbsp;&nbsp;&nbsp;<input type=" + 'checkbox ';
						data += "id=" + 'chkBox' + " value=" + datas[i]["user_id_pk"] + " />"
						data += "</li></ul>";
					}
					
					userListDiv.innerHTML = data;
					
					userChk.addEventListener("click", e => {
						const chkBox = document.getElementById("chkBox");
						console.log(chkBox.value);
						
						//적립하려는 사용자 id를 localStorage에 저장
						localStorage.setItem("user_id", chkBox.value);
					})
				}
				else{
					data += "등록된 회원이 없습니다";
					
					userListDiv.innerHTML = data;
				}
			}
		}
		xhr.send(formData);
	}
	else if(target === backBtn){
		//alert("click");
		
		const modalBtnList = document.getElementById("modal-btn-list");
		const modalUserDiv = document.getElementById("modal-user-wrapper");
		
		modalBtnList.classList.remove("hide");
		modalBtnList.classList.add("show");
		
		modalUserDiv.classList.remove("show");
		modalUserDiv.classList.add("hide");
		
		user_number.value = "";
		userListDiv.innerHTML = "";
	}
})


























