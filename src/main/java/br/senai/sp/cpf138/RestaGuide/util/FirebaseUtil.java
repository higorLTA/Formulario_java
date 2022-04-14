package br.senai.sp.cpf138.RestaGuide.util;

import java.io.IOException;

import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FirebaseUtil {

	//variável para guardar as credenciais do Firebase
	private Credentials credenciais;
	
	//variavel para acessar o storege
	private Storage storege;
	
	//constante para o nome do buket
	private final String BUCKET_NAME ="hospedagemguide.appspot.com";
	
	//contante para o sufixo da URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/hospedagemguide.appspot.com/o/";
	
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
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
	public String uploadFile(MultipartFile arquivo) throws IOException {
		//gera uma String aleatoria pa o nome do arquivo 
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);

		//criar um blobinfo a partir do blobid
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		//manda o blobinfo para o storage passando os bytes do arquivo
		storege.create(blobInfo, arquivo.getBytes());
		
		return String.format(DOWNLOAD_URL, nomeArquivo);
		
	}
	
	//metodo para excluir a foto do firebase
	public void deletar(String nomeArquivo) {
		//Retira o prefixo e o sufixo do arquivo
		nomeArquivo = nomeArquivo.replace(PREFIX, "").replace(SUFFIX, "");
		//pega um blob através do nome do arquivo
		Blob blob = storege.get(BlobId.of(BUCKET_NAME, nomeArquivo));
		// deleta o arquivo
		storege.delete(blob.getBlobId());
	}
	
}
