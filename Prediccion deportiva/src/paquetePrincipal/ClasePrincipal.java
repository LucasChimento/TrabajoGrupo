package paquetePrincipal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class ClasePrincipal {
	static ArrayList<Persona> personas= new ArrayList<Persona>();
	static HashMap<Integer,Ronda> rondas;
	static String[] configs;
	public static void main(String[] args) {
		
		boolean salir=false;
		
		System.out.println("-Bienvenido!!");
		System.out.println("\n-A continuacion se ingresara o crearan los archivos necesarios.");
		// Introduccion y set de ruta para leer las rondas
		modificarRutas(0);
		lecturaArchivos(0);
		modificarRutas(2);
		cargarConfigs();
		do {
			salir=menu(ManejoConsola.pedirEntero("-MENU PRINCIPAL\n"
				+ "1) Cargar pronosticos desde base de datos.\n"
				+ "2) Cargar pronosticos desde archivos.\n"
				+ "3) Mostrar datos de las rondas.\n"
				+ "4) Mostrar menu de las personas.\n"
				+ "5) Mostrar tabla de resultados.\n"
				+ "6) Cambiar rutas de archivos.\n"
				+ "7) Volver a cargar archivos.\n"
				+ "8) Volver a leer el archivo de configuraciones.\n"
				+ "9) Salir.\n"
				+ "\n Ingrese una de las opciones: "
				));
		}while(!salir);
		
	}
	public static boolean menu(int opcion)	// Menu principal.
	{
		switch(opcion)
		{
		case 1:
			personas= new ArrayList<Persona>();
			cargarPronosticosBDD();
			if(personas!=null && personas.size()>0)System.out.println("-Exito al cargar pronosticos\n");
			return false;
		case 2:
			ConexionBDD.cerrarConexion();
			if(ConexionBDD.getEstadoConexion())
			{
				System.out.println("-Por favor, antes de seguir cierre la conexion con la base de datos.");
				return false;
			}
			modificarRutas(1);
			personas= new ArrayList<Persona>();
			lecturaArchivos(1);
			return false;
		case 3:
			Ronda r=rondas.get(ManejoConsola.pedirEntero("-Ingrese el numero de ronda a mostrar: ")-1);
			if(r!=null)r.mostrarDatosConsola();
			else System.out.println("-No se pudo encontrar la ronda!.");
			return false;
		case 4:
			menuPersonas();
		return false;
		case 5:
			tablaPuntajes();
			return false;
		case 6:
			menuCambioRuta();
			return false;
		case 7:
			menuRecargaDeArhivos();
			return false;
		case 8:
			cargarConfigs();
			return false;
		case 9:
			System.out.println("-Saliendo...");
		return true;
		default:
			System.out.println("-Por favor ingrese alguna de las opciones listadas arriba.");
			return false;
		}
	}
	public static void cargarConfigs()
	{
		do {
			 configs = ControlArchivos.leerConfiguracion();
			 if(configs==null)
			 {
				 if(!ManejoConsola.preguntaSioNo("-Pudo resolver el problema? s/n"))
				 {
					 if(ManejoConsola.preguntaSioNo("-Desea cambiar el directorio? s/n"))
					 {
						 modificarRutas(2);
					 }
					 else {
						 if(ManejoConsola.preguntaSioNo("-Desea salir? s/n"))
						 {
							 System.out.println("-Saliendo..."); 
							 System.exit(0);
						 }						 
					 }
				 }
			 }
		}while(configs==null);
		System.out.println("-Archivo de configuracion cargado con exito!\n");
	}
	public static void menuPersonas()	// Menu que permite interactuar con la informacion de las personas.
	{
		System.out.println("\nMENU PERSONAS\n");
		int opcion1=-1;
		int opcion2;
		Persona p;
		int puntos;
		
		for(int i=0; i<personas.size();i++)
		{
			System.out.println((i+1)+") "+personas.get(i).getNombre()+".");
		}
		System.out.println((personas.size()+1)+") Volver.");
		do{
			opcion1=ManejoConsola.pedirEntero("-Elija una opcion: ");
			if(opcion1==personas.size()+1) return;
			opcion1-=1;
		}while(opcion1<0||opcion1>=personas.size());
		p=personas.get(opcion1);
		do {
			opcion2=ManejoConsola.pedirEntero("\n"+p.getNombre().toUpperCase()+"\n"
				+ "1) Ver puntos de una ronda en particular.\n"
				+ "2) Ver total de puntos.\n"
				+ "3) Agregar puntos extras.\n"
				+ "4) Elejir otra persona.\n"
				+ "5) Volver al menu principal.\n");
			Ronda r;
			switch(opcion2)
			{
			case 1:
				int nroRonda=ManejoConsola.pedirEntero("-Ingrese el numero de la ronda: ")-1;
				 r=rondas.get(nroRonda);
				if(r!=null)
				{
					if(ManejoConsola.preguntaSioNo("-Desea ver los detalles? s/n"))
					{
						puntos =p.getPuntosConDetalle(r);						
					}
					else
					{
						puntos =p.getPuntosSinDetalle(r);
					}
					System.out.println("-"+p.getNombre()+" tiene un total de: "+puntos);
				}
				else {
					System.out.println("-No se pudo encontrar la ronda especificada!");
				}
				break;
			case 2:
				puntos=0;
				
				boolean detalles=ManejoConsola.preguntaSioNo("-Desea ver los detalles? s/n");
					for(int i=0;i<=rondas.size();i++)
					{
						r=rondas.get(i);
						if(r!=null && detalles)
						{
							puntos+=p.getPuntosConDetalle(r);						
						}else if(r!=null && !detalles)
						{
							puntos+=p.getPuntosSinDetalle(r);
						}
					}
				System.out.println("-"+p.getNombre()+" tiene un total de: "+puntos
						+"\nY "+p.getPuntosE()+" puntos extras");
				break;
			case 3:
				p.agregarPuntosExtra(ManejoConsola.pedirEntero("Ingresar la cantidad de puntos extras.\n"
						+ "Recuerde que cada punto extra vale: "+Persona.getPuntosExtra()+" :"));
				break;
			case 4:
				menuPersonas();
				return;
			case 5:
				opcion2=-1;
				break;
			}
		
		} while(opcion2!=-1);
	}
	public static void modificarRutas(int opcion)	// Procedimiento que permite modificar las rutas para la lectura de archivos. Rondas(0), Pronosticos(1).							
	{
		boolean rutaEncontrada=false;
		switch(opcion)
		{
			case 0:
				System.out.println("\n-Por favor ingrese la direccion de donde desea cargar los archivos de las rondas.\n"+
					"El archivo debe llevar el nombre de \"Rondas.txt\"\n"+
					"En caso de que la ruta sea invalida o no se proporcione ninguna. Se crearan automaticamente en la ruta: \""+ControlArchivos.getRutaRondasPorDefecto()+"\".\n");
				do {
					String rutaDeseada=ManejoConsola.pedirTexto("-Ruta deseada: ");
					rutaEncontrada=ControlArchivos.setRutaRondas(rutaDeseada);
					if(!rutaEncontrada)
					{
						
						if(!ManejoConsola.preguntaSioNo("-Desea volver a intentar? s/n"))
						{
							rutaEncontrada=ControlArchivos.setRutaRondas(null);
							if(!ControlArchivos.crearDirectoriosPorDefecto(0))
							{
								System.out.println("-Hubo un error al crear el archivo por defecto! Saliendo del programa.");
								System.exit(0);
							}else
							{
								ControlArchivos.crearArchivosRondas();
								System.out.println("-Se ha creado en la ruta \""+ControlArchivos.getRutaRondasPorDefecto()+
										"\" un archivo de rondas. Por favor editelo.");	
							}
						}
					}
				}while(!rutaEncontrada);
			break;

			case 1:
					System.out.println("\n-Por favor ingrese la direccion de donde desea cargar los archivos de los pronosticos.\n"+
					"Dentro de la ruta esecificada debe haber otra con el nombre de la persona y dentro el archivo \"Pronosticos.txt\"\n"+
					"En caso de que la ruta sea invalida o no se proporcione ninguna. Se crearan automaticamente en la ruta: \""+ControlArchivos.getRutaPronosticosPorDefecto()+"\".\n");
					do 
					{
						rutaEncontrada=ControlArchivos.setRutaPronosticos(ManejoConsola.pedirTexto("Ruta deseada: "));
						if(!rutaEncontrada)
						{
							if(!ManejoConsola.preguntaSioNo("-Desea volver a intentar? s/n"))
							{
								rutaEncontrada=ControlArchivos.setRutaPronosticos(null);
								if(!ControlArchivos.crearDirectoriosPorDefecto(1))
								{
									System.out.println("-Hubo un error al crear el archivo por defecto! Saliendo del programa.");
									System.exit(0);
								}
								else
								{
									System.out.println("-Se ha creado en la ruta \""+ControlArchivos.getRutaPronosticos()+
											"\" un archivo de pronosticos.");
									if(ManejoConsola.preguntaSioNo("-Desea crear los archivos de pronosticos? s/n"))
									{
										int cantidad=ManejoConsola.pedirEntero("-Cuantas personas desea agregar?");
										for(int i=1; i<=cantidad;i++)
										{
											String nombre=ManejoConsola.pedirTexto("-Ingrese el nombre de la persona: ");
											Persona p=ControlArchivos.crearPronosticosPersona(nombre);
											if(p!=null)personas.add(p);
										}
									}
								
								}
							}
						}
					}while(!rutaEncontrada);
				break;
			case 2:
				System.out.println("\n-Por favor ingrese la direccion de donde desea cargar el archivo de configuracion.\n"+
					"El archivo debe llevar el nombre de \"Configuracion.txt\"\n"+
					"En caso de que la ruta sea invalida o no se proporcione ninguna. Se creara automaticamente en la ruta: \""+ControlArchivos.getRutaConfigsPorDefecto()+"\".\n");
				do {
					String rutaDeseada=ManejoConsola.pedirTexto("-Ruta deseada: ");
					rutaEncontrada=ControlArchivos.setRutaConfig(rutaDeseada);
					if(!rutaEncontrada)
					{
						
						if(!ManejoConsola.preguntaSioNo("-Desea volver a intentar? s/n"))
						{
							rutaEncontrada=ControlArchivos.setRutaConfig(null);
							if(!ControlArchivos.crearDirectoriosPorDefecto(2))
							{
								System.out.println("-Hubo un error al crear el archivo por defecto! Saliendo del programa.");
								System.exit(0);
							}else
						{
								ControlArchivos.crearArchivosConfig();
								System.out.println("-Se ha creado en la ruta \""+ControlArchivos.getRutaConfigsPorDefecto()+
										"\" un archivo de configuracion. Por favor editelo y vuelva a cargarlo a traves de la opcion (8).");	
							}
						}
					}
				}while(!rutaEncontrada);
				break;
		}
	}
	
	public static void menuCambioRuta()
	{
		int op=0;
		do {
			op=ManejoConsola.pedirEntero("\n1) Modificar ruta de las rondas.\n"
				+ "2) Modificar ruta de pronosticos.\n"
				+ "3) Modificar ruta de configuraciones.\n"
				+ "4) Volver.");
			if(op==4) return;
			else op-=1;
			modificarRutas(op);
			if(ManejoConsola.preguntaSioNo("Desea volver a cargar los archivos? s/n"))
			{
				if(op==3)cargarConfigs();
				else lecturaArchivos(op);
			}
		}while(true);
	}
	public static void lecturaArchivos (int opcion)	// Procedimiento de lectura de los archivos de Rondas.txt y Pronosticos.txt .
	{	
		switch(opcion)
		{
		case 0:
			do {
				rondas=ControlArchivos.leerRondas();
				if(rondas==null)
				{
					if(!ManejoConsola.preguntaSioNo("-Pudo resolver el error? s/n")) 
					{
						if(ManejoConsola.preguntaSioNo("-Desea cambiar de directorio? s/n"))
						{
							modificarRutas(0);
						}
						else {
							if(ManejoConsola.preguntaSioNo("-Desea salir? s/n"))
							{
								System.out.println("Saliendo...");
								System.exit(0);
							}
						}
					}
				}
			}while(rondas==null);
			
			break;
		case 1:
			do {
				personas=ControlArchivos.leerPronosticos(rondas);
				if(personas==null)
				{
					if(!ManejoConsola.preguntaSioNo("-Pudo resolver el error? s/n")) 
					{
						if(ManejoConsola.preguntaSioNo("-Desea cambiar de directorio? s/n"))
						{
							modificarRutas(1);
						}
						else {
							if(ManejoConsola.preguntaSioNo("-Desea salir? s/n"))
							{
								System.out.println("Saliendo...");
								System.exit(0);
							}
						}
					}
				}
			}while(personas==null);
			break;
		}
		
	}
	public static void tablaPuntajes()	// Imprime en consola una tabla con los resultados ordenados de mayor puntaje a menor.
	{
		System.out.println("-------------------------");
		System.out.println("\n-TABLA DE PUNTAJES:");
		for(Persona p : personas)
		{
			p.getPuntosTotales(rondas);
		}
		Comparator<Persona> c=(o1,o2) -> o1.compareTo(o2);
		personas.sort(c.reversed());
		for(Persona p : personas)
		{
			int lugar=personas.indexOf(p)+1;
			System.out.println(lugar+")"+p.getNombre()+" con "+p.getPuntos()+" puntos y "+p.getPuntosE()+" puntos extra.\n");
		}
		System.out.println("-------------------------");
	}
	public static void cargarPronosticosBDD()
	{
		boolean conexionHecha=false;
		if(!ConexionBDD.getEstadoConexion()) {
			String usuario=ManejoConsola.pedirTexto("Ingrese el usuario de la base de datos \""+configs[1]+":"+configs[2]+"\" : ");
			String contraseña=ManejoConsola.pedirTexto("Ingrese la contrasenia: ");
			conexionHecha=ConexionBDD.ConectarBDD(configs[0], configs[1], configs[2], usuario, contraseña);	
		}else
			{
			 if(ManejoConsola.preguntaSioNo("-Desea cambiar la conexion? s/n"))
			 {
				System.out.println("-Recuerde cambiar la configuracion de la base de datos "
						+ "en el archivo Configuracion.txt"); 
				if(ManejoConsola.preguntaSioNo("-Desea recargar el archivo? s/n"))
				{
					cargarConfigs();
				}
				ConexionBDD.cerrarConexion();
				if(ConexionBDD.getEstadoConexion()) {
					System.out.println("-No se cerro la conexion anterior! Por favor vuelva a intentar.");
					return;
				}
				String usuario=ManejoConsola.pedirTexto("Ingrese el usuario de la base de datos \""+configs[1]+":"+configs[2]+"\" : ");
				String contraseña=ManejoConsola.pedirTexto("Ingrese la contrasenia: ");
				conexionHecha=ConexionBDD.ConectarBDD(configs[0], configs[1], configs[2], usuario, contraseña);	
			 }
			}
		
		
		if(conexionHecha) {
			personas=ConexionBDD.leerPersonas();
			if(personas==null)
			{
				System.out.println("-No hay personas cargadas!\n");
				return;
			}
			for(Persona p : personas)
			{
				if(p==null) continue;
				if(!ConexionBDD.leerPronosticos(p, rondas))System.out.println("-Error al cargar pronostico de "+p.getNombre());
			}
		}
	}
	public static void menuRecargaDeArhivos()
	{
		int op;
		do{
			op=ManejoConsola.pedirEntero("1) Volver a cargar rondas.\n"
					+ "2) Volver a cargar pronosticos (archivo).\n"
					+ "3) Volver al menu principal.\n");
			switch(op)
			{
			case 1:
				rondas=new HashMap<Integer,Ronda>();
				Ronda.resetContador();
				lecturaArchivos(0);
				break;
			case 2:
				personas=new ArrayList<Persona>();
				lecturaArchivos(1);
				break;
			}
		}while(op!=3);
	}
}


