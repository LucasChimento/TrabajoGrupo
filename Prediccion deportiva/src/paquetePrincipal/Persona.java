package paquetePrincipal;

import java.util.*;


public class Persona implements Comparable<Persona> {
	private int idPersona;
	private String nombre;
	private ArrayList<Pronostico> pronosticos = new ArrayList<Pronostico>();
	private int puntosParciales;
	private int puntosTotales;
	private int puntosExtrasTotales;
	private static int puntosPorPartidoGanado=0;
	private static int puntosExtra;
	public Persona(String nombre,int id)
	{
		this.nombre=nombre;
		this.idPersona=id;
		puntosParciales=0;
		puntosExtrasTotales=0;
		puntosTotales=0;
	}
	// Setters
	public static void setPuntosPPartido(int puntosPP){puntosPorPartidoGanado=puntosPP;}
	public static void setPuntosExtra(int puntosE) {puntosExtra=puntosE;}
	// Getters
	public static int getPuntosExtra() {return puntosExtra;}
	public int getPuntosE() {return puntosExtrasTotales;}
	public int getPuntos() {return puntosParciales;}
	public int getId() {return idPersona;}
	public String getNombre() {return nombre;}
	public ArrayList<Pronostico> getPronostico() {return pronosticos;}
	public int getPuntosConDetalle(Ronda ronda) {
		int pnts=0;
		for(Pronostico pr : pronosticos)
		{
			if(pr.getNroRonda()!=ronda.getRonda()) continue;
			Partido p = ronda.getPartido(pr.getPartido().getNroPartido());
			if(!(pr.getPartido().getNroPartido()==p.getNroPartido())) continue;
			System.out.println("\nParticipantes");
			System.out.print(p.getEquipo(1).getDatos()+" (Local) vs ");
			System.out.println(p.getEquipo(2).getDatos()+" (Visitante)");
			System.out.println(p.getGolesEquipo1() +" a "+p.getGolesEquipo2());
			System.out.println(nombre +" apuesta porque "+pr.getResultado().toString().toLowerCase()+" "+pr.getEquipo().getDatos());
			if(pr.getResultado()==p.getResultado(pr.getEquipo()))
			{
				System.out.println(nombre+" Gana 1 punto!\n");
				pnts+=puntosPorPartidoGanado;
			}
			else
			{
				System.out.println(nombre+" Se equivoca!\n");
			}
		}
		return pnts;	
	}
	public int getPuntosSinDetalle(Ronda ronda) {
		int pnts=0;
		for(Pronostico pr : pronosticos)
		{
			if(pr.getNroRonda()!=ronda.getRonda()) continue;
			Partido p = ronda.getPartido(pr.getPartido().getNroPartido());
			if(!(pr.getPartido().getNroPartido()==p.getNroPartido())) continue;
			if(pr.getResultado()==p.getResultado(pr.getEquipo()))
			{
				pnts+=puntosPorPartidoGanado;
			}
		}
		return pnts;
		
	}
	public int getPuntosTotales(HashMap<Integer, Ronda> rondas)
	{
		puntosParciales=0;
		Ronda ronda;
		for(int i=0;i<=rondas.size();i++)
		{
			ronda=rondas.get(i);
			if(ronda==null) continue;
			for(Pronostico pr : pronosticos)
			{
				if(pr.getNroRonda()!=ronda.getRonda()) continue;
				Partido p = ronda.getPartido(pr.getPartido().getNroPartido());
				if(!(pr.getPartido().getNroPartido()==p.getNroPartido())) continue;
				if(pr.getResultado()==p.getResultado(pr.getEquipo()))
					puntosParciales+=puntosPorPartidoGanado;
			}	
		}	
		puntosTotales=puntosParciales+puntosExtrasTotales;
		return puntosTotales;
		
	}
	public void agregarPuntosExtra(int puntosE) 
	{ 
		puntosExtrasTotales=(puntosExtra*puntosE);
		System.out.println("-Se agregaron "+(puntosExtra*puntosE));
	}
	//Procedimientos
	public void agregarPronostico(Pronostico pron) {pronosticos.add(pron);}
	@Override
	public int compareTo(Persona o) {
		if(o==null) return 0;
		if(this.puntosTotales<o.puntosTotales) return -1;
		if(this.puntosTotales==o.puntosTotales) return 0;
		if(this.puntosTotales>o.puntosTotales) return 1;
		return 0;
	}
	
}
