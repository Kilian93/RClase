package auxiliar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import modelo.Equipo;
import modelo.Estudiante;
import modelo.Jugador;
import modelo.Partido;
import modelo.Persona;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;

public class Ejercicios {

	// Sgundo trimestre
	
	// 06 abril 2019
	public HashMap<String, ArrayList<Jugador>> crearJugadoresPorEquipo() {
		HashMap<String, ArrayList<Jugador>> mapaListadoJugadoresEquipos = new HashMap<String, ArrayList<Jugador>>();
		HashMap<String, Equipo> mapaEquipos = crearMapaEquipos("ficheros/equipos.txt");
		HashMap<Integer, Jugador> mapaJugadores = crearMapaJugadores2("ficheros/jugadores.txt");
		Set<String> clavesEquipos = mapaEquipos.keySet();
		for (String unaClaveEquipo : clavesEquipos) {
			Equipo valoresEquipo = mapaEquipos.get(unaClaveEquipo);
			String nombreLagoEquipo = valoresEquipo.getNombre();
			mapaListadoJugadoresEquipos.put(nombreLagoEquipo, new ArrayList<Jugador>());
		}
		
		Collection<Jugador> valoresMapaJugador = mapaJugadores.values(); 
		for (Jugador unJugador : valoresMapaJugador) {
			int idEquipoJugador = unJugador.getCodigoEquipo();
			String nombreLagoEquipo = null;
			Collection<Equipo> valoresMapaEquipo = mapaEquipos.values();
			for (Equipo unEquipo : valoresMapaEquipo) {
				int idEquipo = unEquipo.getId();
				if(idEquipo == idEquipoJugador) {
					nombreLagoEquipo = unEquipo.getNombre();
					break;
				}
			}
			mapaListadoJugadoresEquipos.get(nombreLagoEquipo).add(unJugador);
		}
		
		return mapaListadoJugadoresEquipos;
	}
	

	// 28 marzo 2019

	public String leerRegistro(BufferedReader fichero) throws IOException {

		String registro = fichero.readLine();
		if (registro == null) // se ha alcanzado fin del fichero
			return "z";

		return registro;

	}

