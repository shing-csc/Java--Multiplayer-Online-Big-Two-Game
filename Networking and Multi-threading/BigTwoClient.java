import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;




/**
 * The BigTwoClient class implements the NetworkGame interface. It is used to model a Big 
Two game client that is responsible for establishing a connection and communicating with 
the Big Two game server. Below is a detailed description for the BigTwoClient class.
 * @author danie
 *
 */
public class BigTwoClient implements NetworkGame{

	/**
	 * a constructor for creating a Big Two client. 
	 * @param game: a reference to a BigTwo object associated with this client
	 * @param gui: a reference to a BigTwoGUI object associated the BigTwo object
	 */
	public BigTwoClient(BigTwo game, BigTwoGUI gui) {
		this.game = game;
		this.gui = gui;
		
		setServerIP("127.0.0.1");
		setServerPort(2396);
		
		name = JOptionPane.showInputDialog("Your name: ");
		this.setPlayerName(name);
		
	}
	private BigTwo game;
	private BigTwoGUI gui;
	private Socket sock;
	private ObjectOutputStream oos;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private String name;
	private int SleepingTime = 1000;
	private CardGameMessage message;
	public CardGameMessage join1;
	public Thread rThread; //Thread used for communication
	private String[] names;
	private String[] names1;
	
	
	@Override
	/**
	 * a method for getting the playerID (i.e., index) of the local player
	 */
	public int getPlayerID() {
	
		return this.playerID;
	}

	@Override
	/**
	 * a method for setting the playerID (i.e., index) of 
the local player. This method should be called from the parseMessage() method when a 
message of the type PLAYER_LIST is received from the game server. 
	 */
	public void setPlayerID(int playerID) {
		// TODO Auto-generated method stub
		this.playerID = playerID;
	}

	@Override
	/**
	 * a method for getting the name of the local player
	 */
	public String getPlayerName() {
		
		return this.playerName;
	}

	@Override
	/**
	 * a method for setting the name of the local player. 
	 */
	public void setPlayerName(String playerName) {
		// TODO Auto-generated method stub
		this.playerName = playerName;
	}

	@Override
	/**
	 * a method for getting the IP address of the game server.

	 */
	public String getServerIP() {
		// TODO Auto-generated method stub
		return this.serverIP;
	}

	@Override
	/**
	 * a method for setting the IP address of the game server.
	 */
	public void setServerIP(String serverIP) {
		// TODO Auto-generated method stub
		this.serverIP = serverIP;
	}

	@Override
	/**
	 * a method for getting the TCP port of the game server
	 */
	public int getServerPort() {
		// TODO Auto-generated method stub
		return this.serverPort;
	}

	@Override
	/**
	 * a method for setting the TCP port of the game server
	 */
	public void setServerPort(int serverPort) {
		// TODO Auto-generated method stub
		this.serverPort = serverPort;
	}


	//BELOW ARE THE ONES THAT REALLY NEED TO BE CODED
	/**
	 * an inner class that implements the Runnable interfa
	 * @author danie
	 *
	 */
	private class ServerHandler implements Runnable{
	
		private ObjectInputStream ois = null;
		private GameMessage p_message = null;
		private CardGameMessage n_message = null;
		
		public ServerHandler(Socket sock) {
			try {
				ois = null;
				ois = new ObjectInputStream(sock.getInputStream());
				
			}catch(Exception ex){ex.printStackTrace();}
		}
		
		@Override
		/**
		 * create a thread 
with an instance of this class as its job in the connect() method from the NetworkGame 
interface for receiving messages from the game server
		 */
		public void run() {
			// TODO Auto-generated method stub
			try {
				p_message = (GameMessage) ois.readObject();
				n_message = (CardGameMessage) p_message;
				
				while(n_message != null) {
	
					parseMessage(message);
				}
			}catch (Exception ex) {ex.printStackTrace();}
			
		}	
	}
	@Override
	/**
	 * a method for making a socket connection with the game server.
Upon successful connection, you should (i) create an ObjectOutputStream for sending
messages to the game server; (ii) create a new thread for receiving messages from the 
game server
	 */
	public void connect() {
		// TODO Auto-generated method stub
		try {
			sock = new Socket("127.0.0.1",2396);
			
			OutputStream temp1 = sock.getOutputStream();
			oos = new ObjectOutputStream(temp1);
			
			
			//Send what messages to the server?
			rThread = new Thread(new ServerHandler(sock));
			rThread.start();
			
			
		}catch (Exception ex) { ex.printStackTrace(); }
		
	}

