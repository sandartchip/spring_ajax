package kr.or.connect.mvcexam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan (  basePackages = { "kr.or.connect.mvcexam" } ) //여기서 컴포넌트들을 찾는다.
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter{
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
        /* img, js로 오는 요청은 여기서 처리. */
    }
	
	
	// default servlet handler를 사용하게 합니다.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
        // 매핑 정보가 없는 URL 요청은  SPRING의 DefaultServletHandler가 처리.
        // was는 default servlet이 static한 자원을 읽어서 보이게 해줌
    }
    
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
    		System.out.println("아무것도 없음");
    		System.out.println("addViewControllers가 호출됩니다. ");
    		registry.addViewController("/").setViewName("list"); 
    		//컨트롤러 클래스를 작성하지 않고 매핑할 수 있게 해주는거 (디폴트 view)
    		//view name은 ViewResolver로 찾아옴
    }
    
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
    	InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    	resolver.setPrefix("/WEB-INF/views/");
    	resolver.setSuffix(".jsp"); 
    	return resolver; //suffix prefix 붙은게 view가 된다
    	//뷰 정보를 설정 (main-> WEB-INF/views/main.jsp)

    }
}