	public void meclarFicherosOrdenados(String rutaF1, String rutaF2, String rutaF3) {
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaF1));
			BufferedReader fichero2;
			fichero2 = new BufferedReader(new FileReader(rutaF2));
			BufferedWriter fichero3;
			fichero3 = new BufferedWriter(new FileWriter(rutaF3));
			String registro1 = leerRegistro(fichero);
			String registro2 = leerRegistro(fichero2);
			while (!registro1.contentEquals("z") || !registro2.contentEquals("z")) {

				String k1 = registro1.split("#")[0];
				String k2 = registro2.split("#")[0];

				// if (Integer.parseInt(campos) > Integer.parseInt(campos1))
				if ((k1.compareTo(k2)) < 2) {
					fichero3.write(registro1 + "\n");
					registro1 = leerRegistro(fichero);

				} else {
					fichero3.write(registro2 + "\n");
					registro2 = leerRegistro(fichero2);
				}

			}
			fichero.close();
			fichero2.close();
			fichero3.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

	}

	// 13 marzo 2019

	public void ordenarMapaJufadoresPorNombre(HashMap<String, Jugador> nombreJugadores) {
		Set<Entry<String, Jugador>> set = nombreJugadores.entrySet();
		List<Entry<String, Jugador>> listado = new ArrayList<Entry<String, Jugador>>(set);
		Collections.sort(listado, new Comparator<Map.Entry<String, Jugador>>() {
			public int compare(Map.Entry<String, Jugador> jugador1, Map.Entry<String, Jugador> jugador2) {
				return (jugador1.getValue().getNombre()).compareTo(jugador2.getValue().getNombre());
			}
		});

		for (Map.Entry<String, Jugador> entry : listado) {
			System.out.println(entry.getKey() + " ==== " + entry.getValue());
		}
	}

	public ArrayList<Jugador> ordenarListaJugadores(String rutaFichero) {
		ArrayList<Jugador> lista = crearListaJugadores("ficheros/jugadores2.txt");
		lista.sort(new Comparator<Jugador>() {

			/*
			 * @Override public int compare(Equipo eq1, Equipo eq2) {
			 * 
			 * return eq1.getNombreLargo().compareTo(eq2.getNombreLargo()); }
			 */
			public int compare(Jugador ju1, Jugador ju2) {

				if (ju1.getDorsal() < ju2.getDorsal()) {
					return 1;
				} else if (ju1.getDorsal() > ju2.getDorsal())
					return -1;
				else
					return 0;
			}

		});

		return lista;
	}

	public ArrayList<Jugador> crearListaJugadores(String rutaFichero) {
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaFichero));
			String registro;
			Jugador jugador = null;
			ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				// romper la cadena registro
				for (int i = 0; i < campos.length; i++)
					System.out.print(campos[i] + " , ");
				System.out.println("");

				jugador = new Jugador(campos[1], Integer.parseInt(campos[0]), Integer.parseInt(campos[2]),
						Integer.parseInt(campos[3]));

				jugadores.add(jugador);
			}

			fichero.close();
			System.out.println("Fin de la lectura del fichero");
			return jugadores;

		} catch (FileNotFoundException e) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");

		}

		return null;
		// crear la lista vacia
		// recorrer secuencialmente el fichero
		// crear el objeto Jugador por cada registro del fichero
		// a�adir jugador a la lista

		// al terminar el fichero, devolver la lista
		//

	}

	
	public HashMap<Integer, Jugador> crearMapaJugadores2(String rutaFichero) {
		try {
			HashMap<Integer, Jugador> mapaJugadores = new HashMap<Integer, Jugador>();
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaFichero));
			String registro = " ";
			int idEquipoExistente = 0;

			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				ArrayList<Jugador> listadoJugadores;
				int idJugador = Integer.parseInt(campos[0]);
				String nif = campos[1];
				String nombre = campos[2];
				int longitudPaso = Integer.parseInt(campos[3]);
				String fecha = campos[4];
				char sexo = campos[5].charAt(0);
				int dorsal = Integer.parseInt(campos[6]);
				int idEquipo = Integer.parseInt(campos[7]);

				Jugador unJugador = new Jugador(nif, nombre, fecha, longitudPaso, sexo, idJugador, dorsal, idEquipo);
				mapaJugadores.put(idJugador, unJugador);
				System.out.println();

			}
			fichero.close();
			System.out.println("Fin de la lectura del fichero");
			return mapaJugadores;
		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");
		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
		return null;
	}
	
	
	public HashMap<String, Jugador> crearMapaJugadores(String rutaFichero) {
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaFichero));
			String registro;
			Jugador jugador = null;
			HashMap<String, Jugador> jugadores = new HashMap<String, Jugador>();
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");

				jugador = new Jugador(campos[2], Integer.parseInt(campos[0]), Integer.parseInt(campos[6]),
						Integer.parseInt(campos[7]));
				jugadores.put(campos[6], jugador);
			}

			fichero.close();
			System.out.println("Fin de la lectura del fichero");
			return jugadores;
		} catch (FileNotFoundException e) {
			System.out.println("fichero no encontrado");
		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}

		return null;

	}

	// 12 marzo 2019

	public Equipo buscarEquipo(String equipo, ArrayList<Equipo> equipos) {
		Equipo resultado;
		for (Equipo equipo1 : equipos) {
			if (equipo1.getNombreCorto().equals(equipo))
				;
			return equipo1;
		}
		System.out.println("algo falla");
		return null;
	}

	public int busquedaBinaria(int[] pajar, int aguja) {
		int izq = 0;
		int der = pajar.length - 1;
		while (izq < der) {
			int med = ((der - izq) / 2) + izq;

			if (pajar[med] == aguja) {
				System.out.println("Encontrado " + aguja + " en Posicion " + med);
				return med;
			} else if (aguja < pajar[med])
				der = med - 1;
			else if (aguja < pajar[med])
				der = med + 1;

		}
		System.out.println("No Encontrado" + aguja);
		return -1;

	}

	// modificar craerMapaEquipos para que devuelva una lista
	// ArrayList<Equipo>
	// El fichero a leer se llama equipo.txt

	// corregir crearMapaEquipos2

	public HashMap<String, ArrayList<Equipo>> crearMapaEquipos2_1(String rutaFichero) {
		try {
			HashMap<String, ArrayList<Equipo>> mapaListaEquipos = new HashMap<String, ArrayList<Equipo>>();
			ArrayList<Equipo> listaEquipo = new ArrayList<Equipo>();
			BufferedReader fichero = new BufferedReader(new FileReader(rutaFichero));
			String registro;
			Equipo equipo = null;
			String claveMapa = "";
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				equipo = new Equipo(Integer.parseInt(campos[0]), campos[1], campos[2]);
				// equipos.put(campos[1], equipo.getNombreLargo());
				listaEquipo.add(equipo);
				for (int i = 0; i < listaEquipo.size(); i++) {
					if (listaEquipo.get(i).getNombreCorto() == campos[1]) {
						claveMapa = listaEquipo.get(i).getNombreCorto();
						break;
					}

				}
				if (!mapaListaEquipos.containsKey(claveMapa)) {
					mapaListaEquipos.put(claveMapa, new ArrayList<Equipo>());
				}

				mapaListaEquipos.get(claveMapa).add(equipo);
			}
			fichero.close();
			System.out.println("Fin de la lectura del fichero");
			return mapaListaEquipos;

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
		return null;
	}

	/*
	 * public ArrayList<Equipo> crearMapaEquipos2(String rutaFichero) { try {
	 * ArrayList<Equipo> listaEquipo = new ArrayList<Equipo>(); BufferedReader
	 * fichero; String registro; Equipo equipo = null; fichero = new
	 * BufferedReader(new FileReader(rutaFichero)); while ((registro =
	 * fichero.readLine()) != null) { String[] campos = registro.split("#"); equipo
	 * = new Equipo(Integer.parseInt(campos[0]), campos[1], campos[2]);
	 * listaEquipo.add(equipo); } fichero.close();
	 * System.out.println("Fin de la lectura del fichero"); return listaEquipo;
	 * 
	 * } catch (FileNotFoundException e) {
	 * System.out.println("fichero no encontrado"); } catch (IOException e) {
	 * System.out.println("IO Excepcion"); }
	 * 
	 * return null;
	 * 
	 * }
	 */

	// 21 de febrero 2019

	// metodo para mostrar ficheroObjetosEquipos

	public void mostrarFicheroObjetoEquipo(String rutaEquipos) {
		FileInputStream fis = null;
		ObjectInputStream lecturaFicheroObjeto = null;
		try {
			fis = new FileInputStream("ficheros/equipos.obj2" /* rutaEquipos */);
			lecturaFicheroObjeto = new ObjectInputStream(fis);
			int i = 0;
			// fis es el acceso al fichero, el metodo available pregunta cuantos bytes
			// quedan por leer
			// de datos en el fichero, cuando llegue a 0 byte daria false en la comparacion.
			while (fis.available() > 0) {
				Equipo unEquipo = (Equipo) lecturaFicheroObjeto.readObject();
				System.out.println(i + " Id:" + unEquipo.getId() + " -- " + unEquipo.getNombreCorto() + " -- "
						+ unEquipo.getNombre());
				i++;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error FileNotFound " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error I/O " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Error ClassNotFound " + e.getMessage());
		} finally {
			try {
				lecturaFicheroObjeto.close();
			} catch (IOException e) {
				System.out.println("Error I/O finally" + e.getMessage());
			}
		}
	}

	public void mostrarFicheroObjetosEquipos(String rutaFichero) {

		ObjectInputStream objetos = null;
		try {
			objetos = new ObjectInputStream(new FileInputStream(rutaFichero));
			int i = 0;
			while (true) {
				Equipo equipo = (Equipo) objetos.readObject();
				System.out.println(i + " Id:" + equipo.getId() + " -- " + equipo.getNombreCorto() + " -- "
						+ equipo.getNombre());
				i++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("fichero no encontrado");
		} catch (ClassNotFoundException e) {
			System.out.println("clase no encontrada");
		} catch (IOException e) {
			System.out.println("IO Excepcion");
		} finally {
			try {
				objetos.close();
			} catch (IOException e) {
				System.out.println("IO Excepcion");
			}
		}
	}

	// metodo para leer un fichero de objetos
	public void leerFicheroObjeto(String rutaFichero) {

		// ObjectInputStream objetos = new ObjectInputStream(new
		// FileInputStream(rutaFichero));
		try {
			FileInputStream fIs = new FileInputStream(rutaFichero);
			ObjectInputStream fObj = new ObjectInputStream(fIs);
			while (fIs.available() > 0) {

				Equipo equipo = (Equipo) fObj.readObject();
				System.out.println(equipo.getNombre());

			}
			fIs.close();
			fObj.close();
		} catch (FileNotFoundException e) {
			System.out.println("fichero no encontrado");
		} catch (ClassNotFoundException e) {
			System.out.println("clase no encontrada");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// metodo para leer fichero de objetos equipos

	public void leerObjetosEquipos() {
		ObjectInputStream objetos = null;
		try {
			objetos = new ObjectInputStream(new FileInputStream("ficheros/equipos.obj"));

			while (true) {

				Equipo equipo = (Equipo) objetos.readObject();
				System.out.println(equipo.getNombre());
			}

		} catch (FileNotFoundException e) {
			System.out.println("error1");
		} catch (IOException e) {
			System.out.println("Fin de la lectura");
			try {
				objetos.close();
			} catch (IOException e1) {

			}

		} catch (ClassNotFoundException e) {
			System.out.println("clase no encontrada");

		} catch (java.lang.ClassCastException e) {
			System.out.println("Casting imposible");
		}

	}

	public void creaFicheroObjetoEquipos2() {

		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader("ficheros/equipos.txt"));
			FileOutputStream salida = new FileOutputStream("ficheros/equipos.obj");
			ObjectOutputStream objetos = new ObjectOutputStream(salida);

			String registro;

			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				Equipo equipo = new Equipo(Integer.parseInt(campos[0]), campos[1], campos[2]);

				equipo.setGc(0);
				equipo.setGf(0);
				equipo.setPe(0);
				equipo.setPg(0);
				equipo.setPp(0);
				objetos.writeObject(equipo);

			}

			fichero.close();
			System.out.println("Fin de la lectura del fichero");

		} catch (FileNotFoundException e) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");

		}
	}

	public void creaFicheroObjetoEquipos(String rutaDestinoObjeto) {
		try {
			FileOutputStream salida = new FileOutputStream(rutaDestinoObjeto);
			ObjectOutputStream objetos = new ObjectOutputStream(salida);
			// recorre equipos.txt, creando objetos equipo
			HashMap<String, Equipo> mapaEquipos = crearMapaEquipos("ficheros/equipos.txt");
			Set<String> clavesMapaEquipos = mapaEquipos.keySet();
			// y grabandolos en objetos
			for (String claveEquipo : clavesMapaEquipos) {
				Equipo objetoEquipo = mapaEquipos.get(claveEquipo);
				objetos.writeObject(objetoEquipo);
			}
			objetos.close();
			salida.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error FileNotFound " + e.getMessage());

		} catch (IOException e) {

			System.out.println("Error I/O " + e.getMessage());
		}

	}

	// 20 de febrero 2019

	public void grabarTiradasDado(int cuantas) {
		// crear tiradaDado.txt
		// abrir fichero de salida
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter("ficheros/tiradasDado.txt"));
			Random rnd = new Random();
			int acum = 0;
			for (int i = 0; i < cuantas; i++) {

				int numero = 1 + rnd.nextInt(6);
				acum += numero;
				fichero.write(numero + "\n");

			}
			System.out.println();
			System.out.println("Proceso terminado..");
			fichero.close();

		} catch (IOException ex) {
			System.out.println("Error I/O " + ex.getMessage());
		}

	}

	public void entradaTecladoAFichero(String rutaFichero) {

		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter(rutaFichero));

			Scanner teclado = new Scanner(System.in);
			System.out.println("Teclee sus datos.. x|X para terminar");
			String tecleado = teclado.nextLine();
			while ((tecleado = teclado.nextLine()).compareToIgnoreCase("x") != 0) {
				fichero.write(tecleado + "\n");

			}
			fichero.close();
		} catch (IOException ex) {
			System.out.println("Error I/O " + ex.getMessage());
		}
		System.out.println("Fin entrada de datos..");

	}

	// 19 de febrero 2019

	public void muestraClasificacion() {
		JFrame ventana;
		ventana = new JFrame("Clasificacion");

		JPanel panel = new JPanel();
		ventana.add(panel);

		ArrayList<Equipo> equipos = this.generaClasificacion("ficheros/partidos.txt", "ficheros/equipos.txt");
		String[] columnas = { "EQUIPO", "PUNTOS", "PJ", "PG", "PE", "PP", "GF", "GC" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

		modelo.addRow(columnas);
		for (Equipo equipo : equipos) {
			Object[] vector = { equipo.getNombre(), equipo.getPuntos(), equipo.getPg(),
					equipo.getPe(), equipo.getPp(), equipo.getGf(),
					equipo.getGc() };
			modelo.addRow(vector);
		}
		JTable tabla = new JTable(modelo);
		panel.add(tabla);
		ventana.pack();
		ventana.setVisible(true);

	}

	// 13 de febrero 2019

	public Equipo bucasEquipoEnLista(String nombreCorto, ArrayList<Equipo> equipos) {
		Equipo resultado;
		for (Equipo equipo : equipos) {
			if (equipo.getNombreCorto().equals(nombreCorto))
			return equipo;
		}
		System.out.println("Ooops.. algo falla");
		return null;
	}

	public void actualizaEquipos(Partido partido, ArrayList<Equipo> equipos) {

		String nCortoL = partido.geteL();
		String nCortoV = partido.geteV();
		Equipo eL = bucasEquipoEnLista(nCortoL, equipos);
		Equipo eV = bucasEquipoEnLista(nCortoV, equipos);

		// logica del resultado del partido
		if (partido.getgL() > partido.getgV()) {
			eL.setPuntos(eL.getPuntos() + 3);
			eL.setPg(eL.getPg() + 1);
			eV.setPp(eV.getPp() + 1);
		} else if (partido.getgL() < partido.getgV()) {
			eV.setPuntos(eV.getPuntos() + 3);
			eV.setPg(eV.getPg() + 1);
			eL.setPp(eL.getPp() + 1);
		} else {
			eL.setPuntos(eL.getPuntos() + 1);
			eV.setPuntos(eV.getPuntos() + 1);
			eV.setPe(eV.getPe() + 1);
			eL.setPe(eL.getPe() + 1);
		}

		eL.setGf(eL.getGf() + partido.getgL());
		eL.setGc(eL.getGc() + partido.getgV());

		eV.setGf(eV.getGf() + partido.getgV());
		eV.setGc(eV.getGc() + partido.getgL());

		eL.setPj(eL.getPj() + 1);
		eV.setPj(eV.getPj() + 1);

	}

	public Partido creaPartido(String linea) {
		Partido partido = new Partido();

		String[] campos = linea.split("#");
		partido.seteL(campos[2]);
		partido.seteV(campos[4]);

		try {
			partido.setgL(Integer.parseInt(campos[3]));
			partido.setgV(Integer.parseInt(campos[5]));

		} catch (NumberFormatException e) {
			return null;
		}

		return partido;
	}

	public ArrayList<Equipo> generaClasificacion(String rutaPartidos, String rutaEquipos) {
		ArrayList<Equipo> resultado;
		try {
			// crear lista equipos desde fichero equipos.txt
			resultado = crearListaEquipos(rutaEquipos);
			// ArrayList<Equipo> resultado = crearListaEquipos(rutaEquipos);
			//
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaPartidos));
			String registro;
			Partido partido;
			while ((registro = fichero.readLine()) != null) {

				partido = creaPartido(registro);
				if (partido == null) // ultimo partido jugado..
					break;
				// actualiza lista Equipos
				actualizaEquipos(partido, resultado);

			}
			Collections.sort(resultado, null);
			fichero.close();
			return resultado;

			// System.out.println("Fin de la lectura del fichero");

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
		return null;

	}

	// 07 de febrero 2019

	public HashMap<String, ArrayList<String>> tablaPartidos(String rutaFichero) {
		HashMap<String, ArrayList<String>> datos = new HashMap<String, ArrayList<String>>();
		// ArrayList<Equipo> lista = crearListaEquipos("ficheros/equipos.txt");
		HashMap<String, Equipo> nombresLargos = crearMapaEquipos("ficheros/equipos.txt");
		HashMap<String, ArrayList<Integer>> x = resultadosEquipos("ficheros/partidos.txt");
		HashMap<String, Integer> recogerPuntos = muestraPuntosEquipos(x);

		Set<String> clavesMapa = nombresLargos.keySet();

		for (String clave : clavesMapa) {

			ArrayList<String> nuevoDato = new ArrayList<String>();
			// nuevoDato.add(nombresLargos.get(clave));
			// nuevoDato.addAll((Collection<? extends String>) nombresLargos.get(clave));
			nuevoDato.add(Integer.toString(recogerPuntos.get(clave)));
			datos.put(clave, nuevoDato);
		}

		/*
		 * Set<String> claveDatos = datos.keySet(); for (String clave2 : claveDatos) {
		 * System.out.println(clave2 + " [Puntos: " + recogerPuntos.get(clave2) + "]: "
		 * + nombresLargos.get(clave2));
		 * 
		 * }
		 */

		Set<String> claveDatos = datos.keySet();
		for (String clave2 : claveDatos) {

			System.out.println(
					nombresLargos.get(clave2) + " [Puntos: " + recogerPuntos.get(clave2) + "] " + x.get(clave2));

		}

		/*
		 * ArrayList<String>nombres = new ArrayList<String>(); for (Equipo equipo :
		 * lista) {
		 * 
		 * nombres.add(equipo.getNombreLargo()); }
		 */
		return datos;

	}

	// obtener un ArrayList ORDENADO por nombre LARGO del equipo
	// a partir de la lista obtenida del metodo crearListaEquipo

	public ArrayList<Equipo> ordenarListaEquipo(String rutaFichero) {
		ArrayList<Equipo> lista = crearListaEquipos("ficheros/equipos.txt");
		lista.sort(new Comparator<Equipo>() {

			/*
			 * @Override public int compare(Equipo eq1, Equipo eq2) {
			 * 
			 * return eq1.getNombreLargo().compareTo(eq2.getNombreLargo()); }
			 */

			public int compare(Equipo eq1, Equipo eq2) {

				if (eq1.getId() < eq2.getId()) {
					return 1;
				} else if (eq1.getId() > eq2.getId())
					return -1;
				else
					return 0;
			}

		});

		return lista;
	}

	// 05 de febrero 2019
	public void ordenarMapaPuntosEquipos(HashMap<String, Integer> puntosEquipos) {

		Set<Entry<String, Integer>> set = puntosEquipos.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		for (Map.Entry<String, Integer> entry : list) {
			System.out.println(entry.getKey() + " ==== " + entry.getValue());
		}
	}

	public HashMap<String, Integer> generaPuntosEquipos(HashMap<String, ArrayList<Integer>> partidos_GEP) {

		HashMap<String, Integer> resultado = new HashMap<String, Integer>();
		for (String clave : partidos_GEP.keySet()) {
			ArrayList<Integer> datos = partidos_GEP.get(clave);
			int puntos = datos.get(0) * 3 + datos.get(1);
			// System.out.println(clave + " => " + puntos);

			resultado.put(clave, puntos);
		}
		return resultado;

	}

	// 31 de enero 2019

	public void muestraPuntosOrdenadosEquipos(HashMap<String, ArrayList<Integer>> resultados) {
		// recorrer el HashMap previamente ordenado
		HashMap<String, Integer> mapaOrdenadoPuntos = new HashMap<String, Integer>();

		// obtenemos la lista de claves (K)
		for (String clave : resultados.keySet()) {
			ArrayList<Integer> datos = resultados.get(clave);
			int puntos = datos.get(0) * 3 + datos.get(1);
			mapaOrdenadoPuntos.put(clave, puntos);
			// System.out.println(clave + " => " + puntos);
		}
		ArrayList<Integer> valoresPuntos = new ArrayList<Integer>(mapaOrdenadoPuntos.values());
		Collections.sort(valoresPuntos);
		mapaOrdenadoPuntos.entrySet();
	}

	// 30 de enero 2019

	public void pruebaSWING() {
		JFrame ventana = new JFrame("Mi primer SWING");
		JButton boton = new JButton("Pulse Me!");
		JPanel panel = new JPanel();
		ventana.add(panel);
		ArrayList<Equipo> equipos = this.crearListaEquipos("ficheros/equipos.txt");
		Equipo[] arrayEquipos = equipos.toArray(new Equipo[equipos.size()]);
		JComboBox listaEquipo = new JComboBox(arrayEquipos);

		panel.add(listaEquipo);
		panel.add(boton);
		boton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Me has pulsado");

			}
		});
		ventana.pack();
		ventana.setVisible(true);

	}

	// 29 enero 2019

	public HashMap<String, Integer> muestraPuntosEquipos(HashMap<String, ArrayList<Integer>> resultados) {
		// recorrer el HashMap previamente ordenado
		// obtenemos la lista de claves (K)
		HashMap<String, Integer> puntosTotales = new HashMap<String, Integer>();
		for (String clave : resultados.keySet()) {
			ArrayList<Integer> datos = resultados.get(clave);
			int puntos = datos.get(0) * 3 + datos.get(1);
			puntosTotales.put(clave, puntos);
			// System.out.println(clave + " => " + puntosTotales);
		}
		return puntosTotales;

	}

	/*
	 * public void muestraPuntosEquipos(HashMap<String, ArrayList<Integer>>
	 * resultados) { // recorrer el HashMap previamente ordenado // obtenemos la
	 * lista de claves (K) for (String clave : resultados.keySet()) {
	 * ArrayList<Integer> datos = resultados.get(clave); int puntos = datos.get(0) *
	 * 3 + datos.get(1); System.out.println(clave + " => " + puntos); }
	 * 
	 * }
	 */

	public HashMap<String, ArrayList<Integer>> resultadosEquipos(String rutaPartidos)
	// devuelve un mapa de equipos
	// por cada equipo hay una lista de contadores
	// que representan VICTORIAS, EMPATES Y DERROTAS
	{
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaPartidos));
			String registro;

			HashMap<String, ArrayList<Integer>> equipos = new HashMap<String, ArrayList<Integer>>();
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				if (campos[3].equals("")) // ultimo partido jugado..
					break;
				String eL = campos[2];
				String gL = campos[3];
				String eV = campos[4];
				String gV = campos[5];

				// gracias Byron..!!
				// si no existe eL, eV lo a�adimos al mapa..

				if (!equipos.containsKey(eL))
					equipos.put(eL, new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0)));

				if (!equipos.containsKey(eV))
					equipos.put(eV, new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0)));

				// cual fue el resultado ..?

				if (gL.compareTo(gV) > 0) {// gana Local
					equipos.get(eL).set(0, equipos.get(eL).get(0) + 1);
					equipos.get(eV).set(2, equipos.get(eV).get(2) + 1);

				} else if (gL.compareTo(gV) < 0) // gana Visitante
				{// gana Local
					equipos.get(eL).set(2, equipos.get(eL).get(2) + 1);
					equipos.get(eV).set(0, equipos.get(eV).get(0) + 1);

				} else { // empate

					equipos.get(eL).set(1, equipos.get(eL).get(1) + 1);
					equipos.get(eV).set(1, equipos.get(eV).get(1) + 1);
				}
				equipos.get(eL).set(3, equipos.get(eL).get(3) + Integer.parseInt(gL));
				equipos.get(eL).set(4, equipos.get(eL).get(4) + Integer.parseInt(gV));
				equipos.get(eV).set(3, equipos.get(eV).get(3) + Integer.parseInt(gV));
				equipos.get(eV).set(4, equipos.get(eV).get(4) + Integer.parseInt(gL));

			}
			fichero.close();
			System.out.println("Fin de la lectura del fichero");
			return equipos;

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
		return null;

	}

	// 23 enero 2019

	public void mostrarNumeroPartidosJugados(String rutaPartidos) {

		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaPartidos));
			String registro;
			int contador = 0;
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				if (!campos[3].equals("")) {
					Integer.parseInt(campos[3]);
					contador++;

				} else
					break;
			}
			fichero.close();
			System.out.println(contador);
			System.out.println("Fin de la lectura del fichero");

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
	}

	// Mapa de equipos

	public HashMap<String, Equipo> crearMapaEquipos(String rutaFichero) {
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaFichero));
			String registro;
			Equipo equipo = null;
			HashMap<String, Equipo> equipos = new HashMap<String, Equipo>();
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				equipo = new Equipo(Integer.parseInt(campos[0]), campos[1], campos[2]);
				// equipos.put(campos[1], equipo.getNombreLargo());
				equipos.put(campos[1], equipo);
			}
			fichero.close();
			System.out.println("Fin de la lectura del fichero");
			return equipos;

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
		return null;
	}

	/*
	 * public HashMap<String, Equipo> crearMapaEquipos(String rutaFichero) { try {
	 * BufferedReader fichero; fichero = new BufferedReader(new
	 * FileReader(rutaFichero)); String registro; Equipo equipo = null;
	 * HashMap<String, Equipo> equipos = new HashMap<String, Equipo>(); while
	 * ((registro = fichero.readLine()) != null) { String[] campos =
	 * registro.split("#"); equipo = new Equipo(Integer.parseInt(campos[0]),
	 * campos[1], campos[2]); equipos.put(campos[1], equipo); } fichero.close();
	 * System.out.println("Fin de la lectura del fichero"); return equipos;
	 * 
	 * } catch (FileNotFoundException excepcion) {
	 * System.out.println("fichero no encontrado");
	 * 
	 * } catch (IOException e) { System.out.println("IO Excepcion"); } return null;
	 * }
	 */
	// lista de equipos

	public static ArrayList<Equipo> crearListaEquipos(String rutaFichero) {
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaFichero));
			String registro;
			Equipo equipo = null;
			ArrayList<Equipo> equipos = new ArrayList<Equipo>();
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				equipo = new Equipo(Integer.parseInt(campos[0]), campos[1], campos[2]);
				equipo.setGc(0);
				equipo.setGf(0);
				equipo.setPe(0);
				equipo.setPg(0);
				equipo.setPp(0);
				equipo.setPuntos(0);
				equipos.add(equipo);

			}
			fichero.close();
			System.out.println("Fin de la lectura del fichero");
			return equipos;

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
		return null;
	}

	// 22 de enero 2019

	public HashMap<String, Integer> comprobarPartidos(String rutaFichero, String separador) {
		HashMap<String, Integer> mapaEquipo = new HashMap<String, Integer>();

		try {
			FileReader fr = new FileReader(rutaFichero);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			String equipoLocal = "";
			String equiposVisitante = "";
			Integer numVeces = new Integer(1);
			while ((linea = br.readLine()) != null) {
				String[] campos = linea.split(separador);
				for (int i = 2; i < campos.length; i += 2) {
					if (mapaEquipo.containsKey(campos[i])) {
						mapaEquipo.replace(campos[i], (mapaEquipo.get(campos[i]) + numVeces));
					} else {
						mapaEquipo.put(campos[i], numVeces);
					}
				}

			}
			fr.close();
			System.out.println("fin de la lectura del fichero");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapaEquipo;
	}

	// 15 de enero 2019

	public ArrayList<Persona> creaListaPersonas(String rutaFichero, String separador) {
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaFichero));

			ArrayList<Persona> listaPersonas;
			listaPersonas = new ArrayList<Persona>();

			String registro;
			Persona persona;
			while ((registro = fichero.readLine()) != null) {
				// System.out.println(registro);

				// romper la cadena registro
				String[] campos = registro.split(separador);
				for (int i = 0; i < campos.length; i++)
					System.out.print(campos[i] + " , ");
				System.out.println("");

				// crear objeto de la clase Persona
				// a�adirlo a la listaPersonas
				persona = new Persona(campos[0], campos[1], campos[2], Integer.parseInt(campos[3]),
						campos[4].charAt(0));

				listaPersonas.add(persona);

			}
			fichero.close();
			System.out.println("Creada la lista de personas...");

			return listaPersonas;

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
		return null;
	}

	public ArrayList<Persona> creaListaPersona(String rutaFichero, String separador) {
		// Abrir el fichero
		ArrayList<Persona> listaPersona = new ArrayList<Persona>();
		try {
			FileReader fr = new FileReader(rutaFichero);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] campos = linea.split(separador);
				for (int i = 0; i < campos.length; i++) {
					// System.out.println(campos[i]);
					System.out.print(campos[i] + " , ");
					System.out.println("");
				}

				Persona per1 = new Persona(campos[0], campos[1], campos[2], Integer.parseInt(campos[3]),
						campos[4].charAt(0));
				listaPersona.add(per1);
				// crear objeto de la clase persona
				// a�adir a la lista
				// System.out.println(linea);

			}

			fr.close();
			System.out.println("fin de la lectura del fichero");
			return listaPersona;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// 10 enero 2019

	public void leerFichero(String rutaFichero) {
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaFichero));
			String registro;
			while ((registro = fichero.readLine()) != null) {
				System.out.println(registro);
			}
			fichero.close();
			System.out.println("Fin de la lectura de fichero");
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
	}

	public void leerFicheroTexto() {
		try {
			// Abrir el fichero
			FileReader fr = new FileReader("ficheros/datos.txt");
			BufferedReader br = new BufferedReader(fr);
			String linea;
			// System.out.println(LocalDate.now());
			// Leer el fichero linea a linea
			while ((linea = br.readLine()) != null) {

				String[] campos = linea.split("&");
				System.out.println(linea);
				// System.out.println(calculaEdad(campos[2]));

			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// 9 enero 2019

	public void introMapas() {
		// declarar un mapa (HasMap) que almacena
		// objetos de la clase Persona, la clave es el nif..
		HashMap<String, Estudiante> mapa = new HashMap<String, Estudiante>();
		Estudiante est = new Estudiante();
		Estudiante est2 = new Estudiante("54134207g", "Kilian", null, 100, 'M');
		mapa.put(est.getNif(), est);
		mapa.put(est2.getNif(), est2);
		Set<String> clavesMapas = mapa.keySet();
		for (String clave : clavesMapas)
			System.out.println(mapa.get(clave).getNombre());
		// System.out.println(mapa.get(est2.getNif()).getNif());

	}

	// 8 enero 2019

	public void introListas() {

		ArrayList<Object> listaGenerica = new ArrayList<Object>(10);
		// System.out.println("Lista generica tiene " + listaGenerica.size());

		listaGenerica.add("Gran Canaria");
		String nombre = "Pepe";
		listaGenerica.add(nombre);
		listaGenerica.add(123);
		listaGenerica.add(Math.PI);
		listaGenerica.add(123.5f);
		listaGenerica.add(true);
		listaGenerica.add(new Persona());

		// for (Object elemento: listaGenerica)
		// System.out.println(elemento);

		for (int i = 0; i < listaGenerica.size(); i++)
			System.out.println(listaGenerica.get(i));

		// System.out.println("Lista generica tiene " + listaGenerica.size());

		ArrayList<Persona> listaPersonas;

		listaPersonas = new ArrayList<Persona>();

		Persona persona1 = new Persona();

		listaPersonas.add(persona1);

		listaPersonas.add(new Persona());

		listaPersonas.add(new Persona("54134207G", "Kilian", null, 145, 'M'));

		// listaPersonas.add(1, new Persona("nuevoNif", "Pepe", 145, LocalDate.now(),
		// 'M'));

		// System.out.println(listaPersonas.get(1).getNombre());

		System.out.println("fin listas");
	}

	// 18 diciembre 2018

	public void introLista2() {
		ArrayList<Persona> lista = new ArrayList<Persona>();
		lista.add(new Persona("34268798F", "Miriam", null, 150, 'F'));
		lista.add(new Persona());
		lista.add(1, new Persona("89786543L", "Paco", null, 100, 'M'));
		System.out.println(lista.get(1).getNombre());
		System.out.println("Fin lista");

	}

	public void introLista() {
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("Sara");
		lista.add("Miriam");
		lista.add("Juan");
		System.out.println(lista);

	}

	// 13 dic 2018 Intro a la Herencia

	// crear un Estudiante

	public void crearEstudiante() {

		Estudiante estudiante = new Estudiante("43781230V", "Pedro Garcia", null, 153, 'M');

	}

	public void pruebasAPI() {
		// 1. imprimiir por consola el valor de 2 elevado a 2
		// 2. Imprime un valor aleatorio entre 1 y 100
		// 3. En la cadena "LAS PALMAS DE GRAN CANARIA", imprime "GRAN"

		// 1. Usa la clase estatica java.lang.Math y sus metodos
		// 2. Usa la clase java.util.Ramdom y el metodo rnd.
		// 3.

		// double resul = Math.pow(2, 3);
		System.out.println("1." + Math.pow(2, 3));
		Random rnd = new Random();
		System.out.println("2." + rnd.nextInt(100));
		System.out.println("3." + "LAS PALMAS DE GRAN CANARIA".substring(14, 18));

	}

	public Persona[] crearListaPersona() {

		return personas;

	}

	// declarar un array de persona

	private Persona[] personas = {};

	/*
	 * public void hijosPersona() { // int numHijos= personas[4].getHijos().length;
	 * 
	 * for (int i = 0; i < personas.length; i++) { Persona[] hijos =
	 * personas[i].getHijos(); System.out.println("Progenitor --> "+
	 * personas[0].getNombre()); if (hijos != null) for (int j = 0; j <
	 * hijos.length; j++) { System.out.println("Hijo --> " + hijos[j].getNombre());
	 * 
	 * } } }
	 */
	public void invertirLista(int[] lista) {
		int aux = 0;
		for (int i = 0; i < lista.length / 2; i++) {

			aux = lista[i];
			lista[i] = lista[lista.length - i - 1];
			lista[lista.length - i - 1] = aux;
		}

	}

	public int[] invertirLista2(int[] lista) {
		int[] resultado = new int[lista.length];
		for (int i = 0; i < lista.length; i++) {
			// lista[i] = lista[lista.length - i - 1];
			resultado[resultado.length - 1 - i] = lista[i];
		}
		return resultado;

	}

	public int[] mezclaListaOrdenadas(int[] l1, int[] l2) {

		int i = 0, j = 0, k = 0;
		int[] resultado = new int[l1.length + l2.length];

		while (l1[i] != Integer.MAX_VALUE || l2[j] != Integer.MAX_VALUE) {
			if (l1[i] < l2[j])
				resultado[k] = l1[i++];
			else
				resultado[k] = l2[j++];
			k++;

			if (i == l1.length)
				l1[--i] = Integer.MAX_VALUE;

			if (j == l2.length)
				l2[--j] = Integer.MAX_VALUE;
		}
		return resultado;

	}

	public void invertirCaracterCadena(String cadena) {
		String sCadenaInvertida = null;
		for (int i = cadena.length() - 1; i >= 0; i--) {
			sCadenaInvertida = sCadenaInvertida + cadena.charAt(i);
		}

		// for (int x=sCadena.length()-1;x>=0;x--)
		// sCadenaInvertida = sCadenaInvertida + sCadena.charAt(x)
	}

	public void mostrarVentasVndedor() {

	}

	public int[] convertirCadenasAnumeros(String[] cn) {
		int[] resultado = new int[cn.length];
		for (int i = 0; i < resultado.length; i++)
			try {
				resultado[i] = Integer.parseInt(cn[i]);
			} catch (NumberFormatException e) {
				resultado[i] = -1;
			}

		return resultado;

	}

	/*
	 * public float [] resumenVendedorIrregular(float[][] ventas) { float
	 * acumVendedor = 0; float [] resultado = new float[ventas.length]; for (int i =
	 * 0; i < ventas.length; i++) { if(ventas[i].length > acumVendedor)
	 * acumVendedor=ventas[i].length; } for (int j = 0; j < resultado.length; j++) {
	 * for } return resultado; }
	 */

	// Ejercicio para tener claro los bucles anidados

	public int[] listarPrimos3(int desde, int cuantos) {
		int[] primos = new int[cuantos];
		int acum = 0;
		while (acum < cuantos) {
			if (esPrimo2(desde))
				primos[acum++] = desde;
			desde++;
		}
		return primos;
	}

	public void listarPrimos2(int desde, int cuantos) {
		int acum = 0;
		while (acum < cuantos) {
			if (esPrimo2(desde++))
				System.out.print(desde + ", ");
			acum++;

		}
	}

	public void listarPrimos(int desde, int hasta) {
		for (int i = desde; i < hasta; i++)
			if (esPrimo2(i))
				System.out.print(i + ",");

	}

	public boolean esPrimo2(int numero) {

		for (int i = 2; i < numero; i++) {
			if (numero % i == 0)
				return false;
		}

		return true;
	}

	public void esPrimo(int numero) {
		boolean primo = true;
		for (int i = 2; i < numero; i++) {
			if (numero % i == 0)
				primo = false;
			break;
		}
		if (primo)
			System.out.println(numero + " es primo");
		else
			System.out.println(numero + " no es primo");
		;
	}

	public void imprimirFechaHora() {
		for (int i = 0; i < 10; i++) {
			System.out.println(LocalDateTime.now());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void reloj() {
		for (int h = 0; h < 24; h++)
			for (int m = 0; m < 60; m++)
				for (int s = 0; s < 60; s++) {
					System.out.println(h + ":" + m + ":" + s);
					try {
						Thread.sleep(s);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

	}

	public float[] resumenVendedorPorMes(float[][] ventas) {
		float[] resultado = new float[ventas[0].length];
		float acum = 0;
		for (int j = 0; j < ventas[0].length; j++) {
			for (int i = 0; i < ventas.length; i++)
				acum += ventas[i][j];
			resultado[j] = acum;

		}

		return resultado;

	}

	public float[] resumenVendedor(float[][] ventas) {
		float[] resultado = new float[ventas.length];
		float acum = 0f;
		for (int i = 0; i < ventas.length; i++) {
			acum = 0;
			for (int j = 0; j < ventas[i].length; j++)
				acum += ventas[i][j];
			resultado[i] = acum;

		}

		return resultado;

	}

	public void recorerMatriz(int[][] mat) {
		int acum = 0;
		for (int i = 0; i < mat.length; i++)
			for (int j = 0; j < mat[i].length; j++) {
				acum += mat[i][j];

			}
	}

	public void compararCadena() {
		String nombre = "abc";
		String nombre2 = "aba";
		// System.out.println(nombre.compareTo(nombre2));

		if (nombre.compareTo(nombre2) == 0)
			System.out.println("cadena1 y cadena2 son iguales");
		else if (nombre.compareTo(nombre2) < 0)
			System.out.println("cadena1 va antes que cadena2");
		else if (nombre.compareTo(nombre2) > 0)
			System.out.println("cadena2 va despu�s que cadena1: " + nombre2);

	}

	public void pruebaCadena() {

		String nombre = "LAS PALMAS DE GRAN CANARIAS";

		for (int i = 0; i < nombre.length(); i++) {
			System.out.println(nombre.charAt(i));

		}

	}

	public float devolverSaldo(float saldoInicial, float[] movimientos) {
		float saldoFinal = saldoInicial;
		for (int i = 0; i < movimientos.length; i++)
			saldoFinal += movimientos[i];
		System.out.println("Saldo Final es: " + saldoFinal + "�");

		return saldoFinal;

	}

	public Persona[] devolverListaPersona(int n) {
		Persona[] resultado = new Persona[n];
		for (int i = 0; i < n; i++) {
			resultado[i] = new Persona();
		}
		return resultado;
	}

	public void imprimeAleatorio(int n) {
		Random rnd = new Random();
		int numero = 0;
		for (int i = 0; i < n; i++) {
			numero = 1 + rnd.nextInt(100);
			System.out.println(i + 1 + ".- " + numero);
		}
	}

	public int[] estadisticaAparicionNumero(int cuantos, int inferior, int superior) {
		int[] resultado = new int[superior - inferior + 1];
		Random rnd = new Random();
		int numero = 0;
		for (int i = 0; i < cuantos; i++) {
			numero = inferior + rnd.nextInt(superior - inferior + 1);
			resultado[numero - 1]++;
		}

		return resultado;
	}

	// crear un metodo para calcular la frecuencia de aparici�n de un n�mero

	public int[] frecuenciaNum(int cuantos, int inferior, int superior) {
		int[] resultado = new int[superior - inferior + 1];
		int[] lanzamiento = this.generaAleatorio2(cuantos, inferior, superior);
		for (int i = 0; i < lanzamiento.length; i++) {
			resultado[lanzamiento[i] - 1]++;
		}
		return resultado;
	}

	// crear un metodo que genere numeros aleatorios y devuleva un array de enteros

	public int[] generaAleatorio(int n) { // cabecera
		int[] resultado = new int[n]; // inicializar array
		Random rnd = new Random();
		for (int i = 0; i < n; i++)
			resultado[i] = rnd.nextInt(1000);
		return resultado;

	}

	// devuelve un Array de numeros enteros aleatorios

	public int[] generaAleatorio2(int cuantos, int inferior, int superior) {
		int[] resultado = new int[cuantos];// Declaro e Inicializo el Array
		Random rnd = new Random();
		for (int i = 0; i < cuantos; i++)
			resultado[i] = inferior + rnd.nextInt(superior - inferior + 1);

		return resultado;
	}

	// Mostrar por consola los numeros comprendidos entre dos entero a y b

	// 1.prototipo del metodo

	public void listaIntervaloEnteros(int primero, int ultimo) {
		// 2.implementacion del metodo

		for (int j = primero; j < ultimo; j++)
			System.out.println(j);
		;

		// true/false
		boolean condicion = true;
		if (condicion) {
			// en caso de verdadero

		} else {
			// en caso de falso
		}

		// int i=primero;
		/*
		 * while (i < ultimo){ System.out.println(i++);
		 */
	}

	public int factorialNumero(int n) {

		int resultado = 1;
		for (int i = 1; i <= n; i++) {
			resultado *= i;
			System.out.println(resultado);
		}

		return resultado;

	}

	public static int devuelveMayor3(int n1, int n2, int n3, int n4) {
		if (n1 > n2)
			if (n1 > n3)
				if (n1 > n4)
					return n1;
				else
					return n4;

			else if (n3 > n4)
				return n3;
			else
				return n4;

		else if (n2 > n3)
			if (n2 > n4)
				return n2;
			else
				return n4;
		else if (n3 > n4)
			return n3;
		else
			return n4;

	}

	public float devuelveMayor3(float n1, float n2, float n3) {
		if (n1 > n2)
			if (n1 > n3)
				return n1;
			else
				return n3;
		else if (n2 > n3)
			return n2;
		else
			return n3;
	}

	public void devuelveMayor2(float n1, float n2, float n3) {

		if (n1 > n2) {
			if (n1 > n3) {
				System.out.println("El mayor es: " + n1);
			} else {
				System.out.println("el mayor es: " + n3);
			}
		} else if (n2 > n3) {
			System.out.println("el mayor es: " + n2);
		} else {
			System.out.println("el mayor es: " + n3);
		}

	}

	public void devuelveMayor() {

		Scanner sc = new Scanner(System.in);
		int n1, n2, n3;
		System.out.print("Introduzca primer n�mero: ");
		n1 = sc.nextInt();
		System.out.print("Introduzca segundo n�mero: ");
		n2 = sc.nextInt();
		System.out.print("Introduzca tercer n�mero: ");
		n3 = sc.nextInt();
		if (n1 > n2) {
			if (n1 > n3) {
				System.out.println("El mayor es: " + n1);
			} else {
				System.out.println("el mayor es: " + n3);
			}
		} else if (n2 > n3) {
			System.out.println("el mayor es: " + n2);
		} else {
			System.out.println("el mayor es: " + n3);
		}
	}

	public Integer convertirCadenaAentero(String cadena) {
		int parse_numero = 0;
		try {
			parse_numero = Integer.parseInt(cadena);
			System.out.println("El numero es : " + cadena);
		} catch (NumberFormatException e) {
			System.out.println(cadena + " no es un numero valido");
		}
		return parse_numero;
	}

	public void serieFibonacci(int cuanto) {
		int x = 0;
		int y = 1;
		int z;
		int fin = 0;
		for (int i = 2; i < cuanto; i++) {
			// System.out.print(x +" ,");
			z = x + y;
			x = y;
			y = z;
			fin = z;
			System.out.print(fin + ",");
		}

	}

	public int[] numerosFibonacci(int cuantos) {
		int[] numeros = new int[cuantos];
		int x = 0;
		int y = 1;
		int z;
		numeros[0] = x;
		numeros[1] = y;
		for (int i = 2; i < cuantos; i++) {
			z = x + y;
			numeros[i] = z;
			x = y;
			y = z;
		}
		return numeros;
	}

	/*
	 * public static void main(String[] args) { Ejercicios ejercicios = new
	 * Ejercicios(); int n1 = 15, n2 = 50, n3 = 5, n4 = 20; int mayor =
	 * Ejercicios.devuelveMayor3(n1, n2, n3, n4); // definido con la Clase
	 * Ejercicios al estar el metod en // statico // int mayor =
	 * ejercicios.devuelveMayor3(n1, n2, n3, n4); //definido con el // objeto
	 * ejercicios System.out.println("el mayor es : " + mayor); }
	 */

}
