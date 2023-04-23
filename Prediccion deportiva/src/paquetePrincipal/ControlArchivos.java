package paquetePrincipal;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ControlArchivos {
	private static String rutaConfiguracionPorDefecto="../Configuracion/";
	private static String rutaRondasPorDefecto="../Rondas/";
	private static String encabezadoRondaPorDefecto="-Equipo local\tGoles\tGoles\tEquipo visitante\n";
	private static String encabezadoPronosticoPorDefecto="-Equipo local\tGana\tEmpata\tGana\tEquipo visitante\n";
	private static String rutaPronosticosPorDefecto="../Pronosticos/";
	private static String rutaRondas=rutaRondasPorDefecto;
	private static String rutaConfig=rutaConfiguracionPorDefecto;
	private static String rutaPronosticos=rutaPronosticosPorDefecto;
	private static String encabezadoRonda=encabezadoRondaPorDefecto;
	private static String encabezadoPronostico=encabezadoPronosticoPorDefecto;
	
	// getters
	public static String getRutaRondas() {return rutaRondas;}
	public static String getRutaPronosticos() {return rutaPronosticos;}
	public static String getEncabezadoRonda() {return encabezadoRonda;}
	public static String getEncabezadoPronostico() {return encabezadoPronostico;}
	public static String getRutaRondasPorDefecto() {return rutaRondasPorDefecto;}
	public static String getRutaConfigsPorDefecto() {return rutaConfiguracionPorDefecto;}
	public static String getEncabezadoRondaPorDefecto() {return encabezadoRondaPorDefecto;}
	public static String getEncabezadoPronosticoPorDefecto() {return encabezadoPronosticoPorDefecto;}
	public static String getRutaPronosticosPorDefecto() {return rutaPronosticosPorDefecto;}
	// setters
	public static boolean setRutaConfig(String nuevaRuta)
	{
		if(nuevaRuta==null||nuevaRuta.equals(""))
		{
			System.out.println("-Se utilizara la ruta por defecto: \""+rutaConfiguracionPorDefecto+"\".\n");
			rutaConfig=rutaConfiguracionPorDefecto;
			return true;
		}
		if(!nuevaRuta.endsWith("/")||!nuevaRuta.endsWith("\\"))
		{
			if(nuevaRuta.contains("\\"))nuevaRuta=nuevaRuta+"\\";
			else nuevaRuta=nuevaRuta+"/";
		}
		if(Files.exists(Paths.get(nuevaRuta)))
		{
			rutaConfig=nuevaRuta;
			return true;
		}
		else
		{
			System.out.println("-No se encontro la ruta: \""+nuevaRuta+"\".\n");
			return false;
		}
	}
	
	public static boolean setRutaRondas(String nuevaRuta)
	{
			if(nuevaRuta==null||nuevaRuta.equals(""))
			{
				System.out.println("-Se utilizara la ruta por defecto: \""+rutaRondasPorDefecto+"\".\n");
				rutaRondas=rutaRondasPorDefecto;
				return true;
			}
			if(!nuevaRuta.endsWith("/")||!nuevaRuta.endsWith("\\"))
			{
				if(nuevaRuta.contains("\\"))nuevaRuta=nuevaRuta+"\\";
				else nuevaRuta=nuevaRuta+"/";
			}
			if(Files.exists(Paths.get(nuevaRuta)))
			{
				rutaRondas=nuevaRuta;
				return true;
			}
			else
			{
				System.out.println("-No se encontro la ruta: \""+nuevaRuta+"\".\n");
				return false;
			}
	}
	public static boolean setRutaPronosticos(String nuevaRuta)
	{
			if(nuevaRuta==null||nuevaRuta.equals(""))
			{
				System.out.println("-Se utilizara la ruta por defecto.\""+rutaPronosticosPorDefecto+"\".\n");
				rutaPronosticos=rutaPronosticosPorDefecto;
				return true;
			}
			if(!nuevaRuta.endsWith("/")||!nuevaRuta.endsWith("\\"))
			{
				if(nuevaRuta.contains("\\"))nuevaRuta=nuevaRuta+"\\";
				else nuevaRuta=nuevaRuta+"/";
			}
			if(Files.exists(Paths.get(nuevaRuta)))
			{
				rutaPronosticos=nuevaRuta;
				return true;
			}
			else
			{
				System.out.println("-No se encontro la ruta: \""+nuevaRuta+"\".\n");
				return false;
			}
	}
	// Creacion de archivos
	public static Persona crearPronosticosPersona(String nombrePersona)
 	{
		Persona pers=new Persona(nombrePersona,0);
 		Path nuevaRuta;
 		String nombreArchivo="Pronosticos.txt";
		try {		
				String rut=rutaPronosticos+"/"+nombrePersona+"/";
				nuevaRuta=Files.createDirectories(Paths.get(rut));;
				Path archivo=Paths.get(nuevaRuta.toString()+"/"+nombreArchivo);
				if(Files.notExists(archivo))
				{
					System.out.println("-EL archivo de pronosticos creado sera guardado en la direccion \""+rut+"\".\n");
					Files.createFile(archivo);
					Files.writeString(archivo,"Ronda-X-Partido-X"+"\n"+encabezadoPronostico, StandardOpenOption.APPEND);
				}
				return pers;
		}catch(IOException e)
		{
			System.out.println("-Error "+e+" en la creacion de pronosticos de "+nombrePersona+"\n");
			return null;
		}
 	}
	public static boolean crearDirectoriosPorDefecto(int nro)
	{
		Path ruta;
		switch(nro)
		{
		case 0:
			ruta=Paths.get(rutaRondasPorDefecto);
			break;
		case 1:
			ruta=Paths.get(rutaPronosticosPorDefecto);
			break;
		case 2:
			ruta=Paths.get(rutaConfiguracionPorDefecto);
			break;
			default:
				return false;
		}
		try {		
			Files.createDirectories(ruta);
			return true;
		}catch(IOException e)
		{
			System.out.println("-Error "+e+" al crear directorios por defecto.\n");
			return false;
		}
	}
	public static void crearArchivosRondas() 
	{
		try {		
				Path archivo=Paths.get(rutaRondas+"Rondas.txt");
				if(Files.notExists(archivo))
				{
					System.out.println("-El archivo de rondas creado sera guardado en la direccion \""+rutaRondas+"\".\n");
					Files.createFile(archivo);
					Files.writeString(archivo,"Ronda-X\n"+encabezadoRonda, StandardOpenOption.APPEND);
				}
		}catch(IOException e)
		{
			System.out.println("-Error en la creacion de ronda.\n");
			e.printStackTrace();
		}	
	}
	public static void crearArchivosConfig() 
	{
		try {		
				System.out.println(rutaConfig+"Configuracion.txt");
				Path archivo=Paths.get(rutaConfig+"Configuracion.txt");
				if(Files.notExists(archivo))
				{
					System.out.println("-El archivo de configuraciones creado sera guardado en la direccion \""+rutaConfig+"\".\n");
					Files.createFile(archivo);
					Files.writeString(archivo,"-Configuracion base de datos\n"
							+ "driverDeConexion=jdbc:mysql\n"
							+ "localHost=3306\n"
							+ "nombreBDD=apuestas\n"
							+ "\n-Configuracion de puntajes:\n"
							+ "puntosPorPartidoGanado=1\n"
							+ "puntosExtra=1", StandardOpenOption.APPEND);
				}
		}catch(IOException e)
		{
			System.out.println("-Error en la creacion de Configuracion.txt.\n");
			e.printStackTrace();
		}	
	}
 	//Lectura de archivos
	public static HashMap<Integer, Ronda> leerRondas() 
	{
		HashMap<Integer, Ronda> rondas= new HashMap<Integer, Ronda>();
 		try {
			Path Archivo=Paths.get(rutaRondas+"Rondas.txt");
			String[] linea;
			Partido p; 
			Ronda r;
			Equipo e1;
			Equipo e2;
			int nroRonda=0;
			r=null;
			
			for(String conLinea:Files.readAllLines(Archivo))
			{
				if(conLinea.equals(""))
					{
					 continue;
					}
				if(conLinea.contains("-"))
					{
						if(conLinea.contains("Ronda"))
						{
							if(r!=null)
							{
								rondas.put(nroRonda, r);
								r=null;
							}								
							nroRonda=(Integer.parseInt(conLinea.split("-")[1])-1);
						}
						else
						{
							r=new Ronda();
						}
						continue;
					}
				linea=conLinea.split("\t");
				e1=Equipo.getEquipoPorDatos(linea[0].trim());
				if(e1==null)
					e1=new Equipo(linea[0].trim());	
				e2=Equipo.getEquipoPorDatos(linea[3].trim());
				if(e2==null)
					e2=new Equipo(linea[3]);	
				p=new Partido(e1,Integer.parseInt(linea[1]),Integer.parseInt(linea[2]),e2);
				r.agregarPartido(p);
			}
			rondas.put(nroRonda, r);
			System.out.println("-Rondas cargadas exitosamente!\n");
			return rondas;
 		}catch(NumberFormatException e)
 		{
			System.out.println("-Asegurese de cambiar la X en el archivo de ronda, por un valor entero positivo-\n");
			return null;
		}catch(NoSuchFileException e)
		{
			System.out.println("-Error: el archivo en ruta \""+rutaRondas+"\"Rondas.txt no existe!.\n");
			return null;
		}catch(IOException e)
		{
			System.out.println("-Error en el sistemas de archivios\n");
			e.printStackTrace();
			return null;
		}
		
	}
	public static ArrayList<Persona> leerPronosticos(HashMap<Integer,Ronda> rondas)
	{
			File directorios= new File(rutaPronosticos);
			if(directorios.listFiles()==null||directorios.listFiles().length==0)
			{
				System.out.println("-No se encontraron directorios de personas.\n");
				return null;
			}
			ArrayList<Persona> personas  = new ArrayList<Persona>();
			File[] archivosEnDirectorios=directorios.listFiles();
			for( int i=0;i<archivosEnDirectorios.length;i++)
			{
				if(!archivosEnDirectorios[i].isDirectory())continue;
				String nombreArchivo=archivosEnDirectorios[i].getName();
				String ruta=rutaPronosticos+nombreArchivo+"/Pronosticos.txt";
				Persona persona= new Persona(nombreArchivo,i);
				try {
					Path archivo=Paths.get(ruta);
					String[] linea;
					Pronostico nuevoPronostico;
					int nroPartido=0;
					int nroRonda=0;
					Equipo equipoGanador;
					ResultadoPartido res;
					for(String conLinea:Files.readAllLines(archivo))
					{
						if(conLinea.isBlank())continue;
						if(conLinea.contains("-")&&conLinea.split("-").length>2) {
							nroRonda=Integer.parseInt(conLinea.split("-")[1])-1;
							nroPartido=Integer.parseInt(conLinea.split("-")[3])-1;
							continue;
						}else if(conLinea.contains("-"))
						{
							continue;
						}
						linea=conLinea.split("\t");
						equipoGanador=Equipo.getEquipoPorDatos(linea[0].trim());
						if(equipoGanador==null) continue;	
						if(linea[1].toLowerCase().contains("x"))
							res=ResultadoPartido.GANA;
						else if(linea[2].toLowerCase().contains("x"))
							res=ResultadoPartido.EMPATA;
						else if(linea[3].toLowerCase().contains("x"))
							res=ResultadoPartido.PIERDE;
						else res=ResultadoPartido.EMPATA;
						nuevoPronostico=new Pronostico(nroRonda,rondas.get(nroRonda).getPartido(nroPartido),equipoGanador,res);
						persona.agregarPronostico(nuevoPronostico);
					}
					personas.add(persona);
				}catch(NumberFormatException e)
				{
					System.out.println("-Asegurese de cambiar la X en el archivo de ronda, por un valor entero positivo\n"+
							e.getMessage());
							return null;
				}catch(NoSuchFileException e)
				{
					System.out.println("-"+persona.getNombre()+" no posee ningun pronostico.\n");
					continue;
				}catch(IOException e)
				{
					System.out.println("-Error en el sistemas de archivios\n");
					return null;
				}
			}
			if(personas.size()!=0)
		System.out.println("-Pronosticos cargados exitosamente!.\n");
			else {
				System.out.println("-No se encontraron carpetas compatibles!.\n");
				personas=null;
			}
		return personas;
	}
	
	public static String[] leerConfiguracion()
	{
		String[] configs=new String[3];
		try {
			Path archivoConfigs=Paths.get(rutaConfig+"Configuracion.txt");
			if(Files.exists(archivoConfigs))
			{
				for(String linea : Files.readAllLines(archivoConfigs))
				{
					if(linea.contains("-")) continue;
					if(linea.contains("driverDeConexion"))
					{
						configs[0]=linea.split("=")[1];
					}
					else if(linea.contains("localHost"))
					{
						configs[1]=linea.split("=")[1];
					}
					else if(linea.contains("nombreBDD"))
					{
						configs[2]=linea.split("=")[1];
					}
					else if(linea.contains("puntosPorPartidoGanado"))
					{
						Persona.setPuntosPPartido(Integer.parseInt(linea.split("=")[1]));
					}
					else if(linea.contains("puntosExtra"))
					{
						Persona.setPuntosExtra(Integer.parseInt(linea.split("=")[1]));
					}
				}
			}
			else
			{
				System.out.println("No se encontro el archivo en ruta \""+rutaConfig+"\".");
				return null;
			}
		}catch(InputMismatchException e)
		{
			System.out.println("Revise en el puntaje del archivo de configuracion. Probablemente no haya un numero para leer.\n"+e.getStackTrace());
			return null;
		}catch(IOException e)
		{
			System.out.println("Error en el sistema de archivos.\n"+e.getStackTrace());
			return null;
		}
		
		return configs;
	}
	
}
