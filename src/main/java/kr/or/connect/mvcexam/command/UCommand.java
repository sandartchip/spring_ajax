package kr.or.connect.mvcexam.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface UCommand {
	public void execute(HttpServletRequest request, Model model);
}