	@Override
	/**
	 * a method for sending the specified 
message to the game server. This method should be called whenever the client wants to 
communicate with the game server or other clients
	 */
	public synchronized void sendMessage(GameMessage message) {
		// TODO Auto-generated method stub
		try {
			oos.writeObject(message);
			Thread.sleep(this.SleepingTime);
		
		}catch (Exception ex) { ex.printStackTrace(); }
		
	}
	
	
	@Override
	/**
	 * a method for parsing the messages 
received from the game server. This method should be called from the thread 
responsible for receiving messages from the game server. Based on the message type, 
different actions will be carried out (please refer to the general behavior of the client 
described in the previous section).

	 */
	public synchronized void parseMessage(GameMessage message) {
		CardGameMessage c;
		c = (CardGameMessage)message;
		
		
		if (c.getType() == CardGameMessage.PLAYER_LIST) {
			
			this.setPlayerID(playerID);
			String temp[] = (String[]) c.getData();
			game.setName(temp);
			
			this.sendMessage(new CardGameMessage(1, -1, temp));
			//Can I just use 1 to represent JOIN?
		}//Value 0
		else if (c.getType() == CardGameMessage.JOIN) {
			
			names = game.getName();
			String temp2 = (String) c.getData();
			
			names[this.playerID] = temp2;
			game.setName(names);
			
			if (message.getPlayerID() == playerID) {
				gui.go();
				this.sendMessage(new CardGameMessage(4, -1, null));
			}
			
			if (gui==null) {
				return;
			}
			
			gui.repaint();
			
			
		}//Value 1
		else if (c.getType() == CardGameMessage.FULL) {
			
			setPlayerID(-1);
			gui.clearMsgArea();
			
			gui.printMsg("THE GAME IS FULL ALREADY!!");
			gui.printMsg("NO ONE CAN JOIN NOW!!");
			
			gui.disable();
			
			
		}//Value 2
		else if (c.getType() == CardGameMessage.QUIT) {
			try {
				
				Thread.sleep(SleepingTime);
				
				names1 = game.getName();
				
				names1[c.getPlayerID()] = "";
				game.setName(names1);
				
			}catch (Exception ex) { ex.printStackTrace(); }
			
			//Disable and repaint the gui and ready for the game if gui is not null
			if (gui!= null) {
				gui.disable();
				gui.repaint();
				this.sendMessage(new CardGameMessage(3, -1, null));
			}
			
			
			
		}//Value 3
		
		else if (c.getType() == CardGameMessage.READY) {
			try {
				
				Thread.sleep(this.SleepingTime);
				gui.printMsg("Player "+ game.getName()[this.playerID] + " ("+ this.playerID + ") is ready!");
				
			}catch (Exception ex) { ex.printStackTrace(); }
			
			
		}//Value 4
		else if (c.getType() == CardGameMessage.START) {
			
			Deck d = (Deck)c.getData();
			game.start(d);
		
		}//Value 5
		else if (c.getType() == CardGameMessage.MOVE) {
			try {
				Thread.sleep(this.SleepingTime);
				
				int array1[] = (int[])c.getData();
				
				game.checkMove(playerID, array1);
			}
			catch (Exception ex) { ex.printStackTrace(); }
			
		}//Value 6
		else if (c.getType() == CardGameMessage.MSG) {
			
			//Printing the message in the chatbox
			String temp = (String)c.getData();
			gui.printChatMsg(temp);
			
		}//Value 7
		
		
		
	}

}
