package paquetePrincipal;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConexionBDD {
	private static Connection conexion;
	
	public static boolean ConectarBDD(String driverDeConexion,String localHost,String nombreBDD,String usuario, String contrasegna)
	{
		try
		{
			conexion=DriverManager.getConnection(driverDeConexion+"://localhost:"+localHost+"/"+nombreBDD,usuario,contrasegna);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("-Error al intentar conectar a la base de datos.\n");
			e.printStackTrace();
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
			System.out.println("-Error en la consulta SQL.\n");
			e.printStackTrace();
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
			System.out.println("-Error en la consulta SQL.\n"+e.getStackTrace()+"\n"+e.getSQLState()+"\n");
			return false;
		}
	}
	
}


	


