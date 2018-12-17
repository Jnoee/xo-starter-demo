package com.github.jnoee.xo.starter.demo.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.jnoee.xo.dwz.login.AbstractLoginAction;

/**
 * 登录。
 */
@Controller
@RequestMapping("/")
public class LoginAction extends AbstractLoginAction {
}
