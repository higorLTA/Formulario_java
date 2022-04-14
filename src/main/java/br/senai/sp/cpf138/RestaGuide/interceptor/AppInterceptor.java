package br.senai.sp.cpf138.RestaGuide.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.senai.sp.cpf138.RestaGuide.annotation.Publico;

@Component
public class AppInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// variavel para descobrir pra onde então tentando ir 
		String uri = request.getRequestURI();
		// mostrar  a URI
		System.out.println(uri);
		// verifica se o hendlerMethod, oque indica 
		//que foi encontrado um método em algum controller para a requisição 
		if(handler instanceof HandlerMethod) {
			// libera o acesso á pagina inicial
			if(uri.equals("/")) {
				return true;
				
			}
			if(uri.endsWith("/error")){
				return true;
				
			}
			// fazer o casting para handlerMethod
			HandlerMethod metodoChamado =(HandlerMethod) handler;
			// se o metodo for publico, libera
			if(metodoChamado.getMethodAnnotation(Publico.class)!= null) {
				return true;
				
			}
			// verifica se existe um usuario logado
			if(request.getSession().getAttribute("usuarioLogado")!= null) {
				return true;
				
			}else {
				//redireciona para pagina original
				response.sendRedirect("/");
				return false;
			}
			
		}
		return true;
	}
}
