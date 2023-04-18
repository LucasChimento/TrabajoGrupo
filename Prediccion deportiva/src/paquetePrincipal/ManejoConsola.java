package paquetePrincipal;


import java.util.InputMismatchException;
import java.util.Scanner;
public class ManejoConsola {
	private static Scanner entrada= new Scanner(System.in);
	
	public static int pedirEntero(String mensaje)
	{
		int entero;
		do {
			try
			{
				if(mensaje==null)System.out.print("Ingrese un valor numerico entero: ");
				else System.out.print(mensaje);
				entero=entrada.nextInt();
				return entero;
			}catch(InputMismatchException e)
			{
				System.out.println("Error de tipeo. Solo se aceptan numeros enteros");
			}
		}while(true);
	}
	public static String pedirTexto(String mensaje) 
		{
				if(mensaje==null)System.out.print("Ingrese un texto: ");
				else System.out.print(mensaje);
				return entrada.next();
		}
	public static boolean preguntaSioNo(String pregunta)
	{
		do {
				
			System.out.println(pregunta);
			String respuesta=entrada.next().toLowerCase();
			if(respuesta.contains("s")) {
				return true;
			} else if(respuesta.contains("n"))
			{
				return false;
			}
			else
			{
				System.out.println("ingrese (s)i o (n)o");
			}
			
		}while(true);
		
	}
}
