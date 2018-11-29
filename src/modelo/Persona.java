package modelo;

import java.time.LocalDate;
import java.util.Random;

public class Persona {

	private String nif;
	private String nombre;
	private char genero;
	private LocalDate fecha_nac;
	private int longitudPaso;
	private Persona[] hijos;
	private Persona madre;
	private Persona padre;

	public Persona() {
		super();
		this.nif = "54134207G";
		this.nombre = "Kilian Castellano";
		this.fecha_nac = LocalDate.now();
		this.longitudPaso = 57;
	}

	public Persona(String nif, String nombre, int longitudPaso, char genero, Persona madre, Persona padre) {
		super();
		this.nif = nif;
		this.nombre = nombre;
		this.fecha_nac = null;
		// this.fecha_nac = fecha_nac;
		this.longitudPaso = longitudPaso;
		this.genero = genero;
		this.madre= madre;
		this.padre=padre;
	}

	public int caminar(int numPasos) {

		double a = 9.0;
		double raiz = Math.sqrt(a);

		Random miRamdom = new Random();
		int valor = miRamdom.nextInt();

		return numPasos * longitudPaso / 100;
	}

	

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public Persona getMadre() {
		return madre;
	}

	public void setMadre(Persona madre) {
		this.madre = madre;
	}

	public Persona getPadre() {
		return padre;
	}

	public void setPadre(Persona padre) {
		this.padre = padre;
	}

	public Persona[] getHijos() {
		return hijos;
	}

	public void setHijos(Persona[] hijos) {
		this.hijos = hijos;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(LocalDate fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public int getLongitudPaso() {
		return longitudPaso;
	}

	public void setLongitudPaso(int longitudPaso) {
		this.longitudPaso = longitudPaso;
	}
}
