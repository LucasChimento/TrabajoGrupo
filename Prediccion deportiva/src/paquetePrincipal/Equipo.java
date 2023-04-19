package paquetePrincipal;

import java.util.*;

public class Equipo {
	private static ArrayList<Equipo> equipos= new ArrayList<Equipo>();
	private String nombre;
	private String descripcion;
	
	
	public Equipo(String nombre,String descripcion)
	{
		this.nombre=nombre;
		this.descripcion=descripcion;
		equipos.add(this);
	}
	public String getNombre() {return nombre;}
	public String getDescripcion() {return descripcion;}
	public String getDatos() {return nombre+" "+descripcion;}
	public static Equipo getEquipoPorDatos(String nombreEquipo,String descripcion)
	{
		for(Equipo e : equipos)
		{
			if(e.getNombre().equals(nombreEquipo)&&e.getDescripcion().equals(descripcion))
			{
				return e;
			}
		}
		return null;
	}
}
