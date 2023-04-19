package paquetePrincipal;

import java.util.*;


public class Persona implements Comparable<Persona> {
	private String nombre;
	private ArrayList<Pronostico> pronosticos = new ArrayList<Pronostico>();
	private int puntos;
	private boolean puntosObtenidos= false;
	
	public Persona(String nombre)
	{
		this.nombre=nombre;
		puntos=0;
	}
	// Getters
	public int getPuntos() {return puntos;}
	public boolean getPuntosObtenidos() {return puntosObtenidos;}
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
			System.out.print(p.getEquipo(1).getDatos()+" vs ");
			System.out.println(p.getEquipo(2).getDatos());
			System.out.println(p.getGolesEquipo1() +" a "+p.getGolesEquipo2());
			System.out.println(nombre +" apuesta porque "+pr.getResultado().toString().toLowerCase()+" "+pr.getEquipo().getDatos());
			if(pr.getResultado()==p.getResultado(pr.getEquipo()))
			{
				System.out.println(nombre+" Gana 1 punto!\n");
				pnts++;
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
				pnts++;
			}
		}
		return pnts;
		
	}
	public int getPuntosTotales(HashMap<Integer, Ronda> rondas)
	{
		if(!puntosObtenidos)
		{
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
						puntos++;
				}	
			}
			puntosObtenidos=true;	
		}
		return puntos;
		
	}
	//Procedimientos
	public void agregarPronostico(Pronostico pron) {pronosticos.add(pron);}
	@Override
	public int compareTo(Persona o) {
		if(o==null) return 0;
		if(this.puntos<o.puntos) return -1;
		if(this.puntos==o.puntos) return 0;
		if(this.puntos>o.puntos) return 1;
		return 0;
	}
	
}
