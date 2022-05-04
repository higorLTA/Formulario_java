package br.senai.sp.cpf138.RestaGuide.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.senai.sp.cpf138.RestaGuide.annotation.Privado;
import br.senai.sp.cpf138.RestaGuide.annotation.Publico;
import br.senai.sp.cpf138.RestaGuide.rest.UsuarioRestController;

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
			
			if(uri.startsWith("/api")) {
				// variavel para o token
				String token = null;
				//se for um metodo privado
				if(metodoChamado.getMethodAnnotation(Privado.class) != null) {
					
					try {
						
						//obtem o token da request
					token = request.getHeader("Authorization");
					
					//algoritimo para descriptografar
					Algorithm algoritmo = Algorithm.HMAC256(UsuarioRestController.SECRET);
					
					// objeto para verificar o token 
					JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioRestController.EMISSOR).build();
					DecodedJWT jwt = verifier.verify(token);
					
					//enviar os dados do payload
					Map<String, Claim> payload = jwt.getClaims();
					System.out.println(payload.get("nome_usuario"));
					return true;
					
					} catch (Exception e) {
						if(token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						}else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						return false;
					}
					
				}
				//quando for API
				return true;
				
			}else {
				
			
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
	}
		return true;
}
}
