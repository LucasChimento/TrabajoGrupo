package paquetePrincipal;

import java.util.*;

public class Ronda {
	private static int nroRonda=0;
	private int ronda;
	private HashMap<Integer,Partido> partidos;
	private int nroPartidos=0;
	
	// Constructor
	public Ronda()
	{
		ronda=nroRonda;
		nroRonda++;
		partidos= new HashMap<Integer,Partido>();
	}
	// Getters
	public Partido getPartido(int numeroPartido) {return partidos.get(numeroPartido);}
	public int getRonda() {return ronda;}
	public int getNroPartidos() {return nroPartidos;}
	//Procedimientos
	public static void resetContador() {nroRonda=0;}
  	public void agregarPartido(Partido part)
	{
		partidos.put(nroPartidos, part);
		part.setNroPartido(nroPartidos);
		nroPartidos++;
	}
 	public void mostrarDatosConsola()
 	{
 		System.out.println("Ronda nro "+(ronda+1));
 		for(int i=0; i<nroPartidos;i++)
 		{
 			System.out.println(partidos.get(i).getDatosConsola());
 		}
 		
 	}

}
