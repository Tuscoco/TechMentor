package model;

public class Ponto {
    
    private int id_monitor;
	private String data;


	public Ponto(int id_monitor, String data) {

		this.id_monitor = id_monitor;
		this.data = data;

	}


	public int getIdMonitor() {
		return id_monitor;
	}

	public String getData() {
		return data;
	}
    
}
