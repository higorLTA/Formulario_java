package br.senai.sp.cpf138.RestaGuide.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
public class FirebaseUtil {

	//variável para guardar as credenciais do Firebase
	private Credentials credenciais;
	
	//variavel para acessar o storege
	private Storage storege;
	
	//constante para o nome do buket
	private final String BUKET_NAME ="HospedagemGuide.appsport.com";
	
	//contante para o sufixo da URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/guide-lanchonete.appspot.com/o/";
	
	//constante para o suffix da URL
	private final String SUFFIX = "?alt=media";
	
	//constante para a url
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FirebaseUtil(){
		//buscar as credenciais (arquivo JSON)
		Resource resource = new ClassPathResource("Chave-secreta-hospedagem.json");
		
		//ler o arquivo para obter as credenciais
		try {
		credenciais = GoogleCredentials.fromStream(resource.getInputStream());
		//acessa o serviço de storage
		storege = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
		} catch (IOException e) {
		throw new RuntimeException(e.getMessage());
		}	
		
	}
	
	private String getExtensao(String nomeArquivo) {
		//retorna o trecho da string que vai do ultimo ponto até o fim 
		return nomeArquivo.substring(nomeArquivo.lastIndexOf(','));
	}
	
	private String uploadFile(MultipartFile arquivo) {
		//gera uma String aleatoria pa o nome do arquivo 
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		
		return nomeArquivo;
		
	}
	
}
