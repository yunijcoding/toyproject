package com.toy.mykiosk.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toy.mykiosk.entity.MenuEntity;
import com.toy.mykiosk.service.MenuService;

@Controller
@RequestMapping("/mykiosk")
public class MainController {
	
	@Autowired
	private MenuService menuService;
	
	//Log4j2 Logger
	private final Logger logger = LogManager.getLogger(MainController.class);
	
	//클라이언트에게 JSON 형식으로 반환
	@GetMapping("/test")
	@ResponseBody
	public List<MenuEntity> getAll(){
		return this.menuService.getAll();
	}
	
	//model에 담아서 반환 => template에서 thymeleaf를 이용해 사용 가능
	@GetMapping("/test1")
	public String getAllMenu(Model model) {
		
		//info Level Log write
        logger.warn("getAllMenu");
		
		List<MenuEntity> menuList = this.menuService.getAllMenu();
		
		model.addAttribute("menuList", menuList);
		
		return "test";
	}
	
	
	@GetMapping("/test2")
	@ResponseBody
	public List<MenuEntity> getMenuByPrice(){
		return this.menuService.getMenuByPrice(2000);
	}
}
