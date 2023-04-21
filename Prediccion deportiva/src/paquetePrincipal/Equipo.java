package paquetePrincipal;

import java.util.*;

public class Equipo {
	private static ArrayList<Equipo> equipos= new ArrayList<Equipo>();
	private String nombre;
	
	
	
	public Equipo(String nombre)
	{
		this.nombre=nombre;
		equipos.add(this);
	}
	public String getNombre() {return nombre;}
	public String getDatos() {return nombre;}
	public static Equipo getEquipoPorDatos(String nombreEquipo)
	{
		for(Equipo e : equipos)
		{
			if(e.getNombre().equals(nombreEquipo))
			{
				return e;
			}
		}
		return null;
	}
}
