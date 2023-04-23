package paquetePrincipal;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConexionBDD {
	private static Connection conexion;
	
	public static boolean getEstadoConexion()
	{
		if(conexion==null)return false;
		try {
			return !conexion.isClosed();
		}catch(SQLException e)
		{
			System.out.println("-Error al conocer el estado de la conexion con la base de datos.\n"+e.getMessage()+"\n SQLstate:"+e.getSQLState()+"\n");
			conexion=null;
			return false;	
		}
	}
	public static boolean ConectarBDD(String driverDeConexion,String localHost,String nombreBDD,String usuario, String contrasegna)
	{
		try
		{
			conexion=DriverManager.getConnection(driverDeConexion+"://localhost:"+localHost+"/"+nombreBDD,usuario,contrasegna);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println("-Error al intentar la conexion con la base de datos.\n"+e.getMessage()+"\n SQLstate:"+e.getSQLState()+"\n");
			return false;
		}
	}
	
	public static ArrayList<Persona> leerPersonas()
	{
		ArrayList<Persona> personas  = new ArrayList<Persona>();
		try {
		Statement consulta=conexion.createStatement();
		ResultSet tabla=consulta.executeQuery("SELECT * FROM personas");
		while(tabla.next())
		{
			Persona p= new Persona(tabla.getString("Nombre"),tabla.getInt("idPersona"));
			personas.add(p);
		}
		}catch(SQLException e)
		{
			System.out.println("-Error en la consulta SQL en la lectura de personas.\n"+e.getMessage()+"\n SQLstate:"+e.getSQLState()+"\n");
			System.out.println("-No se pudo leer la tabla.");
			return null;
		}
		return personas;
	}
	public static boolean leerPronosticos(Persona p,HashMap<Integer, Ronda> rondas)
	{
		try {
		Statement consulta=conexion.createStatement();
		ResultSet tabla=consulta.executeQuery("SELECT * FROM pronosticos WHERE idPersona="+p.getId());
		while(tabla.next())
		{
			String resultadoP= tabla.getString("Resultado");
			ResultadoPartido res;
			switch(resultadoP.toLowerCase())
			{
			case "gana":
				res=ResultadoPartido.GANA;
				break;
			case "empata":
				res=ResultadoPartido.EMPATA;
				break;
			case "pierde":
				res=ResultadoPartido.PIERDE;
				break;
				default:
					System.out.println("-Error al leer Resultado desde la base de datos. Revise la informacion disponible.\n");
					return false;
			}
			int nroRonda=tabla.getInt("nroRonda")-1;
			int nroPartido=tabla.getInt("nroPartido")-1;
			Partido par=rondas.get(nroRonda).getPartido(nroPartido);
			String equipo=tabla.getString("Equipo");
			Equipo e=Equipo.getEquipoPorDatos(equipo);
			Pronostico pr= new Pronostico(nroRonda,par,e,res);
			p.agregarPronostico(pr);
		}
		return true;
		}catch(SQLException e)
		{
			System.out.println("-Error en la consulta SQL en la lectura de pronosticos.\n"+e.getMessage()+"\n SQLstate:"+e.getSQLState()+"\n");
			System.out.println("-No se pudo leer la tabla.");
			return false;
		}
	}
		public static void cerrarConexion()
		{
			if(conexion==null) return;
			do {
			try {
			if(conexion.isClosed()) return;
			else
			{
				if(ManejoConsola.preguntaSioNo("-Se cerrara la conexion con la base de datos.\n-Esta seguro? s/n"))
				{
					conexion.close();
				}
				else return;
			}
			}catch(SQLException e)
			{
				System.out.println("-Error al intentar cerrar la conexion con la base de datos.\n"+e.getMessage()+"\n SQLstate:"+e.getSQLState()+"\n");
				if(ManejoConsola.preguntaSioNo("-Desea salir? s/n"))
					return;
			}
			}while(true);
		}
	}
	



	


