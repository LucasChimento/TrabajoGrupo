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
	public static boolean comprobarEquipo(Equipo equipo)
	{
		for(Equipo e : equipos)
		{
			if(e.getDatos().equals(equipo.getDatos()))
			{
				return true;
			}
		}
		return false;
	}
	public static Equipo getEquipoPorDatos(String nombreEquipo,String descripcion)
	{
		for(Equipo e : equipos)
		{
			if(e.getDatos().equals(nombreEquipo+" "+descripcion))
			{
				return e;
			}
		}
		return null;
	}
}
