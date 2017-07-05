package es.jgpelaez.openshift.sb.zuulserver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;

//@Configuration
public class ZuulHandlerBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

	// private final MappedInterceptor myInterceptor =new Mapp;

	@Override
	public boolean postProcessAfterInstantiation(final Object bean, final String beanName) throws BeansException {
		System.out.println("beanName: " +bean.getClass());
		if (bean instanceof ZuulHandlerMapping) {
			MyInterceptor myInterceptor = new MyInterceptor();

			ZuulHandlerMapping zuulHandlerMapping = (ZuulHandlerMapping) bean;
			zuulHandlerMapping.setInterceptors(myInterceptor);
		}

		return super.postProcessAfterInstantiation(bean, beanName);
	}

	public static class MyInterceptor implements org.springframework.web.servlet.HandlerInterceptor {

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			System.out.println("preHandle " );

			return false;
		}

		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
				ModelAndView modelAndView) throws Exception {


		}

		@Override
		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
				Exception ex) throws Exception {


		}

	}

}