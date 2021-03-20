package Servidor;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.tools.javac.comp.Flow;

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
		
	/*CRIA UM PAINEL PARA OS BOTOES, ULTILIZANDO UM FLOWLAYOUT*/	
		JPanel buttonPanel = new JPanel (new FlowLayout(FlowLayout.CENTER));
		
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

