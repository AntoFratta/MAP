import data.Data;
import data.OutOfRangeSampleSize;
import mining.KMeansMiner;

import java.io.*;
import java.net.Socket;

public class ServerOneClient extends Thread {
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	KMeansMiner kmeans;

	public ServerOneClient(Socket s) throws IOException {
		this.socket = s;
		this.in = new ObjectInputStream(socket.getInputStream());
		this.out = new ObjectOutputStream(socket.getOutputStream());

		start();
	}

	public void run() {
		while (true) {
			System.out.println("Client connesso.");
			ObjectInputStream inF = null;
			Object nomeTable = null;
			Data data = null;
			try {
				while (true) {
					Object scelta = in.readObject();
					int opzione = (int) scelta;
					switch (opzione) {
					case 3:
						nomeTable = in.readObject();
						Object numCl = in.readObject();
						String fileName = nomeTable + "_" + numCl + ".dat";
						inF = new ObjectInputStream(new FileInputStream("Save\\" + fileName));
						out.writeObject("OK");
						kmeans = new KMeansMiner("Save\\" + fileName);
						Object response = kmeans.getC().toString();
						out.writeObject(response);
						break;
					case 0:
						nomeTable = in.readObject();
						try {
							data = new Data((String) nomeTable);
							out.writeObject("OK");
						} catch (Exception e) {
							out.writeObject("NO");
						}
						break;
					case 1:
						numCl = in.readObject();
						if ((int) numCl > 0 && (int) numCl < 15) {
							KMeansMiner kmeans = new KMeansMiner((int) numCl);
							kmeans.kmeans(data);
							out.writeObject("OK");
							out.writeObject(kmeans.getC().toString(data));
							if ((int) in.readObject() == 2) {
								fileName = nomeTable + "_" + numCl + ".dat";
								kmeans.salva("Save\\" + fileName);
								out.writeObject("OK");
							}
						} else {
							out.writeObject("NO");
						}
						break;
					default:
						break;
					}
				}
			} catch (OutOfRangeSampleSize e) {
				throw new RuntimeException(e);
			} catch (FileNotFoundException e) {
				System.out.println("File inesistente");
				try {
					out.writeObject("Errore");
				} catch (Exception e2) {
					System.out.println("Errore");
				}
			} catch (IOException e) {
				System.out.println("Client disconnesso");
				try {
					in.close();
					out.close();
					socket.close();
					break;
				} catch (Exception e2) {
					System.out.println("Errore chiusura risorse");
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
