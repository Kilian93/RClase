package modelo;

public class Equipo {
	
	private int id;
	private String nombreCorto;
	private String nombreLargo;
	
	
	public Equipo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Equipo(int id, String nombreCorto, String nombreLargo) {
		super();
		this.id = id;
		this.nombreCorto = nombreCorto;
		this.nombreLargo = nombreLargo;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	public String getNombreLargo() {
		return nombreLargo;
	}
	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

}