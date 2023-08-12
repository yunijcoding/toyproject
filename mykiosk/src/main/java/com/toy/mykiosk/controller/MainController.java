package com.toy.mykiosk.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.Tuple;
import com.toy.mykiosk.dto.MenuCartDTO;
import com.toy.mykiosk.entity.CartEntity;
import com.toy.mykiosk.entity.MenuEntity;
import com.toy.mykiosk.service.CartService;
import com.toy.mykiosk.service.MenuService;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/mykiosk")
public class MainController {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CartService cartService;
	
	//Log4j2 Logger
	private final Logger logger = LogManager.getLogger(MainController.class);
	
	//(TEST) 클라이언트에게 JSON 형식으로 반환
	@GetMapping("/test")
	@ResponseBody
	public List<MenuEntity> getAll(){
		return this.menuService.getAll();
	}
	
	//(TEST) model에 담아서 반환 => template에서 thymeleaf를 이용해 사용 가능
	@GetMapping("/test1")
	public String getAllMenu(Model model) {
		
		//info Level Log write
        logger.warn("getAllMenu");
		
		List<MenuEntity> menuList = this.menuService.getAllMenu();
		
		model.addAttribute("menuList", menuList);
		
		return "test";
	}
	
	//(TEST) queryDSL test
	@GetMapping("/test2")
	@ResponseBody
	public List<MenuEntity> getMenuByPrice(){
		return this.menuService.getMenuByPrice(2000);
	}
	
	//===============start===============
	// 1. home
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	// 2. main list
	@GetMapping("/menu-list")
	public String totMenuList(Model model) {
		List<MenuEntity> menuList = this.menuService.getAllMenu();
		model.addAttribute("menuList", menuList);
		return "main_list";
	}
	
	//(TEST) 3. cart로 insert 해보기
	// 1) get 방식으로 fetch 요청을 보냈을 때 ~~add?menu_id=3 이렇게 url이 오면
	// @RequestParam 어노테이션으로 받고 괄호 안에 ?menu_id와 같은 변수를 사용한다!
	// 2) html을 보내려면 @ResponseBody 어노테이션을 추가하면 안된다!!!
//	@GetMapping("/add")
//	public String addToCart(@RequestParam("menu_id") Integer menu_id,
//			Model model) {
//		System.out.println("menu_id = " + menu_id);
//		try {
//			this.cartService.addToCart(menu_id);
//			
//			List<CartEntity> cartList = this.cartService.getAllCart();
//			
//			model.addAttribute("cartList", cartList);
//			
//			return "cart_list";
//		}
//		catch(Exception e) {
//			return "Error adding to cart: " + e.getMessage();
//		}
//	}
	
	// 3. cart insert
	@GetMapping("/add")
	public String addToCart(@RequestParam("menu_id") Integer menu_id,
			Model model) {
		System.out.println("menu_id = " + menu_id);
		try {
			this.cartService.addToCart(menu_id);
			
			List<MenuCartDTO> cartMenuJoin = this.cartService.getAllMenuJoinCartConstructor();
			
//			for(MenuCartDTO mcDto : cartMenuJoin) {
//				System.out.println(mcDto);
//			}
			
			//여기에 총합을 나타내는 메소드를 model에 추가해서 cart_list에 같이 보내보자!
			Long totalPrice = this.cartService.getTotalPrice();
//			System.out.println("totalPrice = " + totalPrice);
			
			model.addAttribute("cartMenuJoinList", cartMenuJoin);
			model.addAttribute("totalPrice", totalPrice);
			
			return "cart_list";
		}
		catch(Exception e) {
			return "Error adding to cart: " + e.getMessage();
		}
	}
	
	// 4. cart delete
	@GetMapping("/delete")
	public String deleteToCart(@RequestParam("menu_id") Integer menu_id,
			Model model) {
		System.out.println("menu_id = " + menu_id);
		try {
			this.cartService.deleteToCart(menu_id);
			
			List<MenuCartDTO> cartMenuJoin = this.cartService.getAllMenuJoinCartConstructor();
			
			model.addAttribute("cartMenuJoinList", cartMenuJoin);
			
			Long totalPrice = this.cartService.getTotalPrice();
			model.addAttribute("totalPrice", totalPrice);
			
			return "cart_list";
		}
		catch(Exception e) {
			return "Error deleting to cart: " + e.getMessage();
		}
	}
	
	// - 버튼 클릭 시 delete 처리
	@GetMapping("/minus")
	public String minusToCart(@RequestParam("menu_id") Integer menu_id,
			Model model) {
		Integer cart_id = this.cartService.getOneCartId(menu_id);
		
		this.cartService.deleteCartId(cart_id);
		
		List<MenuCartDTO> cartMenuJoin = this.cartService.getAllMenuJoinCartConstructor();
		
		model.addAttribute("cartMenuJoinList", cartMenuJoin);
		
		Long totalPrice = this.cartService.getTotalPrice();
		model.addAttribute("totalPrice", totalPrice);
		
		return "cart_list";
	}
}

















