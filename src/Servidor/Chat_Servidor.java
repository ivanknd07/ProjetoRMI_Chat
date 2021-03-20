package Servidor;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.management.MBeanServerInvocationHandler;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jdk.internal.org.objectweb.asm.Handle;

//import com.sun.tools.javac.comp.Flow;

//import com.sun.security.ntlm.Server;

/* Interface grafica do servidor */
public class Chat_Servidor extends JFrame implements ActionListener, WindowListener {
	
	/*BOTAO PARA INICAR O SERVIDOR*/
	private JButton botaoStart;
	
	/*BOTAO PARA O SERVIDOR*/
	private JButton botaoStop;
	
	/*BARRA DE STATUS */
	private JLabel botaoStatus;
	
	/*LOGICA DE OPERAÇAO DO SERVIDOR*/
//	private ServerHandler handler;
	
	/*CONSTRUTOR*/
	public Chat_Servidor () {
		
		super("Servidor de Chat");
		
	/*DEFINE SE A JANELA FOR FECHADA, APLICAÇÃO FINALIZA*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	/*NÃO PERMITE O REDIRECIONAMENTO DA JANELA*/	
		setResizable(false);
		
	/*REGISTRA UM LISTENER PARA OS EVENTOS DE JANELA*/
		addWindowListener(this);
		
	/*DEFINE O TAMANHO DA JANELA, EM PILXES*/
		setSize(250,100);
		
	/*CENTRALIZA A JANELA NA TELA*/	
		UIUtils.centerWindon(this);
		
	/*DEFINE O LAYOUT COMO BORDER LAYOUT*/	
		setLayout(new BorderLayout());
		
	/*CRIA UM PAINEL PARA OS BOTÃO, ULTILIZANDO UM FLOWLAYOUT*/	
		JPanel buttonPanel = new JPanel (new FlowLayout(FlowLayout.CENTER));
		
	/*CRIANDO O BOTAO INICIAR E ADICIONAR AO PAINEL*/
		botaoStart = new JButton("Iniciar");
		botaoStart.addActionListener(this);
		buttonPanel.add(botaoStart);
		
		
	/*CRIANDO O BOTAO 'PARAR' E ADICIONAR AO PAINEL*/	
		botaoStop = new JButton("Stop");
		botaoStop.addActionListener(this);
		botaoStop.setEnabled(false);
		buttonPanel.add(botaoStop);
		
	/*ADICIONA O PANEL DE BOTOES NO CENTRO DA JANELA*/	
		add(BorderLayout.CENTER,buttonPanel);
		
	/*CRIA A BARRA DE STATUS SEM TEXTO E ADICIONA AO PAINEL*/	
		botaoStatus = new JLabel(" ");
		
	/*ULTILIZAÇÃO DE UMA BORDA DESTE TIPO DÁ UM EFEITO DE PROFUNDIDADE AO COMPONENTE*/
		botaoStart.setBorder(BorderFactory.createLoweredBevelBorder());
		add(BorderLayout.SOUTH,botaoStart);
		
		try {
			//CRIA O SERVER DANDLER
			Handler = new ServerHandler();
			
		} catch (Exception e) {
			UIUtilis.displayException(this, e);
		}
		
		/*EXIBE A JANELA*/
		setVisible(true);
	}

	//@Override
	public void  verificacao (ActionEvent evento) {
		try {
			if(evento.getSource() == botaoStart) {
				// HOUVE UM CLIQUE  NO BOTAO "INICAR"
				botaoStart.setEnabled(false);
				botaoStop.setEnabled(true);
				
				//INICIA O SERVIDOR. ESTE METODO RETORA A PORTA O SERVIDOR FOI ABERTO
				int porta = handler.start.Server();
				
				//ESCREVE A MENSAGEM NA BARRA DE STATUS
				botaoStatus.setText("Servidor iniciado na porta " + porta);				
			} else if (evento.getSource() == botaoStop) {
				// HOUVE UM CLIQUE NO BOTAO "PARAR"
				
				//HABILITA O BOTAO 'INICIAR' A DESABILITAR BOTAO 'PARAR'
				botaoStart.setEnabled(true);
				botaoStop.setEnabled(false);
				
				//PARA O SERVIDOR
				handler.stopServer();
				
				//ESCREVE A MENSAGEM NA BARRA DE STATUS
				botaoStatus.setText("Servidor  parado");
			}
			} catch /*(ChatException*/ (Exception e) {
				UIUtils.displayException(this, e);
			}
		}
//@Override
	public void  windowFechar(WindowEvent evento) {
		try {
			if(handler  != null) {
				handler.stopServer();
			}
		} catch (/*ChatException*/Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

