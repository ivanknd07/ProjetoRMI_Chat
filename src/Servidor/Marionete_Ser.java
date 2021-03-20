package Servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.logging.Logger;
//import com.sun.org.apache.xerces.internal.dom.AbortException;
import jdk.jfr.Threshold;

/*CLASS MARIONETE MANIPULA E CONTEM TODA LOGICA DO SERVIDOR*/

public class Marionete_Ser {
	//OBJETO LOGGER
	private static  final  Logger  logger = Logger.getLogger(ServerHandler.class);
	
	private static final String CONFIG_FILE = "servir_config.txt";
	
	//CONSTANTE QUE REPRESENTA O NOME DA PROPRIEDA QUE ARMAZENA O NUMERO DA PORTA
	private static final String PROP_PORT = "port";
	
	//PORTA PADRÃO, QUANDO UMA NÃO ESTAR DEFINIDA
	private static final int DEFAULT_PORT = 1909;
	
	private ServerOperationsImpl serverOperations;
	
	//RMI Registry	
	private Registry registro;
	
	//PROPRIEDADES LIDAS DE ARQUIVOS DE CONFIGURAÇÃO
	private  Properties propriedades;
	
	//CONTROLA SE O SERVIDOR ESTÁ INICADO OU NÃO
	private boolean iniciar;
	
	//CONSTRUTOR
	public Marionete_Ser() throws Exception, IOException {
		//LÊ OS DADOS DO ARQUIVO DE CONFIGURAÇÃO
		propriedades = new Properties();
		File file = new  File(CONFIG_FILE);
		
		if(file.exists()) {
			FileInputStream aux = null;
			try {
				aux = new FileInputStream(file);
				propriedades.load(aux);
			} finally {
				if(aux != null) {
					aux.close();
				}
			}
		} else {
			//SE O ARQUIVO NÃO EXISTE, DEFINE A PORTA  COM O VALOR PADRÃO
			propriedades.setProperty(PROP_PORT,String.valueOf(DEFAULT_PORT));
		}
		try {
			//INICIA O RMI REGISTRO
			registro = LocateRegistry.createRegistry(getServerPort());
			
		} catch (RemoteException e) {
			throw new Exception ("Erro ao criar REGISTRO", e);
		}		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int getServerPort() throws RemoteException {
		//LE A PORTA A PARTIR DO OBJETO PROPRIEDADES
		String portaInicio = propriedades.getProperty(PROP_PORT);
		
		//A PORTA DEVE SER SIDO DEFINIDA
		if(portaInicio == null) {
			throw new Exception("Porta do servidor  não definida");
		}
		try {
			// PORTA DEVE PODER SER CONVERTIDA PARA UM NUMERO INTEIRO
			int  port = Integer.parseInt(portaInicio);
			
			//A PORTA DEVE ESTAR NUM INTERVALO ENTRE 1 E 65635
			if(port < 1 || port > 65635) {
				throw new  Exception ("A porta não está em um intervalo valido");
			}
			//RETORNA A PORTA LIDA
			return port;
		} catch (NumberFormatException e) {
			throw new Exception("A porta não e um numero valido");
		}
		return 0;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public int inicioServer() throws RemoteException {
		try {
		logger.info("Servidor foi iniciado..");
		
		//CRIA O OBJETO REMOTO
		serverOperations = new ServerOperationsImpl();
		
		//REGISTRA O OBJETO RMI REGISTRO
		registro.bind(serverOperations.SERVER_OBJ_NAME, serverOperations);
		
		//MARCA O SERVIDOR COMO INICIADO
		iniciar = true;
		
		//RETORNA A PORTA DO SERVIDOR
		return getServerPort();
	} catch (Exception e) {
		throw new Exception ("Erro ao iniciar o servidor", e);
	}
	
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////
	public void  stopServer() throws RemoteException {
		try {
			//SE O SERVIDOR NÃO ESTÁ MAIS EXCUTANDO, NÃO FAZ NADA
			if(!iniciar) {
				return;
			}
			
			logger.info("SERVIDOR FOI PARADO");
			
			//REMOVE O OBJETO REMOTO DO RMI REGISTRY
			registro.unbind(serverOperations.SERVER_OBJ_NAME);
			
			//OBTEM O CONJUNTO DE CLIENTES CONECTADOS AO SERVIDOR
			Set<ClienteInfo> clientes = serverOperations.getCliente();
			
			//INTERA SOBRE  CADA CLIENTE
			for(ClienteInfo clienteInfo : clients) {
				//OBTEM O OBJETO DE CALLBACK, QUE PERMITE SE COMUNICAR COM O CLIENTE
				final ClientCallback callback = clientInfo.getCAllback();
				
				//AVISA O CLIENTE UTILIZANO UMA NOVA THRED. ISTO EVITA QUE O SERVIDOR
				//ESPERANDO QUE O CLIENTE PROCESSE ESTA INFORMAÇÃO
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							//AVISA O CLIENTE QUE O SERVIDOR ESTAR PRESTES A TERMINAR
							callback.onServerShutdown();
						} catch (RemoteException e) {
							e.printStackTrace();
							
						}
						
					}
				}).start();
				
			}
			
			// REMOVE TODO OS CLIENTES DA LISTA DE CLIENTES
			clients.clear();
			
			//MARCA O SERVIDOR COMO PARADO
			iniciar = false;
				
			} catch (Exception e) {
				throw new RemoteException("Erro ao para o servidor ", e);
			}
			
		}
	}
///////////////////////////////////////////////////////////////////////////////////

