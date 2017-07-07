package com.tb.query.api.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tb.query.api.service.UserQueryService;
import com.tb.sso.query.api.bean.TbUser;


@Controller()
@RequestMapping("/user")
public class UserController  {

	/**
	 * 用户服务
	 */
	@Resource(name = "userQueryService")
	private UserQueryService userService;

	
	
	/**
	 * 更具生成token获取用户信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/queryUserByToken/{token}",method=RequestMethod.GET)
	public ResponseEntity<TbUser> queryUserByToken(@PathVariable("token")String token){
	try {
		TbUser user=	this.userService.queryUserByToken(token);
		if(null ==user){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(user);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
