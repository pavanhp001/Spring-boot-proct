package  com.AL.controller.error;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ErrorController {

    @RequestMapping("/error/404")
    public ModelAndView error404Handler() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("404");
        return mav;
    }
    
    
    @RequestMapping("/error/301")
	public RedirectView error301(HttpServletResponse response) {

		/* 301 redirect */
		RedirectView redirectView = new RedirectView("index");
		redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return redirectView;
	}
    
    
    @RequestMapping("/error/403")
	public RedirectView error403(HttpServletResponse response) {

		/* 301 redirect */
		RedirectView redirectView = new RedirectView("index");
		redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return redirectView;
	}
}